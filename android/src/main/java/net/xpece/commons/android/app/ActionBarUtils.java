package net.xpece.commons.android.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toolbar;

import net.xpece.commons.android.content.AndroidUtils;
import net.xpece.commons.android.R;
import net.xpece.commons.android.graphics.TintUtils;
import net.xpece.commons.android.view.XpeceViewCompat;

import java.util.ArrayList;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class ActionBarUtils {
  private ActionBarUtils() {}

  /**
   * Provides native ActionBar (e.g. in PreferenceActivity which is only native) with a Material
   * back button.
   * <p/>
   * The {@code android:actionBarStyle} must specify {@code colorControlNormal} which will define
   * icon color.
   * <p/>
   * <i>From API 14 to API 20. Requires appcompat-v7 library.</i>
   *
   * @param activity
   * @param back Optional back button drawable. Defaults to back arrow. Will be colored.
   */
  @TargetApi(14)
  public static void fixActionBarBackButton(Activity activity, Drawable back) {
    if (!AndroidUtils.API_14 || AndroidUtils.API_21) return;

    ActionBar ab = activity.getActionBar();
    if (ab == null) return;

    try {
      Drawable d;
      if (back == null) {
        d = TintUtils.getDrawableWithColorControlNormal(activity.getActionBar().getThemedContext(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
      } else {
        d = TintUtils.getDrawableWithColorControlNormal(activity.getActionBar().getThemedContext(), back);
      }

      int p = activity.getResources().getDimensionPixelOffset(R.dimen.material_unit);
      int width = activity.getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) + p;
      int height = activity.getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_height_material);

      ImageView image;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        ViewGroup home = (ViewGroup) activity.findViewById(android.R.id.home).getParent();
        image = ((ImageView) home.getChildAt(0));
      } else {
        image = ((ImageView) activity.findViewById(R.id.up));
      }
      image.setScaleType(ImageView.ScaleType.CENTER);
      image.setMinimumWidth(width);
      image.setMinimumHeight(height);
      image.setImageDrawable(d);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Native {@link android.app.Activity} more overflow action bar button is always white
   * on {@link android.os.Build.VERSION_CODES#LOLLIPOP}. This fixes it.
   * <p/>
   * <i>Only on API 21.</i>
   *
   * @param activity Activity which toolbar needs fixing.
   * @param more Optional custom more overflow icon.
   */
  @TargetApi(21)
  public static void fixActionBarOverflowButton(final Activity activity, final Drawable more) {
    if (Build.VERSION.SDK_INT == 21) {
      try {
        final int abId = activity.getResources().getIdentifier("android:id/action_bar", null, null);
        final Toolbar toolbar = (Toolbar) activity.getWindow().getDecorView().findViewById(abId);
        final int moreId = activity.getResources().getIdentifier("android:string/action_menu_overflow_description", null, null);

        final Drawable d;
        if (more == null) {
          final int backId = activity.getResources().getIdentifier("android:drawable/ic_menu_moreoverflow_material", null, null);
          d = toolbar.getContext().getResources().getDrawable(backId);
        } else {
          d = TintUtils.getDrawableWithColorControlNormal(toolbar.getContext(), more);
        }

        toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            XpeceViewCompat.removeOnGlobalLayoutListener(toolbar, this);

            final String moreString = activity.getString(moreId);
            final ArrayList<View> out = new ArrayList<>();
            toolbar.findViewsWithText(out, moreString, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
            if (out.isEmpty()) return;
            final ImageButton moreButton = (ImageButton) out.get(0);
            moreButton.setImageDrawable(d);
          }
        });
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
