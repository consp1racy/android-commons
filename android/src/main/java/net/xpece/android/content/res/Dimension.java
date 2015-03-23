package net.xpece.android.content.res;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DimenRes;
import android.util.TypedValue;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class Dimension {
  private float mDimen = 0;

  private Dimension(float dimen) {
    mDimen = dimen;
  }

  public static Dimension fromAttribute(Context context, @AttrRes int attr) {
    return new Dimension(ResourceUtils.getDimension(context, attr, 0));
  }

  public static Dimension fromResource(Context context, @DimenRes int resId) {
    return new Dimension(context.getResources().getDimension(resId));
  }

  public static Dimension fromDp(Context context, int dip) {
    return new Dimension(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics()));
  }

  public static Dimension fromPx(float px) {
    return new Dimension(px);
  }

  public Dimension plus(Dimension that) {
    return new Dimension(this.mDimen + that.mDimen);
  }

  public Dimension minus(Dimension that) {
    return new Dimension(this.mDimen - that.mDimen);
  }

  public Dimension multiply(float q) {
    return new Dimension(this.mDimen * q);
  }

  public Dimension divide(float d) {
    return new Dimension(this.mDimen / d);
  }

  public float get() { return mDimen; }

  public int getPixelSize() { return (int) (mDimen + 0.5f);}

  public int getPixelOffset() { return (int) (mDimen);}
}
