package net.xpece.android.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.xpece.android.R;

public abstract class HeaderFooterRecyclerViewAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MAX_COUNT = 1000;
    private static final int HEADER_VIEW_TYPE_OFFSET = 0;
    private static final int FOOTER_VIEW_TYPE_OFFSET = HEADER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT;
    private static final int CONTENT_VIEW_TYPE_OFFSET = FOOTER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT;

    private static final int PROGRESS_VIEW_TYPE = Integer.MAX_VALUE - 1;
    private static final int ERROR_VIEW_TYPE = Integer.MAX_VALUE - 2;

//    private int headerItemCount;
//    private int contentItemCount;
//    private int footerItemCount;

    private boolean mProgress = false;

    private boolean mShowError = false;
    private String mErrorText = null;
    private View.OnClickListener mErrorOnClickListener = null;

    public void showProgress() {
        hideError();

        if (!mProgress) {
            mProgress = true;
            notifyFooterItemInserted(0);
        }
    }

    public void hideProgress() {
        if (mProgress) {
            notifyFooterItemRemoved(0);
            mProgress = false;
        }
    }

    public void showError(String text, View.OnClickListener onClickListener) {
        hideProgress();

        mErrorText = text;
        mErrorOnClickListener = onClickListener;
        if (mShowError) {
            notifyFooterItemChanged(0);
        } else {
            mShowError = true;
            notifyFooterItemInserted(0);
        }
    }

    public void hideError() {
        if (mShowError) {
            notifyFooterItemRemoved(0);
            mErrorText = null;
            mErrorOnClickListener = null;
            mShowError = false;
        }
    }

    public boolean isItemHeader(int position) {
//        return headerItemCount > 0 && position < headerItemCount;
        int headerItemCount = getHeaderItemCount();
        return headerItemCount > 0 && position < headerItemCount;
    }

    public boolean isItemContent(int position) {
//        return contentItemCount > 0 && position < headerItemCount + contentItemCount;
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        return contentItemCount > 0 && position < headerItemCount + contentItemCount;
    }

    public boolean isItemFooter(int position) {
//        return footerItemCount > 0 && position < headerItemCount + contentItemCount + footerItemCount;
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getFooterItemCount();
        return footerItemCount > 0 && position < headerItemCount + contentItemCount + footerItemCount;
    }

    public boolean isItemProgress(int position) {
//        return mProgress && position == headerItemCount + contentItemCount;
        return mProgress && position == getHeaderItemCount() + getContentItemCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getItemId(int position) {
        if (!hasStableIds()) {
            return RecyclerView.NO_ID;
        }

        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();

        // Delegate to proper methods based on the position, but validate first.
        if (headerItemCount > 0 && position < headerItemCount) {
            return RecyclerView.NO_ID;
        } else if (contentItemCount > 0 && position - headerItemCount < contentItemCount) {
            return getContentItemId(position - headerItemCount);
        } else {
            return RecyclerView.NO_ID;
        }
    }

    public long getContentItemId(int position) {
        return RecyclerView.NO_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Delegate to proper methods based on the viewType ranges.
        if (viewType >= HEADER_VIEW_TYPE_OFFSET && viewType < HEADER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT) {
            return onCreateHeaderItemViewHolder(parent, viewType - HEADER_VIEW_TYPE_OFFSET);
        } else if (viewType >= FOOTER_VIEW_TYPE_OFFSET && viewType < FOOTER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT) {
            return onCreateFooterItemViewHolder(parent, viewType - FOOTER_VIEW_TYPE_OFFSET);
        } else if (viewType >= CONTENT_VIEW_TYPE_OFFSET && viewType < CONTENT_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT) {
            return onCreateContentItemViewHolder(parent, viewType - CONTENT_VIEW_TYPE_OFFSET);
        } else if (viewType == PROGRESS_VIEW_TYPE) {
            return onCreateProgressItemViewHolder(parent);
        } else if (viewType == ERROR_VIEW_TYPE) {
            return onCreateErrorItemViewHolder(parent);
        } else {
            // This shouldn't happen as we check that the viewType provided by the client is valid.
            throw new IllegalStateException();
        }
    }

    private RecyclerView.ViewHolder onCreateProgressItemViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.xpc_progress, parent, false);
        return new EmptyViewHolder(view);
    }

    private RecyclerView.ViewHolder onCreateErrorItemViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.xpc_try_again, parent, false);
        return new ErrorViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();

        // Delegate to proper methods based on the viewType ranges.
        if (headerItemCount > 0 && position < headerItemCount) {
            onBindHeaderItemViewHolder(viewHolder, position);
        } else if (contentItemCount > 0 && position - headerItemCount < contentItemCount) {
            onBindContentItemViewHolder(viewHolder, position - headerItemCount);
        } else if (mShowError && position == headerItemCount + contentItemCount) {
            onBindErrorItemViewHolder((ErrorViewHolder) viewHolder);
        } else {
            onBindFooterItemViewHolder(viewHolder, position - headerItemCount - contentItemCount);
        }
    }

    private void onBindErrorItemViewHolder(ErrorViewHolder holder) {
        holder.textView.setText(mErrorText);
        holder.button.setOnClickListener(mErrorOnClickListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getItemCount() {
        // Cache the counts and return the sum of them.
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getFooterItemCount();
        return headerItemCount + contentItemCount + footerItemCount;
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public final int getItemViewType(int position) {
//        // Delegate to proper methods based on the position, but validate first.
//        if (headerItemCount > 0 && position < headerItemCount) {
//            return validateViewType(getHeaderItemViewType(position)) + HEADER_VIEW_TYPE_OFFSET;
//        } else if (contentItemCount > 0 && position - headerItemCount < contentItemCount) {
//            return validateViewType(getContentItemViewType(position - headerItemCount)) + CONTENT_VIEW_TYPE_OFFSET;
//        } else {
//            if (mProgress && position == headerItemCount + contentItemCount) {
//                return PROGRESS_VIEW_TYPE;
//            } else {
//                return validateViewType(getFooterItemViewType(position - headerItemCount - contentItemCount)) + FOOTER_VIEW_TYPE_OFFSET;
//            }
//        }
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getItemViewType(int position) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();

        // Delegate to proper methods based on the position, but validate first.
        if (headerItemCount > 0 && position < headerItemCount) {
            return validateViewType(getHeaderItemViewType(position)) + HEADER_VIEW_TYPE_OFFSET;
        } else if (contentItemCount > 0 && position - headerItemCount < contentItemCount) {
            return validateViewType(getContentItemViewType(position - headerItemCount)) + CONTENT_VIEW_TYPE_OFFSET;
        } else {
            if (position == headerItemCount + contentItemCount) {
                if (mShowError) {
                    return ERROR_VIEW_TYPE;
                } else if (mProgress) {
                    return PROGRESS_VIEW_TYPE;
                }
            }
            return validateViewType(getFooterItemViewType(position - headerItemCount - contentItemCount)) + FOOTER_VIEW_TYPE_OFFSET;
        }
    }

    /**
     * Validates that the view type is within the valid range.
     *
     * @param viewType the view type.
     * @return the given view type.
     */
    private int validateViewType(int viewType) {
        if (viewType < 0 || viewType >= VIEW_TYPE_MAX_COUNT) {
            throw new IllegalStateException("viewType must be between 0 and " + VIEW_TYPE_MAX_COUNT);
        }
        return viewType;
    }

    /**
     * Notifies that a header item is inserted.
     *
     * @param position the position of the header item.
     */
    public final void notifyHeaderItemInserted(int position) {
        int newHeaderItemCount = getHeaderItemCount();
        if (position < 0 || position >= newHeaderItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for header items [0 - " + (newHeaderItemCount - 1) + "].");
        }
        notifyItemInserted(position);
    }

    /**
     * Notifies that multiple header items are inserted.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyHeaderItemRangeInserted(int positionStart, int itemCount) {
        int newHeaderItemCount = getHeaderItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount > newHeaderItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for header items [0 - " + (newHeaderItemCount - 1) + "].");
        }
        notifyItemRangeInserted(positionStart, itemCount);
    }

    /**
     * Notifies that a header item is changed.
     *
     * @param position the position.
     */
    public final void notifyHeaderItemChanged(int position) {
        int headerItemCount = getHeaderItemCount();
        if (position < 0 || position >= headerItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for header items [0 - " + (headerItemCount - 1) + "].");
        }
        notifyItemChanged(position);
    }

    /**
     * Notifies that multiple header items are changed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyHeaderItemRangeChanged(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount >= headerItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for header items [0 - " + (headerItemCount - 1) + "].");
        }
        notifyItemRangeChanged(positionStart, itemCount);
    }

    /**
     * Notifies that an existing header item is moved to another position.
     *
     * @param fromPosition the original position.
     * @param toPosition the new position.
     */
    public void notifyHeaderItemMoved(int fromPosition, int toPosition) {
        int headerItemCount = getHeaderItemCount();
        if (fromPosition < 0 || toPosition < 0 || fromPosition >= headerItemCount || toPosition >= headerItemCount) {
            throw new IndexOutOfBoundsException("The given fromPosition " + fromPosition + " or toPosition " + toPosition + " is not within the position bounds for header items [0 - " + (headerItemCount - 1) + "].");
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * Notifies that a header item is removed.
     *
     * @param position the position.
     */
    public void notifyHeaderItemRemoved(int position) {
        int headerItemCount = getHeaderItemCount();
        if (position < 0 || position >= headerItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for header items [0 - " + (headerItemCount - 1) + "].");
        }
        notifyItemRemoved(position);
    }

    /**
     * Notifies that multiple header items are removed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public void notifyHeaderItemRangeRemoved(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount > headerItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for header items [0 - " + (headerItemCount - 1) + "].");
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    /**
     * Notifies that a content item is inserted.
     *
     * @param position the position of the content item.
     */
    public final void notifyContentItemInserted(int position) {
        int newHeaderItemCount = getHeaderItemCount();
        int newContentItemCount = getContentItemCount();
        if (position < 0 || position >= newContentItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for content items [0 - " + (newContentItemCount - 1) + "].");
        }
        notifyItemInserted(position + newHeaderItemCount);
    }

    /**
     * Notifies that multiple content items are inserted.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyContentItemRangeInserted(int positionStart, int itemCount) {
        int newHeaderItemCount = getHeaderItemCount();
        int newContentItemCount = getContentItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount > newContentItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for content items [0 - " + (newContentItemCount - 1) + "].");
        }
        notifyItemRangeInserted(positionStart + newHeaderItemCount, itemCount);
    }

    /**
     * Notifies that a content item is changed.
     *
     * @param position the position.
     */
    public final void notifyContentItemChanged(int position) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        if (position < 0 || position >= contentItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for content items [0 - " + (contentItemCount - 1) + "].");
        }
        notifyItemChanged(position + headerItemCount);
    }

    /**
     * Notifies that multiple content items are changed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyContentItemRangeChanged(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount > contentItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for content items [0 - " + (contentItemCount - 1) + "].");
        }
        notifyItemRangeChanged(positionStart + headerItemCount, itemCount);
    }

    /**
     * Notifies that an existing content item is moved to another position.
     *
     * @param fromPosition the original position.
     * @param toPosition the new position.
     */
    public final void notifyContentItemMoved(int fromPosition, int toPosition) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        if (fromPosition < 0 || toPosition < 0 || fromPosition >= contentItemCount || toPosition >= contentItemCount) {
            throw new IndexOutOfBoundsException("The given fromPosition " + fromPosition + " or toPosition " + toPosition + " is not within the position bounds for content items [0 - " + (contentItemCount - 1) + "].");
        }
        notifyItemMoved(fromPosition + headerItemCount, toPosition + headerItemCount);
    }

    /**
     * Notifies that a content item is removed.
     *
     * @param position the position.
     */
    public final void notifyContentItemRemoved(int position) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        if (position < 0 || position >= contentItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for content items [0 - " + (contentItemCount - 1) + "].");
        }
        notifyItemRemoved(position + headerItemCount);
    }

    /**
     * Notifies that multiple content items are removed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyContentItemRangeRemoved(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount > contentItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for content items [0 - " + (contentItemCount - 1) + "].");
        }
        notifyItemRangeRemoved(positionStart + headerItemCount, itemCount);
    }

    /**
     * Notifies that a footer item is inserted.
     *
     * @param position the position of the content item.
     */
    public final void notifyFooterItemInserted(int position) {
        int newHeaderItemCount = getHeaderItemCount();
        int newContentItemCount = getContentItemCount();
        int newFooterItemCount = getFooterItemCount();
        if (position < 0 || position >= newFooterItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for footer items [0 - " + (newFooterItemCount - 1) + "].");
        }
        notifyItemInserted(position + newHeaderItemCount + newContentItemCount);
    }

    /**
     * Notifies that multiple footer items are inserted.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyFooterItemRangeInserted(int positionStart, int itemCount) {
        int newHeaderItemCount = getHeaderItemCount();
        int newContentItemCount = getContentItemCount();
        int newFooterItemCount = getFooterItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount > newFooterItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for footer items [0 - " + (newFooterItemCount - 1) + "].");
        }
        notifyItemRangeInserted(positionStart + newHeaderItemCount + newContentItemCount, itemCount);
    }

    /**
     * Notifies that a footer item is changed.
     *
     * @param position the position.
     */
    public final void notifyFooterItemChanged(int position) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getFooterItemCount();
        if (position < 0 || position >= footerItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for footer items [0 - " + (footerItemCount - 1) + "].");
        }
        notifyItemChanged(position + headerItemCount + contentItemCount);
    }

    /**
     * Notifies that multiple footer items are changed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyFooterItemRangeChanged(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getFooterItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount > footerItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for footer items [0 - " + (footerItemCount - 1) + "].");
        }
        notifyItemRangeChanged(positionStart + headerItemCount + contentItemCount, itemCount);
    }

    /**
     * Notifies that an existing footer item is moved to another position.
     *
     * @param fromPosition the original position.
     * @param toPosition the new position.
     */
    public final void notifyFooterItemMoved(int fromPosition, int toPosition) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getFooterItemCount();
        if (fromPosition < 0 || toPosition < 0 || fromPosition >= footerItemCount || toPosition >= footerItemCount) {
            throw new IndexOutOfBoundsException("The given fromPosition " + fromPosition + " or toPosition " + toPosition + " is not within the position bounds for footer items [0 - " + (footerItemCount - 1) + "].");
        }
        notifyItemMoved(fromPosition + headerItemCount + contentItemCount, toPosition + headerItemCount + contentItemCount);
    }

    /**
     * Notifies that a footer item is removed.
     *
     * @param position the position.
     */
    public final void notifyFooterItemRemoved(int position) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getFooterItemCount();
        if (position < 0 || position >= footerItemCount) {
            throw new IndexOutOfBoundsException("The given position " + position + " is not within the position bounds for footer items [0 - " + (footerItemCount - 1) + "].");
        }
        notifyItemRemoved(position + headerItemCount + contentItemCount);
    }

    /**
     * Notifies that multiple footer items are removed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyFooterItemRangeRemoved(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getFooterItemCount();
        if (positionStart < 0 || itemCount < 0 || positionStart + itemCount > footerItemCount) {
            throw new IndexOutOfBoundsException("The given range [" + positionStart + " - " + (positionStart + itemCount - 1) + "] is not within the position bounds for footer items [0 - " + (footerItemCount - 1) + "].");
        }
        notifyItemRangeRemoved(positionStart + headerItemCount + contentItemCount, itemCount);
    }

    /**
     * Gets the header item view type. By default, this method returns 0.
     *
     * @param position the position.
     * @return the header item view type (within the range [0 - VIEW_TYPE_MAX_COUNT-1]).
     */
    protected int getHeaderItemViewType(int position) {
        return 0;
    }

    /**
     * Gets the footer item view type. By default, this method returns 0.
     *
     * @param position the position.
     * @return the footer item view type (within the range [0 - VIEW_TYPE_MAX_COUNT-1]).
     */
    protected int getFooterItemViewType(int position) {
        return 0;
    }

    /**
     * Gets the content item view type. By default, this method returns 0.
     *
     * @param position the position.
     * @return the content item view type (within the range [0 - VIEW_TYPE_MAX_COUNT-1]).
     */
    protected int getContentItemViewType(int position) {
        return 0;
    }

    /**
     * Gets the header item count. This method can be called several times, so it should not calculate the count every time.
     *
     * @return the header item count.
     */
    protected int getHeaderItemCount() {
        return 0;
    }

    /**
     * Gets the footer item count. This method can be called several times, so it should not calculate the count every time.
     *
     * @return the footer item count.
     */
    protected int getFooterItemCount() {
        return (mProgress ? 1 : 0)
            + (mShowError ? 1 : 0);
    }

    /**
     * Gets the content item count. This method can be called several times, so it should not calculate the count every time.
     *
     * @return the content item count.
     */
    protected abstract int getContentItemCount();

    /**
     * This method works exactly the same as {@link #onCreateViewHolder(ViewGroup, int)}, but for header items.
     *
     * @param parent the parent view.
     * @param headerViewType the view type for the header.
     * @return the view holder.
     */
    protected RecyclerView.ViewHolder onCreateHeaderItemViewHolder(ViewGroup parent, int headerViewType) {
        return null;
    }

    /**
     * This method works exactly the same as {@link #onCreateViewHolder(ViewGroup, int)}, but for footer items.
     *
     * @param parent the parent view.
     * @param footerViewType the view type for the footer.
     * @return the view holder.
     */
    protected RecyclerView.ViewHolder onCreateFooterItemViewHolder(ViewGroup parent, int footerViewType) {
        return null;
    }

    /**
     * This method works exactly the same as {@link #onCreateViewHolder(ViewGroup, int)}, but for content items.
     *
     * @param parent the parent view.
     * @param contentViewType the view type for the content.
     * @return the view holder.
     */
    protected abstract RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int contentViewType);

    /**
     * This method works exactly the same as {@link #onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)}, but for header items.
     *
     * @param headerViewHolder the view holder for the header item.
     * @param position the position.
     */
    protected void onBindHeaderItemViewHolder(RecyclerView.ViewHolder headerViewHolder, int position) {

    }

    /**
     * This method works exactly the same as {@link #onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)}, but for footer items.
     *
     * @param footerViewHolder the view holder for the footer item.
     * @param position the position.
     */
    protected void onBindFooterItemViewHolder(RecyclerView.ViewHolder footerViewHolder, int position) {

    }

    /**
     * This method works exactly the same as {@link #onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)}, but for content items.
     *
     * @param contentViewHolder the view holder for the content item.
     * @param position the position.
     */
    protected abstract void onBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder, int position);

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ErrorViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;

        public ErrorViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
            button = (Button) itemView.findViewById(android.R.id.button1);
        }
    }
}
