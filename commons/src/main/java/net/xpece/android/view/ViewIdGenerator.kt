package net.xpece.android.view

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import android.util.Log
import android.view.View
import net.xpece.android.os.readIntegerMap
import net.xpece.android.os.writeMap

@RequiresApi(17)
class ViewIdGenerator {
    companion object {
        @JvmField
        var DEBUG = false
        @JvmField
        val TAG = ViewIdGenerator::class.java.simpleName!!
    }

    private val cache = mutableMapOf<String, Int>()

    @IdRes
    operator fun get(key: String): Int {
        var out = cache[key]
        if (out == null) {
            out = View.generateViewId()
            log { "Cache miss! Generated view ID $out for $key." }
            cache[key] = out
        } else {
            //td { "Cache hit! View ID $out for $key." }
        }
        return out
    }

    fun onSaveInstanceState(outState: Bundle, key: String) {
        val bundle = Bundle().apply { writeMap(cache) }
        outState.putBundle(key, bundle)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle, key: String) {
        val bundle = savedInstanceState.getBundle(key)
        val map = bundle.readIntegerMap()
        cache.putAll(map)
    }

    @Suppress("ConstantConditionIf")
    private inline fun log(priority: Int = Log.DEBUG, lazyMessage: () -> String) {
        if (DEBUG) Log.println(priority, TAG, lazyMessage())
    }
}
