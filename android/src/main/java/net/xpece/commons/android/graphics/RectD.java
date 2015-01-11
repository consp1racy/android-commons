package net.xpece.commons.android.graphics;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pechanecjr on 12. 9. 2014.
 */
public class RectD implements Parcelable {
  public static Creator<RectD> CREATOR = new Creator<RectD>() {
    @Override
    public RectD createFromParcel(Parcel parcel) {
      return new RectD(parcel);
    }

    @Override
    public RectD[] newArray(int i) {
      return new RectD[i];
    }
  };

  public double left = 0L, top = 0L, right = 0L, bottom = 0L;

  public RectD() {
  }

  public RectD(RectF r) {
    this.left = r.left;
    this.top = r.top;
    this.right = r.right;
    this.bottom = r.bottom;
  }

  public RectD(Rect r) {
    this.left = r.left;
    this.top = r.top;
    this.right = r.right;
    this.bottom = r.bottom;
  }

  public RectD(double left, double top, double right, double bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  public RectD(Parcel in) {
    left = in.readDouble();
    top = in.readDouble();
    right = in.readDouble();
    bottom = in.readDouble();
  }

  public double width() {
    return right - left;
  }

  public double height() {
    return bottom - top;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeDouble(left);
    parcel.writeDouble(top);
    parcel.writeDouble(right);
    parcel.writeDouble(bottom);
  }
}
