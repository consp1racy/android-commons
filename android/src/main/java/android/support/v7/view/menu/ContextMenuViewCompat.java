package android.support.v7.view.menu;

import android.view.ContextMenu;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Eugen on 31. 10. 2015.
 */
class ContextMenuViewCompat {

    private static final Method METHOD_GET_CONTEXT_MENU_INFO;
    private static final Method METHOD_ON_CREATE_CONTEXT_MENU;
    private static final Field FIELD_LISTENER_INFO;
    private static final Field FIELD_ON_CREATE_CONTEXT_MENU_LISTENER;

    static {
        Method getContextMenuInfo = null;
        try {
            getContextMenuInfo = View.class.getDeclaredMethod("getContextMenuInfo");
            getContextMenuInfo.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        METHOD_GET_CONTEXT_MENU_INFO = getContextMenuInfo;

        Method onCreateContextMenu = null;
        try {
            onCreateContextMenu = View.class.getDeclaredMethod("onCreateContextMenu", ContextMenu.class);
            onCreateContextMenu.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        METHOD_ON_CREATE_CONTEXT_MENU = onCreateContextMenu;

        Field listenerInfo = null;
        try {
            listenerInfo = View.class.getDeclaredField("mListenerInfo");
            listenerInfo.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        FIELD_LISTENER_INFO = listenerInfo;

        Field onCreateContextMenuListener = null;
        try {
            Class listenerInfoCls = Class.forName("android.view.View$ListenerInfo");
            onCreateContextMenuListener = listenerInfoCls.getDeclaredField("mOnCreateContextMenuListener");
            onCreateContextMenuListener.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        FIELD_ON_CREATE_CONTEXT_MENU_LISTENER = onCreateContextMenuListener;
    }

    public static ContextMenu.ContextMenuInfo getContextMenuInfo(View view) {
        try {
            return (ContextMenu.ContextMenuInfo) METHOD_GET_CONTEXT_MENU_INFO.invoke(view);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void onCreateContextMenu(View view, ContextMenu contextMenu) {
        try {
            METHOD_ON_CREATE_CONTEXT_MENU.invoke(view, contextMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static View.OnCreateContextMenuListener getOnCreateContextMenuListener(View view) {
        try {
            Object listenerInfo = FIELD_LISTENER_INFO.get(view);
            if (listenerInfo != null) {
                return (View.OnCreateContextMenuListener) FIELD_ON_CREATE_CONTEXT_MENU_LISTENER.get(listenerInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
