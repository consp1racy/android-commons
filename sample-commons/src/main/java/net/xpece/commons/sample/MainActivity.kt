package net.xpece.commons.sample

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.xpece.android.app.SnackbarActivity
import net.xpece.android.content.dp
import net.xpece.android.content.resolveDrawable
import net.xpece.android.content.resolveString
import net.xpece.android.net.ConnectivityCallback
import net.xpece.android.net.ConnectivityInfo
import net.xpece.android.net.ReactiveConnectivity
import net.xpece.android.widget.XpDatePicker
import net.xpece.android.widget.XpEdgeEffect
import net.xpece.android.widget.XpTimePicker
import net.xpece.commons.android.sample.R
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Eugen on 25. 4. 2015.
 */
class MainActivity : AppCompatActivity(), SnackbarActivity {
    override val snackbarParent: View
        get() = findViewById(android.R.id.content)
    override var snackbar: Snackbar? = null

    val connectivityCallback = ConnectivityCallback {
        if (it.isConnected) {
            showSnackbar("Connected", BaseTransientBottomBar.LENGTH_SHORT)
        } else if (it.isAirplaneModeEnabled) {
            showSnackbar("Airplane mode", BaseTransientBottomBar.LENGTH_INDEFINITE)
        } else {
            showSnackbar("Disconnected", BaseTransientBottomBar.LENGTH_INDEFINITE)
        }
    }

    private lateinit var connectivityObservable : Flowable<ConnectivityInfo>
    private lateinit var connectivitySubscription : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pager = findViewById(R.id.pager) as ViewPager
        val adapter = MyPagerAdapter()
        pager.adapter = adapter

        XpEdgeEffect.setColor(pager, Color.RED)

        val d = dp(16)
        Log.d(TAG, "Dimension real size: " + d.toString(this))

        val toolbar = findViewById(R.id.toolbar) as Toolbar

        val icon = toolbar.context.resolveDrawable(R.attr.homeAsUpIndicator)
        toolbar.navigationIcon = icon
        val title = this.resolveString(R.style.Widget_AppCompat_ActionButton_Overflow, android.R.attr.contentDescription)
        toolbar.title = title

        if (savedInstanceState == null) {
            connectivityObservable = ReactiveConnectivity.observe(this)
        } else {
            connectivityObservable = lastCustomNonConfigurationInstance as Flowable<ConnectivityInfo>
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return connectivityObservable
    }

    override fun onStart() {
        super.onStart()
        connectivitySubscription = connectivityObservable
                .skipWhile { !it.isConnected }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    connectivityCallback.onConnectivityChanged(it)
                }
    }

    override fun onStop() {
        super.onStop()
        connectivitySubscription.dispose()
    }

    internal class MyPagerAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return 4
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val context = container.context
            val view: View
            if (position == 0) {
                val csl = ColorStateList.valueOf(Color.RED)
                val tp = TimePicker(context)
                XpTimePicker.setSelectionDividerTint(tp, csl)
                view = tp
            } else if (position == 1) {
                val csl = ColorStateList.valueOf(Color.RED)
                val dp = DatePicker(context)
                XpDatePicker.setSelectionDividerTint(dp, csl)
                view = dp
            } else {
                view = View(context)
            }
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
