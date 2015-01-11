package net.xpece.commons.android.view;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.xpece.commons.android.content.AndroidUtils;
import net.xpece.commons.android.R;

import java.lang.reflect.Field;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class XpeceViewCompat {
  private XpeceViewCompat() {}

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static void setBackground(android.view.View v, Drawable d) {
    if (Build.VERSION.SDK_INT < 16) {
      v.setBackgroundDrawable(d);
    } else {
      v.setBackground(d);
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static void removeOnGlobalLayoutListener(android.view.View v, ViewTreeObserver.OnGlobalLayoutListener l) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
      v.getViewTreeObserver().removeGlobalOnLayoutListener(l);
    } else {
      v.getViewTreeObserver().removeOnGlobalLayoutListener(l);
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static void setCompoundDrawablesRelative(TextView tv, Drawable start, Drawable top, Drawable end, Drawable bottom) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
      tv.setCompoundDrawables(start, top, end, bottom);
    } else {
      tv.setCompoundDrawablesRelative(start, top, end, bottom);
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView tv, Drawable start, Drawable top, Drawable end, Drawable bottom) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
      tv.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
    } else {
      tv.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public static void setSearchViewLayoutTransition(android.widget.SearchView view) {
    if (!AndroidUtils.API_11) return;
    int searchBarId = view.getContext().getResources().getIdentifier("android:id/search_bar", null, null);
    LinearLayout searchBar = (LinearLayout) view.findViewById(searchBarId);
    searchBar.setLayoutTransition(new LayoutTransition());
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public static void setSearchViewLayoutTransition(android.support.v7.widget.SearchView view) {
    if (!AndroidUtils.API_11) return;
    LinearLayout searchBar = (LinearLayout) view.findViewById(R.id.search_bar);
    searchBar.setLayoutTransition(new LayoutTransition());
  }

  private static final Class<?> CLASS_SCROLL_VIEW = ScrollView.class;
  private static final Field SCROLL_VIEW_FIELD_EDGE_GLOW_TOP;
  private static final Field SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM;

  private static final Class<?> CLASS_LIST_VIEW = AbsListView.class;
  private static final Field LIST_VIEW_FIELD_EDGE_GLOW_TOP;
  private static final Field LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM;

  static {
    Field edgeGlowTop = null, edgeGlowBottom = null;

    for (Field f : CLASS_SCROLL_VIEW.getDeclaredFields()) {
      switch (f.getName()) {
        case "mEdgeGlowTop":
          f.setAccessible(true);
          edgeGlowTop = f;
          break;
        case "mEdgeGlowBottom":
          f.setAccessible(true);
          edgeGlowBottom = f;
          break;
      }
    }

    SCROLL_VIEW_FIELD_EDGE_GLOW_TOP = edgeGlowTop;
    SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM = edgeGlowBottom;

    for (Field f : CLASS_LIST_VIEW.getDeclaredFields()) {
      switch (f.getName()) {
        case "mEdgeGlowTop":
          f.setAccessible(true);
          edgeGlowTop = f;
          break;
        case "mEdgeGlowBottom":
          f.setAccessible(true);
          edgeGlowBottom = f;
          break;
      }
    }

    LIST_VIEW_FIELD_EDGE_GLOW_TOP = edgeGlowTop;
    LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM = edgeGlowBottom;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public static void setEdgeGlowColor(AbsListView listView, int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      try {
        EdgeEffect ee;
        ee = (EdgeEffect) LIST_VIEW_FIELD_EDGE_GLOW_TOP.get(listView);
        ee.setColor(color);
        ee = (EdgeEffect) LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM.get(listView);
        ee.setColor(color);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public static void setEdgeGlowColor(ScrollView scrollView, int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      try {
        EdgeEffect ee;
        ee = (EdgeEffect) SCROLL_VIEW_FIELD_EDGE_GLOW_TOP.get(scrollView);
        ee.setColor(color);
        ee = (EdgeEffect) SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM.get(scrollView);
        ee.setColor(color);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
