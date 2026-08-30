package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.paging.CombinedLoadStates
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

/** Facade interface for the AndroidX [LazyPagingItems] to make it previewable. */
interface PageableItems<T : Any> {
    /**
     * Contains the immutable [ItemSnapshotList] of currently presented items, including any placeholders if they are enabled. Note that
     * similarly to [peek] accessing the items in a list will not trigger any loads. Use [get] to achieve such behavior.
     */
    val itemSnapshotList: ItemSnapshotList<T>

    /** The number of items which can be accessed. */
    val itemCount: Int

    /**
     * Returns the presented item at the specified position, notifying Paging of the item access to trigger any loads necessary to fulfill
     * prefetchDistance.
     *
     * @see peek
     */
    operator fun get(index: Int): T?

    /**
     * Returns the presented item at the specified position, without notifying Paging of the item access that would normally trigger page
     * loads.
     *
     * @param index Index of the presented item to return, including placeholders.
     * @return The presented item at position [index], `null` if it is a placeholder
     */
    fun peek(index: Int): T?

    /**
     * Retry any failed load requests that would result in a [LoadState.Error] update to this [LazyPagingItems].
     *
     * Unlike [refresh], this does not invalidate [PagingSource], it only retries failed loads within the same generation of [PagingData].
     *
     * [LoadState.Error] can be generated from two types of load requests:
     * * [PagingSource.load] returning [PagingSource.LoadResult.Error]
     * * [RemoteMediator.load] returning [RemoteMediator.MediatorResult.Error]
     */
    fun retry()

    /**
     * Refresh the data presented by this [LazyPagingItems].
     *
     * [refresh] triggers the creation of a new [PagingData] with a new instance of [PagingSource] to represent an updated snapshot of the
     * backing dataset. If a [RemoteMediator] is set, calling [refresh] will also trigger a call to [RemoteMediator.load] with
     * [androidx.paging.LoadType.REFRESH] to allow [RemoteMediator] to check for updates to the dataset backing [PagingSource].
     *
     * Note: This API is intended for UI-driven refresh signals, such as swipe-to-refresh. Invalidation due repository-layer signals, such
     * as DB-updates, should instead use [PagingSource.invalidate].
     *
     * @see PagingSource.invalidate
     */
    fun refresh()

    /** A [CombinedLoadStates] object which represents the current loading state. */
    val loadState: CombinedLoadStates

    /**
     * Returns a factory of stable and unique keys representing the item.
     *
     * Keys are generated with the key lambda that is passed in. If null is passed in, keys will default to a placeholder key. If
     * [PagingConfig.enablePlaceholders] is true, LazyPagingItems may return null items. Null items will also automatically default to a
     * placeholder key.
     *
     * This factory can be applied to Lazy foundations such as compose foundation's `LazyGridScope` or Pagers. Examples:
     *
     * @param [key] a factory of stable and unique keys representing the item. Using the same key for multiple items in the list is not
     *   allowed. Type of the key should be saveable via Bundle on Android. When you specify the key the scroll position will be maintained
     *   based on the key, which means if you add/remove items before the current visible item the item with the given key will be kept as
     *   the first visible one.
     */
    fun itemKey(key: ((item: @JvmSuppressWildcards T) -> Any)? = null): (index: Int) -> Any

    /**
     * Returns a factory for the content type of the item.
     *
     * ContentTypes are generated with the contentType lambda that is passed in. If null is passed in, contentType of all items will default
     * to `null`. If [PagingConfig.enablePlaceholders] is true, LazyPagingItems may return null items. Null items will automatically default
     * to placeholder contentType.
     *
     * This factory can be applied to Lazy foundations such as `LazyGridScope.items` or Pagers. Examples:
     *
     * @sample androidx.paging.compose.samples.PagingWithLazyGrid
     * @sample androidx.paging.compose.samples.PagingWithLazyList
     * @param [contentType] a factory of the content types for the item. The item compositions of the same type could be reused more
     *   efficiently. Note that null is a valid type and items of such type will be considered compatible.
     */
    fun itemContentType(contentType: ((item: @JvmSuppressWildcards T) -> Any?)? = null): (index: Int) -> Any?

    private class AndroidXImpl<T : Any>(private val delegate: LazyPagingItems<T>) : PageableItems<T> {
        override val itemSnapshotList: ItemSnapshotList<T>
            get() = delegate.itemSnapshotList

        override val itemCount: Int
            get() = delegate.itemCount

        override fun get(index: Int): T? = delegate.get(index)

        override fun peek(index: Int): T? = delegate.peek(index)

        override fun retry() = delegate.retry()

        override fun refresh() = delegate.refresh()

        override val loadState: CombinedLoadStates
            get() = delegate.loadState

        override fun itemKey(key: ((item: @JvmSuppressWildcards T) -> Any)?): (index: Int) -> Any = delegate.itemKey(key = key)

        override fun itemContentType(contentType: ((item: @JvmSuppressWildcards T) -> Any?)?): (index: Int) -> Any? =
            delegate.itemContentType(contentType = contentType)
    }

    class Preview<T : Any>(private val items: List<T>) : PageableItems<T> {
        override val itemSnapshotList: ItemSnapshotList<T> =
            ItemSnapshotList(
                placeholdersBefore = 0,
                placeholdersAfter = 0,
                items = items,
            )

        override val itemCount: Int
            get() = items.count()

        override fun get(index: Int): T = items[index]

        override fun peek(index: Int): T = items[index]

        override fun retry() {
            // Nothing.
        }

        override fun refresh() {
            // Nothing.
        }

        override val loadState: CombinedLoadStates =
            CombinedLoadStates(
                refresh = LoadState.NotLoading(true),
                prepend = LoadState.NotLoading(true),
                append = LoadState.NotLoading(true),
                source =
                    LoadStates(
                        refresh = LoadState.NotLoading(true),
                        prepend = LoadState.NotLoading(true),
                        append = LoadState.NotLoading(true),
                    ),
                mediator = null,
            )

        @Suppress("detekt:ReturnCount")
        override fun itemKey(key: ((item: @JvmSuppressWildcards T) -> Any)?): (index: Int) -> Any {
            if (items.isEmpty()) return { 0 }
            else if (key == null) return { it }
            else
                return { index ->
                    val item = peek(index)
                    key(item)
                }
        }

        override fun itemContentType(contentType: ((item: @JvmSuppressWildcards T) -> Any?)?): (index: Int) -> Any? {
            if (items.isEmpty()) return { 0 }
            else
                return { index ->
                    if (contentType == null) {
                        null
                    } else {
                        val item = peek(index)
                        contentType(item)
                    }
                }
        }
    }

    companion object {
        @Suppress("FunctionName")
        fun <T : Any> AndroidX(lazyPagingItems: LazyPagingItems<T>): PageableItems<T> = AndroidXImpl(lazyPagingItems)
    }
}
