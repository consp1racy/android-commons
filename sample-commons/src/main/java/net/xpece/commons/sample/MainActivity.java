package net.xpece.commons.sample;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import net.xpece.android.content.XpContext;
import net.xpece.android.content.res.Dimen;
import net.xpece.android.widget.XpDatePicker;
import net.xpece.android.widget.XpEdgeEffect;
import net.xpece.android.widget.XpTimePicker;
import net.xpece.commons.android.sample.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Eugen on 25. 4. 2015.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new MyPagerAdapter();
        pager.setAdapter(adapter);

        XpEdgeEffect.setColor(pager, Color.RED);

        Dimen d = Dimen.dp(16);
        Log.d(TAG, "Dimension real size: " + d.toString(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Drawable icon = XpContext.resolveDrawable(toolbar.getContext(), R.attr.homeAsUpIndicator);
        toolbar.setNavigationIcon(icon);
        String title = XpContext.resolveString(this, R.style.Widget_AppCompat_ActionButton_Overflow, android.R.attr.contentDescription);
        toolbar.setTitle(title);
    }

    static class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Context context = container.getContext();
            final View view;
            if (position == 0) {
                final ColorStateList csl = ColorStateList.valueOf(Color.RED);
                final TimePicker tp = new TimePicker(context);
                XpTimePicker.setSelectionDividerTint(tp, csl);
                view = tp;
            } else if (position == 1) {
                final ColorStateList csl = ColorStateList.valueOf(Color.RED);
                final DatePicker dp = new DatePicker(context);
                XpDatePicker.setSelectionDividerTint(dp, csl);
                view = dp;
            } else {
                view = new View(context);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            final View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    protected void attachBaseContext(final Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
