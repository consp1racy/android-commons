package net.xpece.android.view;

import android.support.annotation.IdRes;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * Extended to support arbitrary objects alongside views. Use negative ids for arbitrary objects.
 * <p/>
 * Sauce: Pierre-Yves Ricau http://www.piwai.info/android-adapter-good-practices/
 */
public class XpViewHolder {
    private static final String TAG = XpViewHolder.class.getSimpleName();

    public static <T> void putObject(View view, int id, T obj) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");

        SparseArray<Object> viewHolder = ensureViewHolder(view);
        viewHolder.put(-id, obj);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObject(View view, int id, T ifNotFound) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive.");

        SparseArray<Object> viewHolder = ensureViewHolder(view);
        return (T) viewHolder.get(-id, ifNotFound);
    }

    public static <T> T getObject(View view, int id) {
        return getObject(view, id , null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, @IdRes int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");

        SparseArray<Object> viewHolder = ensureViewHolder(view);

        Object childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    @SuppressWarnings("unchecked")
    private static SparseArray<Object> ensureViewHolder(View view) {
        SparseArray<Object> viewHolder;
        try {
            viewHolder = (SparseArray<Object>) view.getTag();

            if (viewHolder == null) {
                viewHolder = new SparseArray<>();
                view.setTag(viewHolder);
            }
        } catch (ClassCastException ex) {
            Log.d(TAG, view + " already had a tag. Access it by calling getObject(view, 0).");

            viewHolder = new SparseArray<>();
            viewHolder.put(0, view.getTag());
            view.setTag(viewHolder);
        }
        return viewHolder;
    }
}
