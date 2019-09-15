package net.xpece.commons.sample

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import net.xpece.android.app.SnackbarActivity
import net.xpece.android.content.dp
import net.xpece.android.text.EmphasisCache
import net.xpece.android.text.emphasize
import net.xpece.android.widget.XpEdgeEffect
import net.xpece.android.widget.setSelectionDividerTint
import net.xpece.commons.android.sample.R
import org.threeten.bp.LocalDateTime
import kotlin.system.measureTimeMillis

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
        pager = findViewById(R.id.pager)
        val adapter = MyPagerAdapter()
        pager.adapter = adapter

        XpEdgeEffect.setColor(pager, Color.RED)

        val textFilter = findViewById<TextView>(R.id.textFilter)
        val editFilter = findViewById<EditText>(R.id.editFilter)
        val buttonFilter = findViewById<Button>(R.id.buttonFilter)
        val text = "A quick brown fox jumped over the lazy dog."
//        val text = "Günaydın"
        editFilter.addTextChangedListener {
            val test = it?.trim() ?: ""
            textFilter.setText(emphasize(text, test, resources.configuration.locale), TextView.BufferType.SPANNABLE)
        }

        buttonFilter.setOnClickListener {
            EmphasisCache.clearCache()
            val time = measureTimeMillis {
                textFilter.setText(emphasize(text, "jump", resources.configuration.locale), TextView.BufferType.SPANNABLE)
            }
            Log.w("ASDF", "Took $time ms.")
        }

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
        @JvmField
        val TAG = MainActivity::class.java.simpleName
    }
}
