package io.github.shadowrz.projectkafka.libraries.data.impl.paging

import androidx.paging.PagingSource
import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

// Taken from SQLDelight:
// https://github.com/sqldelight/sqldelight/blob/24173ca342eae67e86668bf40ddea9cfadf2a03d/extensions/androidx-paging3/src/commonMain/kotlin/app/cash/sqldelight/paging3/QueryPagingSource.kt

internal abstract class QueryPagingSource<Key : Any, RowType : Any> :
    PagingSource<Key, RowType>(),
    Query.Listener {
    protected var currentQuery: Query<RowType>? by Delegates.observable(null) { _, old, new ->
        old?.removeListener(this)
        new?.addListener(this)
    }

    init {
        registerInvalidatedCallback {
            currentQuery?.removeListener(this)
            currentQuery = null
        }
    }

    final override fun queryResultsChanged() = invalidate()
}

@Suppress("FunctionName")
fun <RowType : Any> QueryPagingSource(
    countQuery: Query<Int>,
    transacter: TransacterBase,
    context: CoroutineContext,
    queryProvider: (limit: Int, offset: Int) -> Query<RowType>,
    initialOffset: Int = 0,
): androidx.paging.PagingSource<Int, RowType> =
    OffsetQueryPagingSource(
        queryProvider,
        countQuery,
        transacter,
        context,
        initialOffset,
    )

@Suppress("FunctionName")
fun <RowType : Any> QueryPagingSource(
    countQuery: Query<Long>,
    transacter: TransacterBase,
    context: CoroutineContext,
    queryProvider: (limit: Long, offset: Long) -> Query<RowType>,
    initialOffset: Long = 0,
): androidx.paging.PagingSource<Int, RowType> =
    OffsetQueryPagingSource(
        { limit, offset -> queryProvider(limit.toLong(), offset.toLong()) },
        countQuery.toInt(),
        transacter,
        context,
        initialOffset.toInt(),
    )

private fun Query<Long>.toInt(): Query<Int> =
    object : Query<Int>({ cursor -> mapper(cursor).toInt() }) {
        override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>) = this@toInt.execute(mapper)

        override fun addListener(listener: Listener) = this@toInt.addListener(listener)

        override fun removeListener(listener: Listener) = this@toInt.removeListener(listener)
    }
