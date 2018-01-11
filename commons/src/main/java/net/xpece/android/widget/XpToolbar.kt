@file:JvmName("XpToolbar")

package net.xpece.android.widget

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import android.support.v7.widget.AppCompatDrawableManager
import android.support.v7.widget.Toolbar
import android.widget.TextView

private val CLASS_TOOLBAR = Toolbar::class.java

private val FIELD_COLLAPSE_ICON = CLASS_TOOLBAR.getDeclaredField(
        "mCollapseIcon")!!.apply { isAccessible = true }

private val FIELD_TITLE_TEXT_VIEW = CLASS_TOOLBAR.getDeclaredField(
        "mTitleTextView")!!.apply { isAccessible = true }
private val FIELD_TITLE_TEXT_APPEARANCE = CLASS_TOOLBAR.getDeclaredField(
        "mTitleTextAppearance")!!.apply { isAccessible = true }

private val FIELD_SUBTITLE_TEXT_VIEW = CLASS_TOOLBAR.getDeclaredField(
        "mSubtitleTextView")!!.apply { isAccessible = true }
private val FIELD_SUBTITLE_TEXT_APPEARANCE = CLASS_TOOLBAR.getDeclaredField(
        "mSubtitleTextAppearance")!!.apply { isAccessible = true }

var Toolbar.collapseIcon: Drawable?
    get() = FIELD_COLLAPSE_ICON.get(this) as Drawable?
    set(value) = FIELD_COLLAPSE_ICON.set(this, value)

@SuppressLint("RestrictedApi")
fun Toolbar.setCollapseIcon(@DrawableRes iconRes: Int) {
    collapseIcon = AppCompatDrawableManager.get().getDrawable(context, iconRes)
}

val Toolbar.titleTextView: TextView?
    get() = FIELD_TITLE_TEXT_VIEW.get(this) as TextView?

var Toolbar.titleTextAppearance: Int
    @StyleRes get() = FIELD_TITLE_TEXT_APPEARANCE.getInt(this)
    set(@StyleRes value) {
        setTitleTextAppearance(context, value)
    }

val Toolbar.subtitleTextView: TextView?
    get() = FIELD_SUBTITLE_TEXT_VIEW.get(this) as TextView?

var Toolbar.subtitleTextAppearance: Int
    @StyleRes get() = FIELD_SUBTITLE_TEXT_APPEARANCE.getInt(this)
    set(@StyleRes value) {
        setSubtitleTextAppearance(context, value)
    }
