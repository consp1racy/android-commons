package net.xpece.android.graphics

import android.graphics.Rect
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable

@Deprecated("Prefer immutable classes.")
data class RectD(
        var left: Double, var top: Double, var right: Double,
        var bottom: Double) : Parcelable {

    constructor() : this(.0, .0, .0, .0)

    constructor(r: RectF) : this(
            r.left.toDouble(), r.top.toDouble(), r.right.toDouble(), r.bottom.toDouble())

    constructor(r: Rect) : this(
            r.left.toDouble(), r.top.toDouble(), r.right.toDouble(), r.bottom.toDouble())

    constructor(`in`: Parcel) : this(
            `in`.readDouble(), `in`.readDouble(), `in`.readDouble(), `in`.readDouble())

    /**
     * Returns true if the rectangle is empty (left >= right or top >= bottom)
     */
    fun isEmpty(): Boolean = left >= right || top >= bottom

    fun isZero() = this.bottom == .0 && this.top == .0 && this.left == .0 && this.right == .0

    /**
     * @return the rectangle's width. This does not check for a valid rectangle
     * (i.e. left <= right) so the result may be negative.
     */
    fun width(): Double = right - left

    /**
     * @return the rectangle's height. This does not check for a valid rectangle
     * (i.e. top <= bottom) so the result may be negative.
     */
    fun height(): Double = bottom - top

    /**
     * @return the horizontal center of the rectangle. If the computed value
     * is fractional, this method returns the largest integer that is
     * less than the computed value.
     */
    fun centerX(): Int = ((left + right) / 2).toInt()

    /**
     * @return the vertical center of the rectangle. If the computed value
     * is fractional, this method returns the largest integer that is
     * less than the computed value.
     */
    fun centerY(): Int = ((top + bottom) / 2).toInt()

    /**
     * @return the exact horizontal center of the rectangle as a float.
     */
    fun exactCenterX(): Double = (left + right) * 0.5

    /**
     * @return the exact vertical center of the rectangle as a float.
     */
    fun exactCenterY(): Double = (top + bottom) * 0.5

    /**
     * Set the rectangle to (0,0,0,0)
     */
    fun setEmpty() {
        bottom = .0
        top = .0
        right = .0
        left = .0
    }

    /**
     * Set the rectangle's coordinates to the specified values. Note: no range
     * checking is performed, so it is up to the caller to ensure that
     * left <= right and top <= bottom.
     *
     * @param left   The X coordinate of the left side of the rectangle
     * @param top    The Y coordinate of the top of the rectangle
     * @param right  The X coordinate of the right side of the rectangle
     * @param bottom The Y coordinate of the bottom of the rectangle
     */
    operator fun set(left: Int, top: Int, right: Int, bottom: Int) {
        this.left = left.toDouble()
        this.top = top.toDouble()
        this.right = right.toDouble()
        this.bottom = bottom.toDouble()
    }

    /**
     * Set the rectangle's coordinates to the specified values. Note: no range
     * checking is performed, so it is up to the caller to ensure that
     * left <= right and top <= bottom.
     *
     * @param left   The X coordinate of the left side of the rectangle
     * @param top    The Y coordinate of the top of the rectangle
     * @param right  The X coordinate of the right side of the rectangle
     * @param bottom The Y coordinate of the bottom of the rectangle
     */
    operator fun set(left: Float, top: Float, right: Float, bottom: Float) {
        this.left = left.toDouble()
        this.top = top.toDouble()
        this.right = right.toDouble()
        this.bottom = bottom.toDouble()
    }

    /**
     * Set the rectangle's coordinates to the specified values. Note: no range
     * checking is performed, so it is up to the caller to ensure that
     * left <= right and top <= bottom.
     *
     * @param left   The X coordinate of the left side of the rectangle
     * @param top    The Y coordinate of the top of the rectangle
     * @param right  The X coordinate of the right side of the rectangle
     * @param bottom The Y coordinate of the bottom of the rectangle
     */
    operator fun set(left: Double, top: Double, right: Double, bottom: Double) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    /**
     * Copy the coordinates from src into this rectangle.
     *
     * @param src The rectangle whose coordinates are copied into this
     * rectangle.
     */
    fun set(src: Rect) {
        this.left = src.left.toDouble()
        this.top = src.top.toDouble()
        this.right = src.right.toDouble()
        this.bottom = src.bottom.toDouble()
    }

    /**
     * Copy the coordinates from src into this rectangle.
     *
     * @param src The rectangle whose coordinates are copied into this
     * rectangle.
     */
    fun set(src: RectF) {
        this.left = src.left.toDouble()
        this.top = src.top.toDouble()
        this.right = src.right.toDouble()
        this.bottom = src.bottom.toDouble()
    }

    /**
     * Copy the coordinates from src into this rectangle.
     *
     * @param src The rectangle whose coordinates are copied into this
     * rectangle.
     */
    fun set(src: RectD) {
        this.left = src.left
        this.top = src.top
        this.right = src.right
        this.bottom = src.bottom
    }

    /**
     * Offset the rectangle by adding dx to its left and right coordinates, and
     * adding dy to its top and bottom coordinates.
     *
     * @param dx The amount to add to the rectangle's left and right coordinates
     * @param dy The amount to add to the rectangle's top and bottom coordinates
     */
    fun offset(dx: Int, dy: Int) {
        left += dx
        top += dy
        right += dx
        bottom += dy
    }

    /**
     * Offset the rectangle by adding dx to its left and right coordinates, and
     * adding dy to its top and bottom coordinates.
     *
     * @param dx The amount to add to the rectangle's left and right coordinates
     * @param dy The amount to add to the rectangle's top and bottom coordinates
     */
    fun offset(dx: Float, dy: Float) {
        left += dx
        top += dy
        right += dx
        bottom += dy
    }

    /**
     * Offset the rectangle by adding dx to its left and right coordinates, and
     * adding dy to its top and bottom coordinates.
     *
     * @param dx The amount to add to the rectangle's left and right coordinates
     * @param dy The amount to add to the rectangle's top and bottom coordinates
     */
    fun offset(dx: Double, dy: Double) {
        left += dx
        top += dy
        right += dx
        bottom += dy
    }

    /**
     * Offset the rectangle to a specific (left, top) position,
     * keeping its width and height the same.
     *
     * @param newLeft   The new "left" coordinate for the rectangle
     * @param newTop    The new "top" coordinate for the rectangle
     */
    fun offsetTo(newLeft: Int, newTop: Int) {
        right += newLeft - left
        bottom += newTop - top
        left = newLeft.toDouble()
        top = newTop.toDouble()
    }

    /**
     * Offset the rectangle to a specific (left, top) position,
     * keeping its width and height the same.
     *
     * @param newLeft   The new "left" coordinate for the rectangle
     * @param newTop    The new "top" coordinate for the rectangle
     */
    fun offsetTo(newLeft: Float, newTop: Float) {
        right += newLeft - left
        bottom += newTop - top
        left = newLeft.toDouble()
        top = newTop.toDouble()
    }

    /**
     * Offset the rectangle to a specific (left, top) position,
     * keeping its width and height the same.
     *
     * @param newLeft   The new "left" coordinate for the rectangle
     * @param newTop    The new "top" coordinate for the rectangle
     */
    fun offsetTo(newLeft: Double, newTop: Double) {
        right += newLeft - left
        bottom += newTop - top
        left = newLeft
        top = newTop
    }

    /**
     * Inset the rectangle by (dx,dy). If dx is positive, then the sides are
     * moved inwards, making the rectangle narrower. If dx is negative, then the
     * sides are moved outwards, making the rectangle wider. The same holds true
     * for dy and the top and bottom.
     *
     * @param dx The amount to add(subtract) from the rectangle's left(right)
     * @param dy The amount to add(subtract) from the rectangle's top(bottom)
     */
    fun inset(dx: Int, dy: Int) {
        left += dx
        top += dy
        right -= dx
        bottom -= dy
    }

    /**
     * Inset the rectangle by (dx,dy). If dx is positive, then the sides are
     * moved inwards, making the rectangle narrower. If dx is negative, then the
     * sides are moved outwards, making the rectangle wider. The same holds true
     * for dy and the top and bottom.
     *
     * @param dx The amount to add(subtract) from the rectangle's left(right)
     * @param dy The amount to add(subtract) from the rectangle's top(bottom)
     */
    fun inset(dx: Float, dy: Float) {
        left += dx
        top += dy
        right -= dx
        bottom -= dy
    }

    /**
     * Inset the rectangle by (dx,dy). If dx is positive, then the sides are
     * moved inwards, making the rectangle narrower. If dx is negative, then the
     * sides are moved outwards, making the rectangle wider. The same holds true
     * for dy and the top and bottom.
     *
     * @param dx The amount to add(subtract) from the rectangle's left(right)
     * @param dy The amount to add(subtract) from the rectangle's top(bottom)
     */
    fun inset(dx: Double, dy: Double) {
        left += dx
        top += dy
        right -= dx
        bottom -= dy
    }

    /**
     * Insets the rectangle on all sides specified by the dimensions of the `insets`
     * rectangle.
     *
     * @param insets The rectangle specifying the insets on all side.
     */
    fun inset(insets: Rect) {
        left += insets.left
        top += insets.top
        right -= insets.right
        bottom -= insets.bottom
    }

    /**
     * Insets the rectangle on all sides specified by the dimensions of the `insets`
     * rectangle.
     *
     * @param insets The rectangle specifying the insets on all side.
     */
    fun inset(insets: RectF) {
        left += insets.left
        top += insets.top
        right -= insets.right
        bottom -= insets.bottom
    }

    /**
     * Insets the rectangle on all sides specified by the dimensions of the `insets`
     * rectangle.
     *
     * @param insets The rectangle specifying the insets on all side.
     */
    fun inset(insets: RectD) {
        left += insets.left
        top += insets.top
        right -= insets.right
        bottom -= insets.bottom
    }

    /**
     * Insets the rectangle on all sides specified by the insets.
     *
     * @param left The amount to add from the rectangle's left
     * @param top The amount to add from the rectangle's top
     * @param right The amount to subtract from the rectangle's right
     * @param bottom The amount to subtract from the rectangle's bottom
     */
    fun inset(left: Int, top: Int, right: Int, bottom: Int) {
        this.left += left
        this.top += top
        this.right -= right
        this.bottom -= bottom
    }

    /**
     * Insets the rectangle on all sides specified by the insets.
     *
     * @param left The amount to add from the rectangle's left
     * @param top The amount to add from the rectangle's top
     * @param right The amount to subtract from the rectangle's right
     * @param bottom The amount to subtract from the rectangle's bottom
     */
    fun inset(left: Float, top: Float, right: Float, bottom: Float) {
        this.left += left
        this.top += top
        this.right -= right
        this.bottom -= bottom
    }

    /**
     * Insets the rectangle on all sides specified by the insets.
     *
     * @param left The amount to add from the rectangle's left
     * @param top The amount to add from the rectangle's top
     * @param right The amount to subtract from the rectangle's right
     * @param bottom The amount to subtract from the rectangle's bottom
     */
    fun inset(left: Double, top: Double, right: Double, bottom: Double) {
        this.left += left
        this.top += top
        this.right -= right
        this.bottom -= bottom
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeDouble(left)
        parcel.writeDouble(top)
        parcel.writeDouble(right)
        parcel.writeDouble(bottom)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RectD> = object : Parcelable.Creator<RectD> {
            override fun createFromParcel(parcel: Parcel): RectD = RectD(parcel)

            override fun newArray(i: Int): Array<RectD?> = arrayOfNulls(i)
        }
    }
}
