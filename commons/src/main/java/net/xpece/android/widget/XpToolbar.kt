@file:JvmName("XpToolbar")
@file:Suppress("unused")

package net.xpece.android.widget

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar

private val CLASS_TOOLBAR = Toolbar::class.java

private val FIELD_TITLE_TEXT_VIEW by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mTitleTextView").apply { isAccessible = true }
}
private val FIELD_TITLE_TEXT_APPEARANCE by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mTitleTextAppearance").apply { isAccessible = true }
}
private val FIELD_TITLE_TEXT_COLOR by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mTitleTextColor").apply { isAccessible = true }
}

private val FIELD_SUBTITLE_TEXT_VIEW by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mSubtitleTextView").apply { isAccessible = true }
}
private val FIELD_SUBTITLE_TEXT_APPEARANCE by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mSubtitleTextAppearance").apply { isAccessible = true }
}
private val FIELD_SUBTITLE_TEXT_COLOR by lazy(LazyThreadSafetyMode.NONE) {
    CLASS_TOOLBAR.getDeclaredField("mSubtitleTextColor").apply { isAccessible = true }
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
@Deprecated("Replace with direct call.", level = DeprecationLevel.HIDDEN)
var Toolbar.collapseIcon: Drawable?
    get() = collapseIcon
    set(value) {
        collapseIcon = value
    }

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
@Deprecated("Replace with direct call.", level = DeprecationLevel.HIDDEN)
fun Toolbar.setCollapseIcon(@DrawableRes iconRes: Int) {
    setCollapseIcon(iconRes)
}

// There is a package-private method now.
@Suppress("ConflictingExtensionProperty")
val Toolbar.titleTextView: TextView?
    get() = FIELD_TITLE_TEXT_VIEW.get(this) as TextView?

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

// There is a package-private method now.
@Suppress("ConflictingExtensionProperty")
val Toolbar.subtitleTextView: TextView?
    get() = FIELD_SUBTITLE_TEXT_VIEW.get(this) as TextView?

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
