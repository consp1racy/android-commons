@file:JvmName("XpImageView")
package net.xpece.android.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.ViewCompat
import net.xpece.android.content.getDrawableCompat

@JvmOverloads
fun ImageView.switchImage(d: Drawable?, duration: Int = 100) {
    if (ViewCompat.isLaidOut(this)) {
        var old = drawable
        if (old is TransitionDrawable) {
            old = old.getDrawable(1)
        } else if (old == null) {
            if (d == null) {
                return
            } else {
                old = ColorDrawable(Color.TRANSPARENT)
            }
        }
        val target = d ?: ColorDrawable(Color.TRANSPARENT)
        val transition = TransitionDrawable(arrayOf(old, target))
        transition.isCrossFadeEnabled = true
        setImageDrawable(transition)
        transition.startTransition(duration)
    } else {
        setImageDrawable(d)
    }
}

@SuppressLint("RestrictedApi")
@JvmOverloads
fun ImageView.switchImage(@DrawableRes resId: Int, duration: Int = 100) {
    val d = context.getDrawableCompat(resId)
    switchImage(d, duration)
}
