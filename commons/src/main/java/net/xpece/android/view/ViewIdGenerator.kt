package net.xpece.android.view

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import net.xpece.android.os.getIntegerMap
import net.xpece.android.os.putMap

/**
 * Use this class to generate an Android resource ID based on a String key and persist this mapping
 * across configuration changes.
 *
 * Possible use case would be saving and restoring states
 * of [RecyclerView][androidx.recyclerview.widget.RecyclerView] item views.
 */
@RequiresApi(17)
class ViewIdGenerator {

    private val cache = mutableMapOf<String, Int>()

    @IdRes
    operator fun get(key: String): Int {
        var out = cache[key]
        if (out == null) {
            out = View.generateViewId()
            cache[key] = out
        }
        return out
    }

    fun onSaveInstanceState(outState: Bundle, key: String) {
        outState.putMap(key, cache)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle, key: String) {
        val map = savedInstanceState.getIntegerMap(key)
        cache.putAll(map)
    }
}
