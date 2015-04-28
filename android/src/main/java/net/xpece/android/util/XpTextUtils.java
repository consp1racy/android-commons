package net.xpece.android.util;

import android.annotation.TargetApi;
import android.os.Build;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by Eugen on 26. 2. 2015.
 */
public class XpTextUtils {

  private static final Pattern PATTERN_UNACCENT = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

  /**
   * Source: http://www.rgagnon.com/javadetails/java-0456.html
   *
   * @param s
   * @return
   * @since API 9
   */
  @TargetApi(Build.VERSION_CODES.GINGERBREAD)
  public static String unaccent(CharSequence s) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
      throw new UnsupportedOperationException("unAccent is not supported below API 9.");
    } else {
      String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
      return PATTERN_UNACCENT.matcher(temp).replaceAll("");
    }
  }

}
