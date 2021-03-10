package net.xpece.android.view;

import android.view.View;

import java.util.HashSet;
import java.util.Set;

public class OnFocusChangeListenerWrapper implements View.OnFocusChangeListener {

    private final Set<View.OnFocusChangeListener> mListeners = new HashSet<>();

    public static OnFocusChangeListenerWrapper listen(View view) {
        View.OnFocusChangeListener old = view.getOnFocusChangeListener();
        if (old instanceof OnFocusChangeListenerWrapper) {
            // already setup
            return (OnFocusChangeListenerWrapper) old;
        } else {
            OnFocusChangeListenerWrapper listener = new OnFocusChangeListenerWrapper();
            listener.add(old);
            view.setOnFocusChangeListener(listener);
            return listener;
        }
    }

    public static void add(View view, View.OnFocusChangeListener listener) {
        if (view.getOnFocusChangeListener() == null) {
            view.setOnFocusChangeListener(listener);
            return;
        }

        OnFocusChangeListenerWrapper wrapper = listen(view);
        wrapper.add(listener);
    }

    public static void remove(View view, View.OnFocusChangeListener listener) {
        if (view.getOnFocusChangeListener() == listener) {
            view.setOnFocusChangeListener(null);
            return;
        }

        OnFocusChangeListenerWrapper wrapper = listen(view);
        wrapper.remove(listener);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        for (View.OnFocusChangeListener listener : mListeners) {
//            if (listener != null) {
                listener.onFocusChange(v, hasFocus);
//            }
        }
    }

    public OnFocusChangeListenerWrapper add(View.OnFocusChangeListener listener) {
        if (listener != null) {
            mListeners.add(listener);
        }
        return this;
    }

    public OnFocusChangeListenerWrapper remove(View.OnFocusChangeListener listener) {
        if (listener != null) {
            mListeners.remove(listener);
        }
        return this;
    }
}
