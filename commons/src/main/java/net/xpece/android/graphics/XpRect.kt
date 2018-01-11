@file:JvmName("XpRect")

package net.xpece.android.graphics

import android.graphics.Rect
import android.graphics.RectF

/**
 * @author Eugen on 08.09.2016.
 */

fun Rect.isZero() = this.bottom == 0 && this.top == 0 && this.left == 0 && this.right == 0

fun RectF.isZero() = this.bottom == 0f && this.top == 0f && this.left == 0f && this.right == 0f
