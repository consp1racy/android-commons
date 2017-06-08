package net.xpece.android.widget

import android.annotation.TargetApi
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Build
import android.os.Looper
import android.util.LruCache
import android.webkit.WebView

/**
 * Created by Eugen on 16.03.2017.
 */
object WebViewCache : ComponentCallbacks2 {
    override fun onLowMemory() {
        // No-op.
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        // No-op.
    }

    @TargetApi(17)
    override fun onTrimMemory(level: Int) {
        if (level >= ComponentCallbacks2.TRIM_MEMORY_COMPLETE) {
            cache.evictAll()
        } else if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN && Build.VERSION.SDK_INT >= 17) {
            cache.trimToSize(1)
        }
    }

    val cache = LruCache<String, WebView>(6)

    fun get(key: String, factory: () -> WebView): WebView {
        checkMainThread()
        var webView = cache[key]
        if (webView == null) {
            webView = factory.invoke()
            cache.put(key, webView)
        }
        return webView
    }

    fun remove(key: String) {
        cache.remove(key)
    }

    private fun checkMainThread() {
        if (Looper.getMainLooper().thread != Thread.currentThread()) {
            throw IllegalStateException("Expected to be called on the main thread but was ${Thread.currentThread().name}")
        }
    }
}
