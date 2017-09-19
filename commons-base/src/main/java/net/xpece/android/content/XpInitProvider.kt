package net.xpece.android.content

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.support.annotation.RestrictTo

/**
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class XpInitProvider : EmptyContentProvider() {
    override fun onCreate(): Boolean {
        sContext = context.applicationContext
        return false
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmField
        var sContext: Context = object : ContextWrapper(null) {
            override fun getResources(): Resources {
                throw IllegalStateException("You forgot to call init(Context).")
            }
        }
    }
}
