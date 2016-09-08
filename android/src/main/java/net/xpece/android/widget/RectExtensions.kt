package net.xpece.android.widget

import android.graphics.Rect

/**
 * @author Eugen on 08.09.2016.
 */

internal fun Rect.isZero() = this.bottom == 0 && this.top == 0 && this.left == 0 && this.right == 0
