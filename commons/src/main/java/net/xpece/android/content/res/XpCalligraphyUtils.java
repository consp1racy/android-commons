package net.xpece.android.content.res;

import android.content.Context;
import android.widget.TextView;

import net.xpece.android.content.XpContext;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

/**
 * @author Eugen on 20. 4. 2016.
 */
public class XpCalligraphyUtils {

    public static void applyFontToTextView(final TextView titleTextView, final int titleTextAppearance) {
        final Context context = titleTextView.getContext();
        String fontPath = getCalligraphyFontPath(context, titleTextAppearance);
        if (fontPath != null) {
            CalligraphyUtils.applyFontToTextView(context, titleTextView, fontPath);
        }
    }

    public static String getCalligraphyFontPath(final Context context, final int titleTextAppearance) {
        int attrId = CalligraphyConfig.get().getAttrId();
        return XpContext.resolveString(context, titleTextAppearance, attrId);
    }
}
