package net.xpece.android.util;

/**
 * Created by Eugen on 26. 2. 2015.
 */
public class XpObjects {
  private XpObjects() {}
  @SuppressWarnings("unchecked")
  public static <T> T cast(Object obj) {
    return (T) obj;
  }
}
