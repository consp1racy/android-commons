package android.support.v7.view.menu;

import android.app.Activity;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/**
 * @author Eugen on 31. 10. 2015.
 */
@SuppressWarnings("RestrictedApi")
public class XpAppCompatContextMenu {
    private static final WeakHashMap<Window, XpAppCompatContextMenu> MAP = new WeakHashMap<>();

    private static final Method METHOD_IS_DESTROYED;

    static {
        Method isDestroyed = null;
        try {
            isDestroyed = Window.class.getDeclaredMethod("isDestroyed");
            isDestroyed.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        METHOD_IS_DESTROYED = isDestroyed;
    }

    private final WeakReference<Window> mWindow;

    /**
     * Simple callback used by the context menu and its submenus. The options
     * menu submenus do not use this (their behavior is more complex).
     */
    final DialogMenuCallback mContextMenuCallback = new DialogMenuCallback(Window.FEATURE_CONTEXT_MENU);

    XpContextMenuBuilder mContextMenu;
    private MenuDialogHelper mContextMenuHelper;

    private XpAppCompatContextMenu(final Window window) {
        mWindow = new WeakReference<>(window);
    }

    Window getWindow() {
        return mWindow.get();
    }

    boolean isWindowDestroyed() {
        try {
            return (boolean) METHOD_IS_DESTROYED.invoke(getWindow());
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private static XpAppCompatContextMenu getInstance(View view) {
        ContextWrapper context = (ContextWrapper) view.getContext();
        while (!(context instanceof Activity)) {
            context = (ContextWrapper) context.getBaseContext();
        }
        Activity activity = (Activity) context;
        return getInstance(activity.getWindow());
    }

    private static XpAppCompatContextMenu getInstance(Window window) {
        XpAppCompatContextMenu menu = MAP.get(window);
        if (menu == null) {
            menu = new XpAppCompatContextMenu(window);
            MAP.put(window, menu);
        }
        return menu;
    }

    public static void showContextMenu(View v) {
        XpAppCompatContextMenu.getInstance(v).showContextMenuForChild(v);
    }

    public static void showContextMenu(View v, @NonNull ContextMenu.ContextMenuInfo info) {
        XpAppCompatContextMenu.getInstance(v).showContextMenuForChild(v, info);
    }

    public boolean showContextMenuForChild(View originalView) {
        return showContextMenuForChild(originalView, null);
    }

    public boolean showContextMenuForChild(View originalView, ContextMenu.ContextMenuInfo info) {
        // Reuse the context menu builder
        if (mContextMenu == null) {
            mContextMenu = new XpContextMenuBuilder(originalView.getContext());
            mContextMenu.setCallback(mContextMenuCallback);
        } else {
            mContextMenu.clearAll();
        }

        final MenuDialogHelper helper = provideMenuDialogHelper(originalView, info);
        if (helper != null) {
            helper.setPresenterCallback(mContextMenuCallback);
        } else if (mContextMenuHelper != null) {
            // No menu to show, but if we have a menu currently showing it just became blank.
            // Close it.
            mContextMenuHelper.dismiss();
        }
        mContextMenuHelper = helper;
        return helper != null;
    }

    private MenuDialogHelper provideMenuDialogHelper(View originalView, ContextMenu.ContextMenuInfo info) {
        if (info == null) {
            // Use View.getContextMenuInfo().
            return mContextMenu.show(originalView,
                originalView.getWindowToken());
        } else {
            // Use info parameter.
            return mContextMenu.show(originalView,
                originalView.getWindowToken(), info);
        }
    }

    /**
     * Show the context menu for this view. It is not safe to hold on to the
     * menu after returning from this method.
     * <p/>
     * You should normally not overload this method. Overload
     * {@link View#onCreateContextMenu(ContextMenu)} or define an
     * {@link View.OnCreateContextMenuListener} to add items to the context menu.
     *
     * @param menu The context menu to populate
     */
    static void createContextMenu(View view, ContextMenu menu) {
        ContextMenu.ContextMenuInfo menuInfo = XpContextMenuViewCompat.getContextMenuInfo(view);
        createContextMenu(view, menu, menuInfo);
    }

    static void createContextMenu(View view, ContextMenu menu, ContextMenu.ContextMenuInfo menuInfo) {
    // Sets the current menu info so all items added to menu will have
        // my extra info set.
        ((MenuBuilder) menu).setCurrentMenuInfo(menuInfo);

        XpContextMenuViewCompat.onCreateContextMenu(view, menu);
//        ListenerInfo li = mListenerInfo;
//        if (li != null && li.mOnCreateContextMenuListener != null) {
//            li.mOnCreateContextMenuListener.onCreateContextMenu(menu, view, menuInfo);
//        }
        View.OnCreateContextMenuListener li = XpContextMenuViewCompat.getOnCreateContextMenuListener(view);
        if (li != null) {
            li.onCreateContextMenu(menu, view, menuInfo);
        }

        // Clear the extra information so subsequent items that aren't mine don't
        // have my extra info.
        ((MenuBuilder) menu).setCurrentMenuInfo(null);

        if (view.getParent() != null) {
            try {
                createContextMenu((View) view.getParent(), menu);
            } catch (ClassCastException ex) {
                //
            }
        }
    }

    /**
     * Closes the context menu. This notifies the menu logic of the close, along
     * with dismissing it from the UI.
     */
    public synchronized void closeContextMenu() {
        if (mContextMenu != null) {
            mContextMenu.close();
            dismissContextMenu();
        }
    }

    /**
     * Dismisses just the context menu UI. To close the context menu, use
     * {@link #closeContextMenu()}.
     */
    synchronized void dismissContextMenu() {
        mContextMenu = null;

        if (mContextMenuHelper != null) {
            mContextMenuHelper.dismiss();
            mContextMenuHelper = null;
        }
    }

    /**
     * Simple implementation of MenuBuilder.Callback that:
     * <li> Opens a submenu when selected.
     * <li> Calls back to the callback's onMenuItemSelected when an item is
     * selected.
     */
    private final class DialogMenuCallback implements MenuBuilder.Callback, MenuPresenter.Callback {
        private int mFeatureId;
        private MenuDialogHelper mSubMenuHelper;

        public DialogMenuCallback(int featureId) {
            mFeatureId = featureId;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            if (menu.getRootMenu() != menu) {
                onCloseSubMenu(menu);
            }

            if (allMenusAreClosing) {
                Window.Callback callback = getWindow().getCallback();
                if (callback != null && !isWindowDestroyed()) {
                    callback.onPanelClosed(mFeatureId, menu);
                }

                if (menu == mContextMenu) {
                    dismissContextMenu();
                }

                // Dismiss the submenu, if it is showing
                if (mSubMenuHelper != null) {
                    mSubMenuHelper.dismiss();
                    mSubMenuHelper = null;
                }
            }
        }

        public void onCloseSubMenu(MenuBuilder menu) {
            Window.Callback callback = getWindow().getCallback();
            if (callback != null && !isWindowDestroyed()) {
                callback.onPanelClosed(mFeatureId, menu.getRootMenu());
            }
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            Window.Callback callback = getWindow().getCallback();
            return (callback != null && !isWindowDestroyed())
                && callback.onMenuItemSelected(mFeatureId, item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            if (subMenu == null) return false;

            // Set a simple callback for the submenu
            subMenu.setCallback(this);

            // The window manager will give us a valid window token
            mSubMenuHelper = new MenuDialogHelper(subMenu);
            mSubMenuHelper.show(null);

            return true;
        }
    }
}
