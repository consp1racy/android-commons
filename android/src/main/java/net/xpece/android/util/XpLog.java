package net.xpece.android.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;

/**
 * @author Eugen on 11. 2. 2016.
 * @hide
 */
public final class XpLog {
    private XpLog() {}

    public static void logException(Throwable ex, Context context) {
        boolean debug = (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        if (debug) ex.printStackTrace();
    }

    public static void logException(Throwable ex, View view) {
        boolean debug = (view.getContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        if (debug) ex.printStackTrace();
    }
}
