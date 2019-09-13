package net.xpece.android.widget

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup


abstract class HeaderFooterRecyclerViewAdapter<T : androidx.recyclerview.widget.RecyclerView.ViewHolder> : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    companion object {

        internal const val VIEW_TYPE_MAX_COUNT = 0x0000ffff
        internal const val HEADER_VIEW_TYPE_OFFSET = VIEW_TYPE_MAX_COUNT
        internal const val CONTENT_VIEW_TYPE_OFFSET = HEADER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT
        internal const val PRE_FOOTER_VIEW_TYPE_OFFSET = CONTENT_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT
        internal const val FOOTER_VIEW_TYPE_OFFSET = PRE_FOOTER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT
    }

    fun isItemHeader(adapterPosition: Int): Boolean {
        val headerItemCount = this.headerItemCount
        return headerItemCount > 0 && adapterPosition < headerItemCount
    }

    fun isItemContent(adapterPosition: Int): Boolean {
        val headerItemCount = this.headerItemCount
        val contentItemCount = this.contentItemCount
        return contentItemCount > 0
                && adapterPosition < headerItemCount + contentItemCount
                && adapterPosition >= headerItemCount
    }

    internal fun isItemPreFooter(adapterPosition: Int): Boolean {
        val headerItemCount = this.headerItemCount
        val contentItemCount = this.contentItemCount
        val preFooterItemCount = this.preFooterItemCount
        return preFooterItemCount > 0
                && adapterPosition < headerItemCount + contentItemCount + preFooterItemCount
                && adapterPosition >= headerItemCount + contentItemCount
    }

    fun isItemFooter(adapterPosition: Int): Boolean {
        val headerItemCount = this.headerItemCount
        val contentItemCount = this.contentItemCount
        val preFooterItemCount = this.preFooterItemCount
        val footerItemCount = this.footerItemCount
        return footerItemCount > 0
                && adapterPosition < headerItemCount + contentItemCount + preFooterItemCount + footerItemCount
                && adapterPosition >= headerItemCount + contentItemCount + preFooterItemCount
    }

    /**
     * Gets the header item view type. By default, this method returns 0.

     * @param position the position.
     * *
     * @return the header item view type (within the range [0 - VIEW_TYPE_MAX_COUNT-1]).
     */
    protected open fun getHeaderItemViewType(position: Int) = 0

    /**
     * Gets the footer item view type. By default, this method returns 0.

     * @param position the position.
     * *
     * @return the footer item view type (within the range [0 - VIEW_TYPE_MAX_COUNT-1]).
     */
    protected open fun getFooterItemViewType(position: Int) = 0


    /**
     * Gets the content item view type. By default, this method returns 0.

     * @param position the position.
     * *
     * @return the content item view type (within the range [0 - VIEW_TYPE_MAX_COUNT-1]).
     */
    protected open fun getContentItemViewType(position: Int) = 0

    internal open fun getPreFooterItemViewType(position: Int) = 0

    override fun getItemViewType(position: Int): Int {
        val headerItemCount = this.headerItemCount
        val contentItemCount = this.contentItemCount
        val preFooterItemCount = this.preFooterItemCount
        val footerItemCount = this.footerItemCount

        // Delegate to proper methods based on the position, but validate first.
        if (headerItemCount > 0 && position < headerItemCount) {
            return validateViewType(getHeaderItemViewType(position)) + HEADER_VIEW_TYPE_OFFSET
        } else if (contentItemCount > 0 && position - headerItemCount < contentItemCount) {
            return validateViewType(getContentItemViewType(position - headerItemCount)) + CONTENT_VIEW_TYPE_OFFSET
        } else if (preFooterItemCount > 0 && position - headerItemCount - contentItemCount < preFooterItemCount) {
            return validateViewType(getPreFooterItemViewType(position - headerItemCount - contentItemCount)) + PRE_FOOTER_VIEW_TYPE_OFFSET
        } else if (footerItemCount > 0 && position - headerItemCount - contentItemCount - preFooterItemCount < footerItemCount) {
            return validateViewType(getFooterItemViewType(position - headerItemCount - contentItemCount - preFooterItemCount)) + FOOTER_VIEW_TYPE_OFFSET
        } else {
            throw IllegalArgumentException("No view type for position $position.")
        }
    }

    /**
     * Validates that the view type is within the valid range.

     * @param viewType the view type.
     *
     * @return the given view type.
     */
    private fun validateViewType(viewType: Int): Int {
        if (viewType < 0 || viewType >= VIEW_TYPE_MAX_COUNT) {
            throw IllegalStateException("viewType must be between 0 and " + VIEW_TYPE_MAX_COUNT)
        }
        return viewType
    }

    /**
     * Gets the header item count. This method can be called several times, so it should not calculate the count every time.
     */
    open val headerItemCount: Int
        get () = 0

    /**
     * Gets the content item count. This method can be called several times, so it should not calculate the count every time.
     */
    abstract val contentItemCount: Int

    /**
     * Gets the footer item count. This method can be called several times, so it should not calculate the count every time.
     */
    open val footerItemCount: Int
        get() = 0

    open internal val preFooterItemCount: Int
        get() = 0

    override final fun getItemCount() = headerItemCount + contentItemCount + preFooterItemCount + footerItemCount

    @Suppress("UNCHECKED_CAST")
    override final fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val headerItemCount = this.headerItemCount
        val contentItemCount = this.contentItemCount
        val preFooterItemCount = this.preFooterItemCount
        val footerItemCount = this.footerItemCount

        if (headerItemCount > 0 && position < headerItemCount) {
            onBindHeaderItemViewHolder(holder, position)
        } else if (contentItemCount > 0 && position - headerItemCount < contentItemCount) {
            onBindContentItemViewHolder(holder as T, position - headerItemCount)
        } else if (preFooterItemCount > 0 && position - headerItemCount - contentItemCount < preFooterItemCount) {
            onBindPreFooterItemViewHolder(holder, position - headerItemCount - contentItemCount)
        } else if (footerItemCount > 0 && position - headerItemCount - contentItemCount - preFooterItemCount < footerItemCount) {
            onBindFooterItemViewHolder(holder, position - headerItemCount - contentItemCount - preFooterItemCount)
        } else {
            throw IllegalStateException("Invalid position $position, item count is $itemCount.")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int, payloads: List<@JvmSuppressWildcards Any>) {
        val headerItemCount = this.headerItemCount
        val contentItemCount = this.contentItemCount
        val preFooterItemCount = this.preFooterItemCount
        val footerItemCount = this.footerItemCount

        if (headerItemCount > 0 && position < headerItemCount) {
            onBindHeaderItemViewHolder(holder, position, payloads)
        } else if (contentItemCount > 0 && position - headerItemCount < contentItemCount) {
            onBindContentItemViewHolder(holder as T, position - headerItemCount, payloads)
        } else if (preFooterItemCount > 0 && position - headerItemCount - contentItemCount < preFooterItemCount) {
            onBindPreFooterItemViewHolder(holder, position - headerItemCount - contentItemCount, payloads)
        } else if (footerItemCount > 0 && position - headerItemCount - contentItemCount - preFooterItemCount < footerItemCount) {
            onBindFooterItemViewHolder(holder, position - headerItemCount - contentItemCount - preFooterItemCount, payloads)
        } else {
            throw IllegalStateException("Invalid position $position, item count is $itemCount.")
        }
    }

    override final fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        if (viewType >= HEADER_VIEW_TYPE_OFFSET && viewType < HEADER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT) {
            return onCreateHeaderItemViewHolder(parent, viewType - HEADER_VIEW_TYPE_OFFSET)
        } else if (viewType >= CONTENT_VIEW_TYPE_OFFSET && viewType < CONTENT_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT) {
            return onCreateContentItemViewHolder(parent, viewType - CONTENT_VIEW_TYPE_OFFSET)
        } else if (viewType >= PRE_FOOTER_VIEW_TYPE_OFFSET && viewType < PRE_FOOTER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT) {
            return onCreatePreFooterItemViewHolder(parent, viewType - PRE_FOOTER_VIEW_TYPE_OFFSET)
        } else if (viewType >= FOOTER_VIEW_TYPE_OFFSET && viewType < FOOTER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT) {
            return onCreateFooterItemViewHolder(parent, viewType - FOOTER_VIEW_TYPE_OFFSET)
        } else {
            throw IllegalStateException("Unknown view type $viewType")
        }
    }

    /**
     * This method works exactly the same as [.onCreateViewHolder], but for header items.

     * @param parent the parent view.
     * *
     * @param headerViewType the view type for the header.
     * *
     * @return the view holder.
     */
    protected open fun onCreateHeaderItemViewHolder(parent: ViewGroup, headerViewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        throw UnsupportedOperationException()
    }

    /**
     * This method works exactly the same as [.onCreateViewHolder], but for footer items.

     * @param parent the parent view.
     * *
     * @param footerViewType the view type for the footer.
     * *
     * @return the view holder.
     */
    protected open fun onCreateFooterItemViewHolder(parent: ViewGroup, footerViewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        throw UnsupportedOperationException()
    }

    internal open fun onCreatePreFooterItemViewHolder(parent: ViewGroup, footerViewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        throw UnsupportedOperationException()
    }

    /**
     * This method works exactly the same as [.onCreateViewHolder], but for content items.

     * @param parent the parent view.
     * *
     * @param contentViewType the view type for the content.
     * *
     * @return the view holder.
     */
    protected abstract fun onCreateContentItemViewHolder(parent: ViewGroup, contentViewType: Int): T

    /**
     * This method works exactly the same as [.onBindViewHolder], but for header items.

     * @param headerViewHolder the view holder for the header item.
     * *
     * @param position the position.
     */
    protected open fun onBindHeaderItemViewHolder(headerViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
    }

    /**
     * This method works exactly the same as [.onBindViewHolder], but for footer items.

     * @param footerViewHolder the view holder for the footer item.
     * *
     * @param position the position.
     */
    protected open fun onBindFooterItemViewHolder(footerViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
    }

    internal open fun onBindPreFooterItemViewHolder(footerViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
    }

    /**
     * This method works exactly the same as [.onBindViewHolder], but for content items.

     * @param contentViewHolder the view holder for the content item.
     * *
     * @param position the position.
     */
    protected abstract fun onBindContentItemViewHolder(contentViewHolder: T, position: Int)

    /**
     * This method works exactly the same as [.onBindViewHolder], but for header items.

     * @param headerViewHolder the view holder for the header item.
     * @param position the position.
     * @param payloads A non-null list of merged payloads. Can be empty list if requires full update.
     */
    protected open fun onBindHeaderItemViewHolder(headerViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int, payloads: List<@JvmSuppressWildcards Any>) {
        onBindHeaderItemViewHolder(headerViewHolder, position)
    }

    /**
     * This method works exactly the same as [.onBindViewHolder], but for footer items.

     * @param footerViewHolder the view holder for the footer item.
     * @param position the position.
     * @param payloads A non-null list of merged payloads. Can be empty list if requires full update.
     */
    protected open fun onBindFooterItemViewHolder(footerViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int, payloads: List<@JvmSuppressWildcards Any>) {
        onBindFooterItemViewHolder(footerViewHolder, position)
    }

    internal open fun onBindPreFooterItemViewHolder(footerViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int, payloads: List<@JvmSuppressWildcards Any>) {
        onBindPreFooterItemViewHolder(footerViewHolder, position)
    }

    /**
     * This method works exactly the same as [.onBindViewHolder], but for content items.

     * @param contentViewHolder the view holder for the content item.
     * @param position the position.
     * @param payloads A non-null list of merged payloads. Can be empty list if requires full update.
     */
    protected open fun onBindContentItemViewHolder(contentViewHolder: T, position: Int, payloads: List<@JvmSuppressWildcards Any>) {
        onBindContentItemViewHolder(contentViewHolder, position)
    }

    /**
     * Notifies that a header item is inserted.

     * @param position the position of the header item.
     */
    fun notifyHeaderItemInserted(position: Int) {
        notifyHeaderItemRangeInserted(position, 1)
    }

    /**
     * Notifies that multiple header items are inserted.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyHeaderItemRangeInserted(positionStart: Int, itemCount: Int) {
        notifyItemRangeInserted(positionStart, itemCount)
    }

    /**
     * Notifies that a header item is changed.

     * @param position the position.
     */
    fun notifyHeaderItemChanged(position: Int) {
        notifyHeaderItemRangeChanged(position, 1)
    }

    /**
     * Notifies that multiple header items are changed.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyHeaderItemRangeChanged(positionStart: Int, itemCount: Int) {
        notifyItemRangeChanged(positionStart, itemCount)
    }

    /**
     * Notifies that an existing header item is moved to another position.

     * @param fromPosition the original position.
     * *
     * @param toPosition the new position.
     */
    open fun notifyHeaderItemMoved(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }

    /**
     * Notifies that a header item is removed.

     * @param position the position.
     */
    fun notifyHeaderItemRemoved(position: Int) {
        notifyHeaderItemRangeRemoved(position, 1)
    }

    /**
     * Notifies that multiple header items are removed.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyHeaderItemRangeRemoved(positionStart: Int, itemCount: Int) {
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    /**
     * Notifies that a content item is inserted.

     * @param position the position of the content item.
     */
    fun notifyContentItemInserted(position: Int) {
        notifyContentItemRangeInserted(position, 1)
    }

    /**
     * Notifies that multiple content items are inserted.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyContentItemRangeInserted(positionStart: Int, itemCount: Int) {
        notifyItemRangeInserted(positionStart + headerItemCount, itemCount)
    }

    /**
     * Notifies that a content item is changed.

     * @param position the position.
     */
    fun notifyContentItemChanged(position: Int) {
        notifyContentItemRangeChanged(position, 1)
    }

    /**
     * Notifies that multiple content items are changed.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyContentItemRangeChanged(positionStart: Int, itemCount: Int) {
        notifyItemRangeChanged(positionStart + headerItemCount, itemCount)
    }

    /**
     * Notifies that an existing content item is moved to another position.

     * @param fromPosition the original position.
     * *
     * @param toPosition the new position.
     */
    open fun notifyContentItemMoved(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition + headerItemCount, toPosition + headerItemCount)
    }

    /**
     * Notifies that a content item is removed.

     * @param position the position.
     */
    fun notifyContentItemRemoved(position: Int) {
        notifyContentItemRangeRemoved(position, 1)
    }

    /**
     * Notifies that multiple content items are removed.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyContentItemRangeRemoved(positionStart: Int, itemCount: Int) {
        notifyItemRangeRemoved(positionStart + headerItemCount, itemCount)
    }

    /**
     * Notifies that a footer item is inserted.

     * @param position the position of the content item.
     */
    fun notifyFooterItemInserted(position: Int) {
        notifyFooterItemRangeInserted(position, 1)
    }

    /**
     * Notifies that multiple footer items are inserted.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyFooterItemRangeInserted(positionStart: Int, itemCount: Int) {
        val offset = headerItemCount + contentItemCount + preFooterItemCount
        notifyItemRangeInserted(positionStart + offset, itemCount)
    }

    /**
     * Notifies that a footer item is changed.

     * @param position the position.
     */
    fun notifyFooterItemChanged(position: Int) {
        notifyFooterItemRangeChanged(position, 1)
    }

    /**
     * Notifies that multiple footer items are changed.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyFooterItemRangeChanged(positionStart: Int, itemCount: Int) {
        val offset = headerItemCount + contentItemCount + preFooterItemCount
        notifyItemRangeChanged(positionStart + offset, itemCount)
    }

    /**
     * Notifies that an existing footer item is moved to another position.

     * @param fromPosition the original position.
     * *
     * @param toPosition the new position.
     */
    open fun notifyFooterItemMoved(fromPosition: Int, toPosition: Int) {
        val offset = headerItemCount + contentItemCount + preFooterItemCount
        notifyItemMoved(offset + fromPosition, toPosition + offset)
    }

    /**
     * Notifies that a footer item is removed.

     * @param position the position.
     */
    fun notifyFooterItemRemoved(position: Int) {
        notifyFooterItemRangeRemoved(position, 1)
    }

    /**
     * Notifies that multiple footer items are removed.

     * @param positionStart the position.
     * *
     * @param itemCount the item count.
     */
    open fun notifyFooterItemRangeRemoved(positionStart: Int, itemCount: Int) {
        val offset = headerItemCount + contentItemCount + preFooterItemCount
        notifyItemRangeRemoved(positionStart + offset, itemCount)
    }

    internal fun notifyPreFooterItemInserted(position: Int) {
        notifyFooterItemRangeInserted(position, 1)
    }

    open internal fun notifyPreFooterItemRangeInserted(positionStart: Int, itemCount: Int) {
        val offset = headerItemCount + contentItemCount
        notifyItemRangeInserted(positionStart + offset, itemCount)
    }

    internal fun notifyPreFooterItemChanged(position: Int) {
        notifyPreFooterItemRangeChanged(position, 1)
    }

    open internal fun notifyPreFooterItemRangeChanged(positionStart: Int, itemCount: Int) {
        val offset = headerItemCount + contentItemCount
        notifyItemRangeChanged(positionStart + offset, itemCount)
    }

    open internal fun notifyPreFooterItemMoved(fromPosition: Int, toPosition: Int) {
        val offset = headerItemCount + contentItemCount
        notifyItemMoved(offset + fromPosition, toPosition + offset)
    }

    internal fun notifyPreFooterItemRemoved(position: Int) {
        notifyPreFooterItemRangeRemoved(position, 1)
    }

    open internal fun notifyPreFooterItemRangeRemoved(positionStart: Int, itemCount: Int) {
        val offset = headerItemCount + contentItemCount
        notifyItemRangeRemoved(positionStart + offset, itemCount)
    }


}
