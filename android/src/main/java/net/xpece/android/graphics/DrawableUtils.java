package net.xpece.android.graphics;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.StateSet;

import net.xpece.android.AndroidUtils;
import net.xpece.commons.android.R;

/**
 * Created by pechanecjr on 7. 1. 2015.
 */
@Deprecated
public class DrawableUtils {
    private DrawableUtils() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Drawable getDividerDrawable(Context context, boolean doInset) {
        ColorDrawable color = new ColorDrawable(XpColorUtils.getDividerColor(context));
        if (doInset) {
            boolean rtl = AndroidUtils.isRtl(context);
            int inset = context.getResources().getDimensionPixelOffset(R.dimen.material_content_keyline);
            return new InsetDrawable(color, (rtl ? 0 : inset), 0, (rtl ? inset : 0), 0);
        } else {
            return color;
        }
    }

    public static Drawable getDisabledDrawable(Context context, @DrawableRes int did, @ColorRes int cid) {
        Drawable d = ContextCompat.getDrawable(context, did);
        int c = ContextCompat.getColor(context, cid);
        return getDisabledDrawable(d, c);
    }

    public static Drawable getDisabledDrawable(Drawable d, int color) {
        byte alpha;
        if (XpColorUtils.isLightColor(color)) {
            alpha = (int) (0.5f + 255 * 0.3f);
        } else {
            alpha = (int) (0.5f + 255 * 0.26f);
        }
        int disabledColor = XpColorUtils.setColorAlpha(color, alpha);

        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{-android.R.attr.state_enabled}, XpTintManager.getDrawable(d.getConstantState().newDrawable(), disabledColor));
        sld.addState(StateSet.WILD_CARD, XpTintManager.getDrawable(d.getConstantState().newDrawable(), color));
        return sld;
    }

}
