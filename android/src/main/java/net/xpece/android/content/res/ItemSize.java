package net.xpece.android.content.res;

import android.content.Context;
import android.support.annotation.DimenRes;

import net.xpece.commons.android.R;

/**
 * Created by Eugen on 24. 4. 2015.
 */
public abstract class ItemSize {
    public static final ItemSize MATERIAL_SMALL = new ItemSize() {
        @Override
        public int getIconSize(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_icon_size_small);
        }

        @Override
        public int getListItemHeight(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_list_item_height_small);
        }

        @Override
        public int getGridItemWidth(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_grid_item_size_small);
        }
    };

    public static final ItemSize MATERIAL_MEDIUM = new ItemSize() {
        @Override
        public int getIconSize(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_icon_size_medium);
        }

        @Override
        public int getListItemHeight(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_list_item_height_medium);
        }

        @Override
        public int getGridItemWidth(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_grid_item_size_medium);
        }
    };
    public static final ItemSize MATERIAL_LARGE = new ItemSize() {
        @Override
        public int getIconSize(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_icon_size_large);
        }

        @Override
        public int getListItemHeight(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_list_item_height_large);
        }

        @Override
        public int getGridItemWidth(Context context) {
            return getDimensionPixelSize(context, R.dimen.material_grid_item_size_large);
        }
    };

    protected static int getDimensionPixelSize(final Context context, @DimenRes final int resId) {
        return context.getApplicationContext().getResources().getDimensionPixelSize(resId);
    }

    public abstract int getIconSize(Context context);

    public abstract int getListItemHeight(Context context);

    public abstract int getGridItemWidth(Context context);
}
