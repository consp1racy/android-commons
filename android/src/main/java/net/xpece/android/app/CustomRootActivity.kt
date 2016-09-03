package net.xpece.android.app

import android.support.annotation.LayoutRes
import android.view.View

/**
 * @author Eugen on 31.08.2016.
 */
interface CustomRootActivity {
    fun superSetContentView(view: View)
    fun superSetContentView(@LayoutRes layoutResId: Int)
}
