package net.xpece.android.content

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources

/**
 * @hide
 */
class XpInitProvider : EmptyContentProvider() {
    override fun onCreate(): Boolean {
        CONTEXT = context.applicationContext
        return false
    }

    companion object {
        var CONTEXT : Context = object : ContextWrapper(null) {
            override fun getResources(): Resources {
                throw IllegalStateException("You forgot to call init(Context).")
            }
        }
    }
}
