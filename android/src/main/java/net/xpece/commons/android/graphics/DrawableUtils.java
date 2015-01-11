package net.xpece.commons.android.graphics;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;

import net.xpece.commons.android.R;
import net.xpece.commons.android.content.AndroidUtils;

/**
 * Created by pechanecjr on 7. 1. 2015.
 */
public class DrawableUtils {
  private DrawableUtils() {
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static Drawable getDividerDrawable(Context context, boolean doInset) {
    ColorDrawable color = new ColorDrawable(ColorUtils.getDividerColor(context));
    if (doInset) {
      boolean rtl = AndroidUtils.isRtl(context);
      int inset = context.getResources().getDimensionPixelOffset(R.dimen.material_content_inset);
      return new InsetDrawable(color, (rtl ? 0 : inset), 0, (rtl ? inset : 0), 0);
    } else {
      return color;
    }
  }

}
