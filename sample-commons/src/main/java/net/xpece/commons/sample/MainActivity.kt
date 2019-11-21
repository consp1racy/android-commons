package net.xpece.commons.sample

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.BulletSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import net.xpece.android.app.SnackbarActivity
import net.xpece.android.content.dp
import net.xpece.android.edgeeffect.widget.setEdgeEffectColorCompat
import net.xpece.android.picker.widget.setSelectionDividerTintCompat
import net.xpece.android.text.span.BulletSpanCompat
import net.xpece.android.text.span.TextAppearanceSpanCompat
import net.xpece.commons.android.sample.R
import org.threeten.bp.LocalDateTime

class MainActivity : AppCompatActivity(), SnackbarActivity {
    override val snackbarParent: View
        get() = findViewById(android.R.id.content)
    override var snackbar: Snackbar? = null

    private lateinit var pager: ViewPager
    private lateinit var textSpanTest: TextView

    private var localDateTime = LocalDateTime.of(2000, 1, 31, 16, 30)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager = findViewById(R.id.pager)
        textSpanTest = findViewById(R.id.textSpanTest)

        val adapter = MyPagerAdapter()
        pager.adapter = adapter

        pager.setEdgeEffectColorCompat(Color.RED)

        val d = dp(16)
        Log.d(TAG, "Dimension real size: " + d.toString(this))

        val textBullets = findViewById<TextView>(R.id.textBullets)
        val textBullets2 = findViewById<TextView>(R.id.textBullets2)
//        textBullets.text = SpannableString(textBullets.text)
        textBullets.text = SpannableString("Lorem ipsum")
                .apply { setSpan(BulletSpanCompat.create(32, Color.BLACK, 8), 0, length, 0) }
        if (Build.VERSION.SDK_INT >= 28) {
            textBullets2.text = SpannableString("Lorem ipsum")
                .apply { setSpan(BulletSpan(32, Color.BLACK, 8), 0, length, 0) }
        }

        textSpanTest.text = buildSpannedString {
            append("This is ")
            inSpans(TextAppearanceSpanCompat(textSpanTest.context, R.style.TextAppearance_Raleway_Thin)) {
                append("thin ")
            }
            inSpans(TextAppearanceSpanCompat(textSpanTest.context, R.style.TextAppearance_Raleway_Thin_Italic)) {
                append("italic ")
            }
        }
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
                tp.setSelectionDividerTintCompat(csl)
                view = tp
            } else if (position == 1) {
                val csl = ColorStateList.valueOf(Color.RED)
                val dp = DatePicker(context)
                dp.setSelectionDividerTintCompat(csl)
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
        @JvmField
        val TAG = MainActivity::class.java.simpleName
    }
}
