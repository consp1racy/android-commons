@file:JvmName("XpToolbar")
@file:Suppress("unused")

package net.xpece.android.widget

import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar

private val CLASS_TOOLBAR = Toolbar::class.java

private val FIELD_TITLE_TEXT_APPEARANCE by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mTitleTextAppearance").apply { isAccessible = true }
}
private val FIELD_TITLE_TEXT_COLOR by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mTitleTextColor").apply { isAccessible = true }
}

private val FIELD_SUBTITLE_TEXT_APPEARANCE by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mSubtitleTextAppearance").apply { isAccessible = true }
}
private val FIELD_SUBTITLE_TEXT_COLOR by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mSubtitleTextColor").apply { isAccessible = true }
}

var Toolbar.titleTextAppearance: Int
    @StyleRes get() = FIELD_TITLE_TEXT_APPEARANCE.getInt(this)
    set(@StyleRes value) {
        setTitleTextAppearance(context, value)
    }

var Toolbar.titleTextColor: Int
    @ColorInt get() = FIELD_TITLE_TEXT_COLOR.getInt(this)
    set(@ColorInt value) {
        setTitleTextColor(value)
    }

var Toolbar.subtitleTextAppearance: Int
    @StyleRes get() = FIELD_SUBTITLE_TEXT_APPEARANCE.getInt(this)
    set(@StyleRes value) {
        setSubtitleTextAppearance(context, value)
    }

var Toolbar.subtitleTextColor: Int
    @ColorInt get() = FIELD_SUBTITLE_TEXT_COLOR.getInt(this)
    set(@ColorInt value) {
        setSubtitleTextColor(value)
    }
