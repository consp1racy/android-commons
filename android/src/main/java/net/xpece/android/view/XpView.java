package net.xpece.android.view;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.xpece.android.AndroidUtils;
import net.xpece.android.R;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
@TargetApi(21)
public final class XpView {
    protected XpView() {}

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    public static void setBackground(View v, Drawable d) {
        if (Build.VERSION.SDK_INT < 16) {
            v.setBackgroundDrawable(d);
        } else {
            v.setBackground(d);
        }
    }

    public static void setVisibility(View v, boolean visible) {
        v.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static void setTextAndVisibility(TextView v, CharSequence text) {
        boolean visible = !TextUtils.isEmpty(text);
        if (visible) {
            v.setText(text);
            v.setVisibility(View.VISIBLE);
        } else {
            v.setVisibility(View.GONE);
            v.setText(null);
        }
    }

    /**
     * @return Returns true this ScrollView can be scrolled
     */
    public static boolean canScroll(ScrollView scroll) {
        View child = scroll.getChildAt(0);
        if (child != null) {
            int childHeight = child.getHeight();
            return scroll.getHeight() < childHeight + scroll.getPaddingTop() + scroll.getPaddingBottom();
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener l) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(l);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(l);
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

}
