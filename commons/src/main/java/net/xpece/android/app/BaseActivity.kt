package net.xpece.android.app

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import net.xpece.android.content.ensureRuntimeTheme

/**
 * A version of activity which switches from preview to runtime theme.
 */

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var root: ViewGroup
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        this.ensureRuntimeTheme()
        super.onCreate(savedInstanceState)

        root = provideRootView()
    }

    open fun provideRootView(): ViewGroup = ViewContainer.DEFAULT.forActivity(this)

    fun superSetContentView(view: View) {
        super.setContentView(view)
    }

    fun superSetContentView(@LayoutRes layoutResId: Int) {
        super.setContentView(layoutResId)
    }

    override fun setContentView(@LayoutRes layoutResId: Int) {
        val root = this.root
        root.removeAllViews()
        layoutInflater.inflate(layoutResId, root)
    }

    override fun setContentView(view: View) {
        val root = this.root
        root.removeAllViews()
        root.addView(view)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        val root = this.root
        root.removeAllViews()
        root.addView(view, params)
    }

    override fun addContentView(view: View, params: ViewGroup.LayoutParams) {
        val root = this.root
        root.addView(view, params)
    }
}
