package net.xpece.commons.sample

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.text.style.BulletSpan
import android.text.style.StrikethroughSpan
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
import com.google.android.material.snackbar.Snackbar
import net.xpece.android.app.SnackbarActivity
import net.xpece.android.content.dp
import net.xpece.android.edgeeffect.widget.setEdgeEffectColorCompat
import net.xpece.android.graphics.drawable.RippleDrawableCompatInflater
import net.xpece.android.picker.widget.setSelectionDividerTintCompat
import net.xpece.android.text.span.BulletSpanCompat
import net.xpece.android.text.span.TextAppearanceSpanCompat
import net.xpece.android.text.span.asSpan
import net.xpece.commons.android.sample.R
import net.xpece.commons.android.sample.databinding.ActivityMainBinding
import org.threeten.bp.LocalDateTime

class MainActivity : AppCompatActivity(), SnackbarActivity {
    override val snackbarParent: View
        get() = findViewById(android.R.id.content)
    override var snackbar: Snackbar? = null

    private var localDateTime = LocalDateTime.of(2000, 1, 31, 16, 30)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)

        RippleDrawableCompatInflater.installDelegate()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLight.setOnClickListener {
            if (SDK_INT >= 21) {
                val context = it.context
                Intent(context, RippleActivity::class.java)
                    .let(context::startActivity)
            }
        }

        val adapter = MyPagerAdapter()
        binding.pager.adapter = adapter

        binding.pager.setEdgeEffectColorCompat(Color.RED)

        val d = dp(16)
        Log.d(TAG, "Dimension real size: " + d.toString(this))

        binding.textBullets.text = buildSpannedString {
            for (i in 0..3) {
                inSpans(
                    BulletSpanCompat(32, null, 8),
                    StrikethroughSpan(),
                    Typeface.defaultFromStyle(BOLD).asSpan()
                ) {
                    appendLine("Lorem ipsum")
                }
            }
            delete(length - 1, length)
        }
        binding.textBullets2.text = buildSpannedString {
            for (i in 0..3) {
                inSpans(
                    BulletSpanCompat(32, Color.BLACK),
                    StrikethroughSpan()
                ) {
                    appendLine("Lorem ipsum")
                }
            }
            delete(length - 1, length)
        }
        if (SDK_INT >= 28) {
            binding.textBullets3.text = buildSpannedString {
                for (i in 0..3) {
                    inSpans(
                        BulletSpan(32, Color.BLACK, 8),
                        StrikethroughSpan()
                    ) {
                        appendLine("Lorem ipsum")
                    }
                }
                delete(length - 1, length)
            }
        }

        binding.textSpanTest.text = buildSpannedString {
            append("This is ")
            inSpans(
                TextAppearanceSpanCompat(
                    binding.textSpanTest.context,
                    R.style.TextAppearance_Raleway_Thin
                )
            ) {
                append("thin ")
            }
            inSpans(
                TextAppearanceSpanCompat(
                    binding.textSpanTest.context,
                    R.style.TextAppearance_Raleway_Thin_Italic
                )
            ) {
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
