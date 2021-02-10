package net.xpece.android.graphics.drawable

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.appcompat.widget.ResourceManagerInternal
import androidx.appcompat.widget.RippleDrawableCompatInflateDelegate

object RippleDrawableCompatInflater {

    private var isDelegateInstalled = false

    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun installDelegate() {
        if (SDK_INT !in 21..22) return

        if (!isDelegateInstalled) {
            synchronized(this) {
                if (!isDelegateInstalled) {
                    isDelegateInstalled = true
                    val manager = ResourceManagerInternal.get()
                    addDelegate(manager, RippleDrawableCompatInflateDelegate)
                }
            }
        }
    }

    private fun addDelegate(
        manager: ResourceManagerInternal,
        delegate: RippleDrawableCompatInflateDelegate
    ) {
        try {
            manager.javaClass.declaredMethods
                .single { it.name == "addDelegate" }
                .apply { isAccessible = true }
                .invoke(manager, "ripple", delegate)
        } catch (ex: Throwable) {
            Log.w("RippleDrawableCompat", "Failed to install InflateDelegate: ${ex.message}")
        }
    }
}
