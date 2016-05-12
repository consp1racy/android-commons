package net.xpece.android.app;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

/**
 * Often you're unable to use {@link Fragment#getChildFragmentManager()} because you need to manage
 * retained fragments. This means you have to attach them to the {@link android.support.v4.app.FragmentActivity}
 * and havve no reference to the parent fragment. This class will help you set another fragment
 * as a callback listener instead of the activity.
 *
 * @author Eugen on 12. 10. 2015.
 */
@Deprecated
public final class FragmentCallbacksHelper {
    private static final String TAG = FragmentCallbacksHelper.class.getSimpleName();
    public static final String KEY_CALLBACKS_FRAGMENT_ID = TAG + ".CALLBACKS_FRAGMENT_ID";
    public static final String KEY_CALLBACKS_FRAGMENT_TAG = TAG + ".CALLBACKS_FRAGMENT_TAG";

    private FragmentCallbacksHelper() {
    }

    /**
     * <pre>{@code
     * public void onAttach(final Context context) {
     *     super.onAttach(context);
     *     if (!FragmentCallbacksHelper.overrideCallbacks(this)) {
     *         mCallbacks = (Callbacks) context;
     *     }
     * }
     * }</pre>
     *
     * @param provider Fragment whose callbacks to override.
     * @return Whether callbacks were overridden.
     */
    @SuppressWarnings("unchecked")
    public static <C, F extends Fragment & ICanOverrideCallbacks<C>> boolean overrideCallbacks(F provider) {
        Bundle args = provider.getArguments();

        String parentTag = args.getString(KEY_CALLBACKS_FRAGMENT_TAG);
        if (parentTag != null) {
            final C fragment = (C) provider.getFragmentManager().findFragmentByTag(parentTag);
            if (fragment != null) {
                provider.setCallbacks(fragment);
                return true;
            }
        }

        int parentId = args.getInt(KEY_CALLBACKS_FRAGMENT_ID, 0);
        if (parentId != 0) {
            final C fragment = (C) provider.getFragmentManager().findFragmentById(parentId);
            if (fragment != null) {
                provider.setCallbacks(fragment);
                return true;
            }
        }

        return false;
    }

    private static void updateArguments(Fragment fragment, @IdRes int callbacksFragmentId, String callbacksFragmentTag) {
        Bundle args = fragment.getArguments();
//        if (args == null) {
//            args = new Bundle();
//            fragment.setArguments(args);
//        }
        args.putInt(KEY_CALLBACKS_FRAGMENT_ID, callbacksFragmentId);
        args.putString(KEY_CALLBACKS_FRAGMENT_TAG, callbacksFragmentTag);
    }

    /**
     * @param fragment Who's callback object will be set.
     * @param callbacksFragment Who will receive callbacks instead of the Activity.
     */
    public static <C extends Fragment, F extends Fragment & ICanOverrideCallbacks> void updateArguments(F fragment, C callbacksFragment) {
        updateArguments(fragment, callbacksFragment.getId(), callbacksFragment.getTag());
    }

    public interface ICanOverrideCallbacks<C> {
        void setCallbacks(C callbacks);
    }
}
