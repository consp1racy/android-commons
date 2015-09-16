package net.xpece.android.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Eugen on 26.08.2015.
 */
public abstract class ExpandableRecyclerViewAdapter<T extends Expandable<?>>
    extends HeaderFooterRecyclerViewAdapter {

    private static final int TYPE_PRIMARY_REGULAR = 0;
    private static final int TYPE_PRIMARY_EXPANDABLE = 1;
    private static final int TYPE_SECONDARY_REGULAR = 2;

    private final ArrayList<T> mCategories = new ArrayList<>();

    // caches for numbers
    private int mCategoryCount = 0;
    private boolean[] mExpandedCategories = new boolean[0];
    private boolean[] mExpandableCategories = new boolean[0];
    private int[] mCategoryPositions = new int[0];
    private int[] mCategorySizes = new int[0];

    public void setItems(Collection<T> items) {
        mCategories.clear();
        mCategories.addAll(items);
        int size = mCategories.size();

        mCategoryCount = size;
        mExpandedCategories = new boolean[size]; // none by default
        mExpandableCategories = new boolean[size]; // we'll find out
        mCategorySizes = new int[size];
        mCategoryPositions = new int[size]; // very dynamic, but at first very sequential

        for (int i = 0; i < size; i++) {
            T category = mCategories.get(i);
            boolean expandable = category.canExpand();
            mExpandableCategories[i] = expandable;
            if (expandable) {
                mCategorySizes[i] = category.getChildren().size();
            } else {
                mCategorySizes[i] = 0;
            }
        }
        if (size > 0) {
            recalculateCategoryPositions(0); // safer than a default sequence
        }

        notifyDataSetChanged();
    }

    public ArrayList<T> getItems() {
        return mCategories;
    }

    @Override
    protected int getContentItemViewType(int position) {
        for (int i = 0, size = mCategoryPositions.length; i < size; i++) {
            int start = mCategoryPositions[i];
            if (position == start) {
                if (mExpandableCategories[i]) {
                    return TYPE_PRIMARY_EXPANDABLE;
                } else {
                    return TYPE_PRIMARY_REGULAR;
                }
            }
        }
        return TYPE_SECONDARY_REGULAR;
    }

    @Override
    protected int getContentItemCount() {
        int categories = mCategoryCount;
        int subcategories = 0;
        for (int i = 0; i < categories; i++) {
            if (mExpandedCategories[i]) {
                subcategories += mCategorySizes[i];
            }
        }
        return categories + subcategories;
    }

    @Override
    protected final RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        switch (contentViewType) {
            case TYPE_PRIMARY_REGULAR:
                return onCreatePrimaryRegularItemViewHolder(parent);
            case TYPE_PRIMARY_EXPANDABLE:
                return onCreatePrimaryExpandableItemViewHolder(parent);
            case TYPE_SECONDARY_REGULAR:
                return onCreateSecondaryRegularItemViewHolder(parent);
            default:
                return null;
        }
    }

    public abstract RecyclerView.ViewHolder onCreatePrimaryExpandableItemViewHolder(ViewGroup parent);

    public abstract RecyclerView.ViewHolder onCreatePrimaryRegularItemViewHolder(ViewGroup parent);

    public abstract RecyclerView.ViewHolder onCreateSecondaryRegularItemViewHolder(ViewGroup parent);

    @Override
    protected final void onBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder, int position) {
        int categoryIndex = getCategoryIndex(position);
        int categoryPosition = mCategoryPositions[categoryIndex];

        T category = mCategories.get(categoryIndex);
        if (position == categoryPosition) { // primary category
            if (mExpandableCategories[categoryIndex]) {
                onBindPrimaryExpandableItemViewHolder(contentViewHolder, category, categoryIndex);
            } else {
                onBindPrimaryRegularItemViewHolder(contentViewHolder, category);
            }
        } else { // subcategory
            int subcategoryIndex = position - categoryPosition - 1;
            category = (T) category.getChildren().get(subcategoryIndex);
            onBindSecondaryRegularItemViewHolder(contentViewHolder, category);
        }
    }

    public abstract void onBindPrimaryExpandableItemViewHolder(RecyclerView.ViewHolder holder, T category, int categoryIndex);

    public abstract void onBindPrimaryRegularItemViewHolder(RecyclerView.ViewHolder holder, T category);

    public abstract void onBindSecondaryRegularItemViewHolder(RecyclerView.ViewHolder holder, T category);

    public void expandCategory(int categoryIndex) {
        if (!mExpandedCategories[categoryIndex]) {
            mExpandedCategories[categoryIndex] = true;
            recalculateCategoryPositions(categoryIndex);
            int start = mCategoryPositions[categoryIndex];
            int size = mCategories.get(categoryIndex).getChildren().size();
            notifyContentItemRangeInserted(start + 1, size);
            notifyContentItemChanged(start);
        }
    }

    public void collapseCategory(int categoryIndex) {
        if (mExpandedCategories[categoryIndex]) {
            int start = mCategoryPositions[categoryIndex];
            int size = mCategories.get(categoryIndex).getChildren().size();
            notifyContentItemRangeRemoved(start + 1, size);
            notifyContentItemChanged(start);
            mExpandedCategories[categoryIndex] = false;
            recalculateCategoryPositions(categoryIndex);
        }
    }

    public boolean isCategoryExpanded(int categoryIndex) {
        return mExpandedCategories[categoryIndex];
    }

    public int getCategoryPosition(int categoryIndex) {
        return mCategoryPositions[categoryIndex];
    }

    public int getCategorySize(int categoryIndex) {
        return mCategorySizes[categoryIndex];
    }

    private void recalculateCategoryPositions(int fromCategory) {
        int position = mCategoryPositions[fromCategory];
        for (int i = fromCategory + 1, size = mCategoryCount; i < size; i++) {
            position++;
            if (mExpandedCategories[i - 1]) {
                T prevCat = mCategories.get(i - 1);
                int prevSize = prevCat.canExpand() ? prevCat.getChildren().size() : 0;
                position += prevSize;
            }
            mCategoryPositions[i] = position;
        }
    }

    private int getCategoryIndex(int position) {
        int size = mCategoryPositions.length;
        for (int i = 0; i < size; i++) {
            if (mCategoryPositions[i] > position) return i - 1;
        }
        return size - 1;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        private View.OnClickListener onClickListener;

        public TextViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            onClickListener = listener;
            textView.setOnClickListener(listener);
        }

        public View.OnClickListener getOnClickListener() {
            return onClickListener;
        }
    }
}
