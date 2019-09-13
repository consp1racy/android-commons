package net.xpece.commons.sample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.view.isVisible
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
import net.xpece.android.time.toLocalDateTime
import net.xpece.android.time.toSqlTimestamp
import net.xpece.android.widget.XpDatePicker
import net.xpece.android.widget.XpEdgeEffect
import net.xpece.android.widget.XpTimePicker
import net.xpece.commons.android.sample.R
import org.threeten.bp.LocalDateTime
import java.sql.Timestamp

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
        pager.isVisible = it.isConnected
    }

    private lateinit var connectivityObservable: Flowable<ConnectivityInfo>
    private var connectivitySubscription: Disposable? = null

    private lateinit var pager: androidx.viewpager.widget.ViewPager

    private var localDateTime = LocalDateTime.of(2000, 1, 31, 16, 30)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager = findViewById<androidx.viewpager.widget.ViewPager>(R.id.pager)
        val adapter = MyPagerAdapter()
        pager.adapter = adapter

        XpEdgeEffect.setColor(pager, Color.RED)

        val d = dp(16)
        Log.d(TAG, "Dimension real size: " + d.toString(this))

        if (savedInstanceState == null) {
            connectivityObservable = ReactiveConnectivity.observe(this)

            val timestamp = localDateTime.toSqlTimestamp()
            val local = timestamp.toLocalDateTime()
            Log.w(TAG, local.toString())
        } else {
            connectivityObservable = lastCustomNonConfigurationInstance as Flowable<ConnectivityInfo>

            val timestamp = savedInstanceState.getSerializable("time") as Timestamp
            val local = timestamp.toLocalDateTime()
            Log.w(TAG, local.toString())
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return connectivityObservable
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("time", localDateTime.toSqlTimestamp())
    }

    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            connectivitySubscription = connectivityObservable
                    .skipWhile { it.isConnected }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        connectivityCallback.onConnectivityChanged(it)
                    }
        }
    }

    override fun onStop() {
        super.onStop()
        connectivitySubscription?.dispose()
    }

    internal class MyPagerAdapter : androidx.viewpager.widget.PagerAdapter() {
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

    companion object {
        @JvmField val TAG = MainActivity::class.java.simpleName
    }
}
