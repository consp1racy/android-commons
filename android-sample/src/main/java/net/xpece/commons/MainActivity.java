package net.xpece.commons;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import net.xpece.android.content.res.Dimen;
import net.xpece.android.widget.XpEdgeEffect;
import net.xpece.commons.android.sample.R;

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
            final View view = new View(context);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            final View view = (View) object;
            container.removeView(view);
        }
    }
}
