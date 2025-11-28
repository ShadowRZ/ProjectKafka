package io.github.shadowrz.projectkafka.libraries.data.impl.paging

import androidx.paging.PagingState
import app.cash.sqldelight.Query
import app.cash.sqldelight.SuspendingTransacter
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.TransactionCallbacks
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class RowIdAnchoredPagingSource<RowType : Any>(
    private val forwardQueryProvider: (anchor: Long?, limit: Long) -> Query<RowType>,
    private val backwardQueryProvider: (anchor: Long?, limit: Long) -> Query<RowType>,
    private val rowId: (value: RowType) -> Long,
    private val transacter: TransacterBase,
    private val context: CoroutineContext,
) : QueryPagingSource<Long, RowType>() {
    override val jumpingSupported: Boolean get() = false

    override fun getRefreshKey(state: PagingState<Long, RowType>): Long? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, RowType> =
        withContext(context) {
            val getPagingSourceLoadResult: TransactionCallbacks.() -> LoadResult<Long, RowType> = {
                when (params) {
                    is LoadParams.Refresh<*>,
                    is LoadParams.Append<*>,
                    -> {
                        val result = forwardQueryProvider(params.key, params.loadSize.toLong())
                            .also { currentQuery = it }
                            .executeAsList()
                        val nextKey = if (result.size < params.loadSize) null else rowId(result.last())
                        val prevKey = result.firstOrNull()?.let { rowId(it) }
                        LoadResult.Page(result, prevKey, nextKey)
                    }

                    is LoadParams.Prepend<*> -> {
                        val result = backwardQueryProvider(params.key, params.loadSize.toLong())
                            .also { currentQuery = it }
                            .executeAsList()
                        val prevKey = if (result.size < params.loadSize) null else rowId(result.last())
                        val nextKey = result.firstOrNull()?.let { rowId(it) }
                        LoadResult.Page(result, prevKey, nextKey)
                    }
                }
            }
            when (transacter) {
                is Transacter -> transacter.transactionWithResult(bodyWithReturn = getPagingSourceLoadResult)
                is SuspendingTransacter -> transacter.transactionWithResult(bodyWithReturn = getPagingSourceLoadResult)
            }
        }
}
