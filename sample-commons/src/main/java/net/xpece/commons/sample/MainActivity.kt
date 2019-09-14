package net.xpece.commons.sample

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import net.xpece.android.app.SnackbarActivity
import net.xpece.android.content.dp
import net.xpece.android.widget.XpEdgeEffect
import net.xpece.android.widget.setSelectionDividerTint
import net.xpece.commons.android.sample.R
import org.threeten.bp.LocalDateTime

class MainActivity : AppCompatActivity(), SnackbarActivity {
    override val snackbarParent: View
        get() = findViewById(android.R.id.content)
    override var snackbar: Snackbar? = null

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
                tp.setSelectionDividerTint(csl)
                view = tp
            } else if (position == 1) {
                val csl = ColorStateList.valueOf(Color.RED)
                val dp = DatePicker(context)
                dp.setSelectionDividerTint(csl)
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
