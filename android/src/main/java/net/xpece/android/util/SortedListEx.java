package net.xpece.android.util;

import android.support.v7.util.SortedList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Eugen on 17.03.2016.
 */
public class SortedListEx<T> extends SortedList<T> {

    private final Class<T> mTClass;

    private final StateCallback<T> mCallback;

    // TODO Make a setter.
    private NotifyChangedStrategy<T> mChangedStrategy = new NotifyChangedStrategy.Move<>();

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

//        notifyChangedEasy(callback, oldSize, newSize);
        notifyChangedMove(callback, oldItems, newItems);
//        notifyChangedFade(callback, oldItems, newItems);

        mCallback.setEnabled(true);
    }

    private static <T> void notifyChangedMove(Callback<T> callback, T[] oldItems, T[] newItems) {
        final int newSize = newItems.length;
        final int oldSize = oldItems.length;

        List<T> working = new LinkedList<>();
        for (T item : oldItems) {
            working.add(item);
        }

        int same = 0;
        outer:
        for (int i = 0; i < newSize; i++) {
            final T newItem = newItems[i];
            for (int j = 0; j < oldSize; j++) {
                final T oldItem = working.get(j);
                if (callback.areItemsTheSame(oldItem, newItem)) {
                    same++;
                    if (!callback.areContentsTheSame(oldItem, newItem)) {
                        callback.onChanged(j, 1);
                    }
                    if (i != j) {
                        callback.onMoved(j, i);

                        working.remove(oldItem);
                        working.add(i, oldItem);
                    }
                    continue outer;
                }
            }
            callback.onInserted(i, 1);
            working.add(i, newItem);
        }
        if (same < oldSize) {
            int extras = oldSize - same;
            callback.onRemoved(newSize, extras);
        }
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

    public interface NotifyChangedStrategy<T3> {
        void notifyChanged(Callback<T3> callback, T3[] oldItems, T3[] newItems);

        /**
         * Fades all items, inserts missing, removes obsolete.
         *
         * @param <T4>
         */
        class FadeAll<T4> implements NotifyChangedStrategy<T4> {
            @Override
            public void notifyChanged(Callback<T4> callback, T4[] oldItems, T4[] newItems) {
                final int newSize = newItems.length;
                final int oldSize = oldItems.length;
                if (oldSize < newSize) {
                    callback.onChanged(0, oldSize);
                    callback.onInserted(oldSize, newSize - oldSize);
                } else if (oldSize > newSize) {
                    callback.onChanged(0, newSize);
                    callback.onRemoved(newSize, oldSize - newSize);
                } else {
                    callback.onChanged(0, newSize);
                }
            }
        }

        /**
         * Keeps items which didn't change position or content, fades the rest, inserts missing, removes obsolete.
         *
         * @param <T4>
         */
        class FadeSelective<T4> implements NotifyChangedStrategy<T4> {
            @Override
            public void notifyChanged(Callback<T4> callback, T4[] oldItems, T4[] newItems) {
                final int newSize = newItems.length;
                final int oldSize = oldItems.length;
                final int smaller = newSize < oldSize ? newSize : oldSize;
                for (int i = 0; i < smaller; i++) {
                    final T4 oldItem = oldItems[i];
                    final T4 newItem = newItems[i];
                    if (callback.areItemsTheSame(oldItem, newItem)) {
                        if (!callback.areContentsTheSame(oldItem, newItem)) {
                            callback.onChanged(i, 1);
                        }
                    } else {
                        callback.onChanged(i, 1);
                    }
                }
                if (newSize > oldSize) {
                    callback.onInserted(oldSize, newSize - oldSize);
                } else if (newSize < oldSize) {
                    callback.onRemoved(newSize, oldSize - newSize);
                }
            }
        }

        class Move<T4> implements NotifyChangedStrategy<T4> {
            @Override
            public void notifyChanged(Callback<T4> callback, T4[] oldItems, T4[] newItems) {
                final int newSize = newItems.length;
                final int oldSize = oldItems.length;

                List<T4> working = new LinkedList<>();
                Collections.addAll(working, oldItems);

                int same = 0;
                outer:
                for (int i = 0; i < newSize; i++) {
                    final T4 newItem = newItems[i];
                    for (int j = 0; j < oldSize; j++) {
                        final T4 oldItem = working.get(j);
                        if (callback.areItemsTheSame(oldItem, newItem)) {
                            same++;
                            if (!callback.areContentsTheSame(oldItem, newItem)) {
                                callback.onChanged(j, 1);
                            }
                            if (i != j) {
                                callback.onMoved(j, i);

                                working.remove(oldItem);
                                working.add(i, oldItem);
                            }
                            continue outer;
                        }
                    }
                    callback.onInserted(i, 1);
                    working.add(i, newItem);
                }
                if (same < oldSize) {
                    int extras = oldSize - same;
                    callback.onRemoved(newSize, extras);
                }
            }
        }
    }
}
