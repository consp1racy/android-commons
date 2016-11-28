package net.xpece.android.graphics

import android.graphics.Rect
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by pechanecjr on 12. 9. 2014.
 */
class RectD : Parcelable {

    var left = 0.0
    var top = 0.0
    var right = 0.0
    var bottom = 0.0

    constructor() {
    }

    constructor(r: RectF) {
        this.left = r.left.toDouble()
        this.top = r.top.toDouble()
        this.right = r.right.toDouble()
        this.bottom = r.bottom.toDouble()
    }

    constructor(r: Rect) {
        this.left = r.left.toDouble()
        this.top = r.top.toDouble()
        this.right = r.right.toDouble()
        this.bottom = r.bottom.toDouble()
    }

    constructor(left: Double, top: Double, right: Double, bottom: Double) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    constructor(`in`: Parcel) {
        left = `in`.readDouble()
        top = `in`.readDouble()
        right = `in`.readDouble()
        bottom = `in`.readDouble()
    }

    fun width(): Double {
        return right - left
    }

    fun height(): Double {
        return bottom - top
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeDouble(left)
        parcel.writeDouble(top)
        parcel.writeDouble(right)
        parcel.writeDouble(bottom)
    }

    companion object {
        @JvmField
        var CREATOR: Parcelable.Creator<RectD> = object : Parcelable.Creator<RectD> {
            override fun createFromParcel(parcel: Parcel): RectD {
                return RectD(parcel)
            }

            override fun newArray(i: Int): Array<RectD?> {
                return arrayOfNulls(i)
            }
        }
    }

    fun isZero() = this.bottom == .0 && this.top == .0 && this.left == .0 && this.right == .0
}
