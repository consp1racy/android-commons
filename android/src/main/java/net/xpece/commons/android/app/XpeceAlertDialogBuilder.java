package net.xpece.commons.android.app;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;

import net.xpece.commons.android.R;

/**
 * Created by Eugen on 6. 3. 2015.
 */
public class XpeceAlertDialogBuilder extends AlertDialog.Builder {

    private Context mContext;

    private XpeceAlertDialogBuilder(Context context) {
        super(context);
    }

    @TargetApi(11)
    private XpeceAlertDialogBuilder(Context context, int theme) {
        super(context, theme);
    }

    public static XpeceAlertDialogBuilder get(Context context) {
        return new XpeceAlertDialogBuilder(context);
    }

    public static XpeceAlertDialogBuilder get(Context context, int theme) {
        XpeceAlertDialogBuilder builder;
        if (Build.VERSION.SDK_INT < 11) {
            Context context2 = new ContextThemeWrapper(context, theme == 0 ? android.R.style.Theme_Dialog : theme);
            builder = new XpeceAlertDialogBuilder(context2);
            builder.mContext = context2;
        } else {
            builder = new XpeceAlertDialogBuilder(context, theme);
        }
        return builder;
    }

    public static XpeceAlertDialogBuilder getLightPreHoneycomb(Context context) {
        XpeceAlertDialogBuilder builder;
        if (Build.VERSION.SDK_INT < 11) {
            Context context2 = new ContextThemeWrapper(context, R.style.Theme_Light_Dialog);
            builder = new XpeceAlertDialogBuilder(context2);
            builder.mContext = context2;
        } else {
            builder = new XpeceAlertDialogBuilder(context);
        }
        return builder;
    }

    public Context getSupportContext() {
        if (Build.VERSION.SDK_INT < 11) {
            return mContext;
        } else {
            return super.getContext();
        }
    }
}
