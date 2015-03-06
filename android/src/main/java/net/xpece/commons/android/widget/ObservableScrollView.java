package net.xpece.commons.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * A custom ScrollView that can accept a scroll listener.
 *<p><b>Deprecated.</b> Use https://github.com/ksoichiro/Android-ObservableScrollView instead.</p>
 * @author com.google.samples.apps.iosched.ui.widget.ObservableScrollView
 */
@Deprecated
public class ObservableScrollView extends ScrollView {
  private ArrayList<Callbacks> mCallbacks = new ArrayList<Callbacks>();

  public ObservableScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    for (Callbacks c : mCallbacks) {
      c.onScrollChanged(l - oldl, t - oldt);
    }
  }

  @Override
  public int computeVerticalScrollRange() {
    return super.computeVerticalScrollRange();
  }

  public void addCallbacks(Callbacks listener) {
    if (!mCallbacks.contains(listener)) {
      mCallbacks.add(listener);
    }
  }

  public static interface Callbacks {
    public void onScrollChanged(int deltaX, int deltaY);
  }
}
