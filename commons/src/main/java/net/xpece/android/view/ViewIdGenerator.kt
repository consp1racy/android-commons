package net.xpece.android.view

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.view.View
import net.xpece.android.os.readIntegerMap
import net.xpece.android.os.writeMap
import net.xpece.android.util.td

/**
 * Created by Eugen on 27.02.2017.
 */
@RequiresApi(17)
class ViewIdGenerator {
    private val cache = mutableMapOf<String, Int>()

    @IdRes
    operator fun get(key: String): Int {
        var out = cache[key]
        if (out == null) {
            out = View.generateViewId()
            td { "Cache miss! Generated view ID $out for $key." }
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
}
