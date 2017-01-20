package net.xpece.android.content

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources

/**
 * @hide
 */
class XpInitProvider : EmptyContentProvider() {
    override fun onCreate(): Boolean {
        sContext = context.applicationContext
        return false
    }

    companion object {
        @JvmField
        var sContext: Context = object : ContextWrapper(null) {
            override fun getResources(): Resources {
                throw IllegalStateException("You forgot to call init(Context).")
            }
        }
    }
}
