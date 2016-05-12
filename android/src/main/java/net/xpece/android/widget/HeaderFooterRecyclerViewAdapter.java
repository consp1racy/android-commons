package net.xpece.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.xpece.android.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class HeaderFooterRecyclerViewAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @IntDef({NO_VIEW_TYPE, PROGRESS_VIEW_TYPE, ERROR_VIEW_TYPE, MESSAGE_VIEW_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface StateView {}

    private static final int VIEW_TYPE_MAX_COUNT = 0xffff;
    private static final int HEADER_VIEW_TYPE_OFFSET = 0;
    private static final int FOOTER_VIEW_TYPE_OFFSET = HEADER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT;
    private static final int CONTENT_VIEW_TYPE_OFFSET = FOOTER_VIEW_TYPE_OFFSET + VIEW_TYPE_MAX_COUNT;

    private static final int NO_VIEW_TYPE = Integer.MAX_VALUE;
    private static final int PROGRESS_VIEW_TYPE = Integer.MAX_VALUE - 1;
    private static final int ERROR_VIEW_TYPE = Integer.MAX_VALUE - 2;
    private static final int MESSAGE_VIEW_TYPE = Integer.MAX_VALUE - 3;

    @StateView private int mStateView = NO_VIEW_TYPE;

    private String mErrorText = null;
    private String mErrorButtonText = null;
    private int mErrorTextId = 0;
    private int mErrorButtonTextId = 0;
    private View.OnClickListener mErrorOnClickListener = null;

    private String mMessageText = null;
    private int mMessageTextId = 0;

    @SuppressLint("SwitchIntDef")
    public void showProgress() {
        @StateView int stateView = mStateView;
        switch (stateView) {
            case PROGRESS_VIEW_TYPE: {
                break;
            }
            case NO_VIEW_TYPE: {
                mStateView = PROGRESS_VIEW_TYPE;
                notifyRealFooterItemRangeInserted(0, 1);
                break;
            }
            default: {
                mStateView = PROGRESS_VIEW_TYPE;
                notifyRealFooterItemRangeChanged(0, 1);
                break;
            }
        }
    }

    @SuppressLint("SwitchIntDef")
    public void hideProgress() {
        @StateView int stateView = mStateView;
        switch (stateView) {
            case PROGRESS_VIEW_TYPE: {
                mStateView = NO_VIEW_TYPE;
                notifyRealFooterItemRangeRemoved(0, 1);
                break;
            }
        }
    }

    public void showError(String text, String button, View.OnClickListener onClickListener) {
        mErrorText = text;
        mErrorTextId = 0;
        mErrorButtonText = button;
        mErrorButtonTextId = 0;
        mErrorOnClickListener = onClickListener;

        showErrorInternal();
    }

    public void showError(@StringRes int text, @StringRes int button, View.OnClickListener onClickListener) {
        mErrorText = null;
        mErrorTextId = text;
        mErrorButtonText = null;
        mErrorButtonTextId = button;
        mErrorOnClickListener = onClickListener;

        showErrorInternal();
    }

    @SuppressLint("SwitchIntDef")
    private void showErrorInternal() {
        @StateView int stateView = mStateView;
        switch (stateView) {
            case NO_VIEW_TYPE: {
                mStateView = ERROR_VIEW_TYPE;
                notifyRealFooterItemRangeInserted(0, 1);
                break;
            }
            default: {
                mStateView = ERROR_VIEW_TYPE;
                notifyRealFooterItemRangeChanged(0, 1);
                break;
            }
        }
    }

    @SuppressLint("SwitchIntDef")
    public void hideError() {
        @StateView int stateView = mStateView;
        switch (stateView) {
            case ERROR_VIEW_TYPE: {
                mStateView = NO_VIEW_TYPE;
                mErrorText = null;
                mErrorTextId = 0;
                mErrorButtonText = null;
                mErrorButtonTextId = 0;
                mErrorOnClickListener = null;
                notifyRealFooterItemRangeRemoved(0, 1);
            }
        }
    }

    public void showMessage(String message) {
        mMessageText = message;
        mMessageTextId = 0;

        showMessageInternal();
    }

    public void showMessage(@StringRes int message) {
        mMessageText = null;
        mMessageTextId = message;

        showMessageInternal();
    }

    @SuppressLint("SwitchIntDef")
    private void showMessageInternal() {
        @StateView int stateView = mStateView;
        switch (stateView) {
            case NO_VIEW_TYPE: {
                mStateView = MESSAGE_VIEW_TYPE;
                notifyRealFooterItemRangeInserted(0, 1);
                break;
            }
            default: {
                mStateView = MESSAGE_VIEW_TYPE;
                notifyRealFooterItemRangeChanged(0,1);
                break;
            }
        }
    }

    @SuppressLint("SwitchIntDef")
    public void hideMessage() {
        @StateView int stateView = mStateView;
        switch (stateView) {
            case MESSAGE_VIEW_TYPE: {
                mStateView = NO_VIEW_TYPE;
                mMessageText = null;
                mMessageTextId = 0;
                notifyRealFooterItemRangeRemoved(0, 1);
            }
        }
    }

    public void hideAny() {
        @StateView int stateView = mStateView;
        if (stateView != NO_VIEW_TYPE) {
            mStateView = NO_VIEW_TYPE;
            notifyRealFooterItemRangeRemoved(0, 1);
        }
    }

    public void hideAnyExceptProgress() {
        @StateView int stateView = mStateView;
        if (stateView != PROGRESS_VIEW_TYPE) {
            hideAny();
        }
    }

    public final boolean isItemHeader(int adapterPosition) {
        int headerItemCount = getHeaderItemCount();
        return headerItemCount > 0
            && adapterPosition < headerItemCount;
    }

    public final boolean isItemContent(int adapterPosition) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        return contentItemCount > 0
            && adapterPosition < headerItemCount + contentItemCount
            && adapterPosition >= headerItemCount;
    }

    public final boolean isItemFooter(int adapterPosition) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getFooterItemCount();
        return footerItemCount > 0
            && adapterPosition < headerItemCount + contentItemCount + footerItemCount
            && adapterPosition >= headerItemCount + contentItemCount;
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
        } else if (viewType == MESSAGE_VIEW_TYPE) {
            return onCreateMessageItemViewHolder(parent);
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
        return new TextWithButtonViewHolder(view);
    }

    private RecyclerView.ViewHolder onCreateMessageItemViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.xpc_text, parent, false);
        return new TextViewHolder(view);
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
        } else if (position == headerItemCount + contentItemCount) {
            if (mStateView == ERROR_VIEW_TYPE) {
                onBindErrorItemViewHolder((TextWithButtonViewHolder) viewHolder);
            } else if (mStateView == MESSAGE_VIEW_TYPE) {
                onBindMessageItemViewHolder((TextViewHolder) viewHolder);
            } else if (mStateView == PROGRESS_VIEW_TYPE) {
                // Do nothing.
            } else {
                onBindFooterItemViewHolder(viewHolder, position - headerItemCount - contentItemCount);
            }
        } else {
            onBindFooterItemViewHolder(viewHolder, position - headerItemCount - contentItemCount);
        }
    }

    private void onBindErrorItemViewHolder(TextWithButtonViewHolder holder) {
        if (mErrorTextId != 0) {
            holder.textView.setText(mErrorTextId);
        } else {
            holder.textView.setText(mErrorText);
        }
        if (mErrorButtonTextId != 0) {
            holder.button.setText(mErrorButtonTextId);
        } else {
            holder.button.setText(mErrorButtonText);
        }
        holder.button.setOnClickListener(mErrorOnClickListener);
    }

    private void onBindMessageItemViewHolder(TextViewHolder holder) {
        if (mMessageTextId != 0) {
            holder.textView.setText(mMessageTextId);
        } else {
            holder.textView.setText(mMessageText);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getItemCount() {
        // Cache the counts and return the sum of them.
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        int footerItemCount = getRealFooterItemCount();
        return headerItemCount + contentItemCount + footerItemCount;
    }

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
                if (mStateView != NO_VIEW_TYPE) {
                    return mStateView;
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
        notifyHeaderItemRangeInserted(position, 1);
    }

    /**
     * Notifies that multiple header items are inserted.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyHeaderItemRangeInserted(int positionStart, int itemCount) {
        notifyItemRangeInserted(positionStart, itemCount);
    }

    /**
     * Notifies that a header item is changed.
     *
     * @param position the position.
     */
    public final void notifyHeaderItemChanged(int position) {
        notifyHeaderItemRangeChanged(position, 1);
    }

    /**
     * Notifies that multiple header items are changed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyHeaderItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    /**
     * Notifies that an existing header item is moved to another position.
     *
     * @param fromPosition the original position.
     * @param toPosition the new position.
     */
    public void notifyHeaderItemMoved(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * Notifies that a header item is removed.
     *
     * @param position the position.
     */
    public void notifyHeaderItemRemoved(int position) {
        notifyHeaderItemRangeRemoved(position, 1);
    }

    /**
     * Notifies that multiple header items are removed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public void notifyHeaderItemRangeRemoved(int positionStart, int itemCount) {
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    /**
     * Notifies that a content item is inserted.
     *
     * @param position the position of the content item.
     */
    public final void notifyContentItemInserted(int position) {
        notifyContentItemRangeInserted(position, 1);
    }

    /**
     * Notifies that multiple content items are inserted.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyContentItemRangeInserted(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        notifyItemRangeInserted(positionStart + headerItemCount, itemCount);
    }

    /**
     * Notifies that a content item is changed.
     *
     * @param position the position.
     */
    public final void notifyContentItemChanged(int position) {
        notifyContentItemRangeChanged(position, 1);
    }

    /**
     * Notifies that multiple content items are changed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyContentItemRangeChanged(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
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
        notifyItemMoved(fromPosition + headerItemCount, toPosition + headerItemCount);
    }

    /**
     * Notifies that a content item is removed.
     *
     * @param position the position.
     */
    public final void notifyContentItemRemoved(int position) {
        notifyContentItemRangeRemoved(position, 1);
    }

    /**
     * Notifies that multiple content items are removed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyContentItemRangeRemoved(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        notifyItemRangeRemoved(positionStart + headerItemCount, itemCount);
    }

    /**
     * Notifies that a footer item is inserted.
     *
     * @param position the position of the content item.
     */
    public final void notifyFooterItemInserted(int position) {
        notifyFooterItemRangeInserted(position, 1);
    }

    /**
     * Notifies that multiple footer items are inserted.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyFooterItemRangeInserted(int positionStart, int itemCount) {
        int offset = getFooterOffset();
        notifyRealFooterItemRangeInserted(positionStart + offset, itemCount);
    }

    private void notifyRealFooterItemRangeInserted(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        notifyItemRangeInserted(positionStart + headerItemCount + contentItemCount, itemCount);
    }

    /**
     * Notifies that a footer item is changed.
     *
     * @param position the position.
     */
    public final void notifyFooterItemChanged(int position) {
        notifyFooterItemRangeChanged(position, 1);
    }

    /**
     * Notifies that multiple footer items are changed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyFooterItemRangeChanged(int positionStart, int itemCount) {
        int offset = getFooterOffset();
        notifyRealFooterItemRangeChanged(positionStart + offset, itemCount);
    }

    private void notifyRealFooterItemRangeChanged(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        notifyItemRangeChanged(positionStart + headerItemCount + contentItemCount, itemCount);
    }

    /**
     * Notifies that an existing footer item is moved to another position.
     *
     * @param fromPosition the original position.
     * @param toPosition the new position.
     */
    public final void notifyFooterItemMoved(int fromPosition, int toPosition) {
        int offset = getFooterOffset();
        notifyRealFooterItemMoved(fromPosition + offset, toPosition + offset);
    }

    private void notifyRealFooterItemMoved(int fromPosition, int toPosition) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        notifyItemMoved(headerItemCount + contentItemCount + fromPosition, toPosition + headerItemCount + contentItemCount);
    }

    /**
     * Notifies that a footer item is removed.
     *
     * @param position the position.
     */
    public final void notifyFooterItemRemoved(int position) {
        notifyFooterItemRangeRemoved(position, 1);
    }

    /**
     * Notifies that multiple footer items are removed.
     *
     * @param positionStart the position.
     * @param itemCount the item count.
     */
    public final void notifyFooterItemRangeRemoved(int positionStart, int itemCount) {
        int offset = getFooterOffset();
        notifyRealFooterItemRangeRemoved(positionStart + offset, itemCount);
    }

    private void notifyRealFooterItemRangeRemoved(int positionStart, int itemCount) {
        int headerItemCount = getHeaderItemCount();
        int contentItemCount = getContentItemCount();
        notifyItemRangeRemoved(positionStart + headerItemCount + contentItemCount, itemCount);
    }

    private int getFooterOffset() {
        return mStateView == NO_VIEW_TYPE ? 0 : 1;
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
    public int getHeaderItemCount() {
        return 0;
    }

    /**
     * Gets the footer item count. This method can be called several times, so it should not calculate the count every time.
     *
     * @return the footer item count.
     */
    public int getFooterItemCount() {
        return 0;
    }

    public final int getRealFooterItemCount() {
        return getFooterItemCount() + (mStateView != NO_VIEW_TYPE ? 1 : 0);
    }

    /**
     * Gets the content item count. This method can be called several times, so it should not calculate the count every time.
     *
     * @return the content item count.
     */
    public abstract int getContentItemCount();

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

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public TextViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public static class TextWithButtonViewHolder extends TextViewHolder {
        public Button button;

        public TextWithButtonViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(android.R.id.button1);
        }
    }
}
