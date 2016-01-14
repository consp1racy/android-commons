package net.xpece.android.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import net.xpece.android.R;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public final class Intents {
    /**
     * Attempt to launch the supplied {@link Intent}. Queries on-device packages before launching and
     * will display a simple message if none are available to handle it.
     */
    public static boolean maybeStartActivity(Context context, Intent intent) {
        return maybeStartActivity(context, intent, false);
    }

    /**
     * Attempt to launch Android's chooser for the supplied {@link Intent}. Queries on-device
     * packages before launching and will display a simple message if none are available to handle
     * it.
     */
    public static boolean maybeStartChooser(Context context, Intent intent) {
        return maybeStartActivity(context, intent, true);
    }

    private static boolean maybeStartActivity(Context context, Intent intent, boolean chooser) {
        if (hasHandler(context, intent)) {
            if (chooser) {
                intent = Intent.createChooser(intent, null);
            }
            context.startActivity(intent);
            return true;
        } else {
//            showNoActivityError(context);
            return false;
        }
    }

    public static boolean maybeStartActivityForResult(Activity activity, Intent intent, int requestCode) {
        if (hasHandler(activity, intent)) {
            activity.startActivityForResult(intent, requestCode);
            return true;
        } else {
//            showNoActivityError(activity);
            return false;
        }
    }

    public static boolean maybeStartActivityForResult(Fragment fragment, Intent intent, int requestCode) {
        Context context = fragment.getContext();
        if (hasHandler(context, intent)) {
            fragment.startActivityForResult(intent, requestCode);
            return true;
        } else {
//            showNoActivityError(context);
            return false;
        }
    }

    public static void showNoActivityError(final Context context) {
        Toast.makeText(context, R.string.xpc_no_intent_handler, LENGTH_LONG).show();
    }

    /**
     * Queries on-device packages for a handler for the supplied {@link Intent}.
     */
    public static boolean hasHandler(Context context, Intent intent) {
        List<ResolveInfo> handlers = context.getPackageManager().queryIntentActivities(intent, 0);
        return !handlers.isEmpty();
    }

    private Intents() {
        throw new AssertionError("No instances.");
    }
}
