// https://github.com/sqldelight/sqldelight/blob/24173ca342eae67e86668bf40ddea9cfadf2a03d/extensions/androidx-paging3/src/commonMain/kotlin/app/cash/sqldelight/paging3/OffsetQueryPagingSource.kt
package io.github.shadowrz.projectkafka.libraries.data.impl.paging

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import app.cash.sqldelight.Query
import app.cash.sqldelight.SuspendingTransacter
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.TransactionCallbacks
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class OffsetQueryPagingSource<RowType : Any>(
    private val queryProvider: (limit: Int, offset: Int) -> Query<RowType>,
    private val countQuery: Query<Int>,
    private val transacter: TransacterBase,
    private val context: CoroutineContext,
    private val initialOffset: Int = 0,
) : QueryPagingSource<Int, RowType>() {
    override val jumpingSupported get() = true

    @Suppress("detekt:CyclomaticComplexMethod")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RowType> =
        withContext(context) {
            val key = params.key ?: initialOffset
            val limit = when (params) {
                is LoadParams.Prepend -> minOf(key, params.loadSize)
                else -> params.loadSize
            }
            val getPagingSourceLoadResult: TransactionCallbacks.() -> LoadResult.Page<Int, RowType> = {
                val count = countQuery.executeAsOne()
                val offset = when (params) {
                    is LoadParams.Prepend -> maxOf(0, key - params.loadSize)
                    is LoadParams.Append -> key
                    is LoadParams.Refresh -> if (key >= count - params.loadSize) maxOf(0, count - params.loadSize) else key
                }
                val data = queryProvider(limit, offset)
                    .also { currentQuery = it }
                    .executeAsList()
                val nextPosToLoad = offset + data.size
                LoadResult.Page(
                    data = data,
                    prevKey = offset.takeIf { it > 0 && data.isNotEmpty() },
                    nextKey = nextPosToLoad.takeIf { data.isNotEmpty() && data.size >= limit && it < count },
                    itemsBefore = offset,
                    itemsAfter = maxOf(0, count - nextPosToLoad),
                )
            }
            val loadResult = when (transacter) {
                is Transacter -> transacter.transactionWithResult(bodyWithReturn = getPagingSourceLoadResult)
                is SuspendingTransacter -> transacter.transactionWithResult(bodyWithReturn = getPagingSourceLoadResult)
            }
            (if (invalid) LoadResult.Invalid() else loadResult)
        }

    override fun getRefreshKey(state: PagingState<Int, RowType>) =
        state.anchorPosition?.let {
            maxOf(
                0,
                it - (state.config.initialLoadSize / 2),
            )
        }
}
