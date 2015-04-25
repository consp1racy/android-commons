package net.xpece.android.widget;

import android.content.Context;

import net.xpece.commons.android.R;

/**
 * Created by Eugen on 24. 4. 2015.
 */
public enum ItemSize {
    SMALL {
        @Override
        public int getIconSize(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_icon_size_small);
        }

        @Override
        public int getListItemHeight(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_list_item_height_small);
        }

        @Override
        public int getGridItemWidth(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_grid_item_size_small);
        }

        @Override
        public int getGridItemWidthLarge(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_grid_item_size_small2);
        }
    }, MEDIUM {
        @Override
        public int getIconSize(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_icon_size_medium);
        }

        @Override
        public int getListItemHeight(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_list_item_height_medium);
        }

        @Override
        public int getGridItemWidth(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_grid_item_size_medium);
        }

        @Override
        public int getGridItemWidthLarge(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_grid_item_size_medium2);
        }
    }, LARGE {
        @Override
        public int getIconSize(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_icon_size_large);
        }

        @Override
        public int getListItemHeight(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_list_item_height_large);
        }

        @Override
        public int getGridItemWidth(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_grid_item_size_large);
        }

        @Override
        public int getGridItemWidthLarge(Context context) {
            return context.getResources().getDimensionPixelSize(R.dimen.material_grid_item_size_large2);
        }
    };

    public abstract int getIconSize(Context context);

    public abstract int getListItemHeight(Context context);

    public abstract int getGridItemWidth(Context context);

    public abstract int getGridItemWidthLarge(Context context);
}
