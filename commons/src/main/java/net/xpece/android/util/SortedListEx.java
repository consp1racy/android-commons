package net.xpece.android.util;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.SortedList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SortedListEx<T> extends SortedList<T> {

    private final Class<T> mTClass;

    private final StateCallback<T> mCallback;

    public static <T> SortedListEx<T> newInstance(Class<T> klass, Callback<T> callback) {
        StateCallback<T> stateCallback = new StateCallback<>(callback);
        return new SortedListEx<>(klass, stateCallback);
    }

    public static <T> SortedListEx<T> newInstance(Class<T> klass, Callback<T> callback, int initialCapacity) {
        StateCallback<T> stateCallback = new StateCallback<>(callback);
        return new SortedListEx<>(klass, stateCallback, initialCapacity);
    }

    private SortedListEx(Class<T> klass, StateCallback<T> callback) {
        super(klass, callback);
        mTClass = klass;
        mCallback = callback;
    }

    private SortedListEx(Class<T> klass, StateCallback<T> callback, int initialCapacity) {
        super(klass, callback, initialCapacity);
        mTClass = klass;
        mCallback = callback;
    }

    @SuppressWarnings("unchecked")
    public T[] asArray() {
        final int size = size();
        final T[] items = (T[]) Array.newInstance(mTClass, size);
        for (int i = 0; i < size; i++) {
            items[i] = get(i);
        }
        return items;
    }

    public List<T> asList() {
        final int size = size();
        final ArrayList<T> items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            items.add(get(i));
        }
        return items;
    }

    @SuppressWarnings("unchecked")
    public void set(Collection<T> items) {
        T[] copy = (T[]) Array.newInstance(mTClass, items.size());
        items.toArray(copy);
        set(copy);
    }

    @SafeVarargs
    public final void set(final T... items) {
        mCallback.setEnabled(false);

        final Callback<T> callback = mCallback.getWrappedCallback();

        final T[] oldItems = asArray();

        clear();
        addAll(items);

        // Continue with the sorted array you fool!
        T[] newItems = asArray();

        notifyChanged(callback, oldItems, newItems, true);

        mCallback.setEnabled(true);
    }

    private static <T> void notifyChanged(final Callback<T> callback, final T[] oldItems, final T[] newItems, final boolean move) {
        final DiffUtil.DiffResult diff = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldItems.length;
            }

            @Override
            public int getNewListSize() {
                return newItems.length;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                final T item1 = oldItems[oldItemPosition];
                final T item2 = newItems[newItemPosition];
                return callback.areItemsTheSame(item1, item2);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                final T item1 = oldItems[oldItemPosition];
                final T item2 = newItems[newItemPosition];
                return callback.areContentsTheSame(item1, item2);
            }
        }, move);

        diff.dispatchUpdatesTo(callback);
    }

    private static class StateCallback<T2> extends Callback<T2> {

        private final Callback<T2> mWrappedCallback;

        private boolean mEnabled;

        public StateCallback(Callback<T2> wrappedCallback) {
            mWrappedCallback = wrappedCallback;
            mEnabled = true;
        }

        public void setEnabled(boolean enabled) {
            mEnabled = enabled;
        }

        public boolean isEnabled() {
            return mEnabled;
        }

        public Callback<T2> getWrappedCallback() {
            return mWrappedCallback;
        }

        @Override
        public int compare(T2 o1, T2 o2) {
            return mWrappedCallback.compare(o1, o2);
        }

        @Override
        public void onInserted(int position, int count) {
            if (mEnabled) {
                mWrappedCallback.onInserted(position, count);
            }
        }

        @Override
        public void onRemoved(int position, int count) {
            if (mEnabled) {
                mWrappedCallback.onRemoved(position, count);
            }
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            if (mEnabled) {
                mWrappedCallback.onMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onChanged(int position, int count) {
            if (mEnabled) {
                mWrappedCallback.onChanged(position, count);
            }
        }

        @Override
        public boolean areContentsTheSame(T2 oldItem, T2 newItem) {
            return mWrappedCallback.areContentsTheSame(oldItem, newItem);
        }

        @Override
        public boolean areItemsTheSame(T2 item1, T2 item2) {
            return mWrappedCallback.areItemsTheSame(item1, item2);
        }
    }
}
