package net.xpece.android.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

import java.text.Normalizer;
import java.util.Locale;
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

  public static String unaccentAndLower(String text) {
    return unaccent(text).toLowerCase(Locale.getDefault());
  }

  /**
   * @param subject Original string with accents
   * @param unEmp Constraint. Unready unaccented for increased effectivity.
   * @return Formatted original string.
   */
  public static CharSequence emphasize(String subject, String unEmp) {
    String unSub = unaccentAndLower(subject);
    if (unSub.equals(unEmp)) {
      return Html.fromHtml("<b>" + subject + "</b>");
    }

    int lenTotal = subject.length();
    int lenEmp = unEmp.length();
    String[] unParts = unSub.split(unEmp);
    StringBuilder sb = new StringBuilder();
    int position = 0;
    for (String unPart : unParts) {
      if (position >= lenTotal) continue;
      // emphasize only first letters of words
      // this behavior reflects that of the filter
      int len = unPart.length();
      String original = subject.substring(position, position + len);
      position += len;
      sb.append(original);
      if (position >= lenTotal) continue;
      String emp = subject.substring(position, position + lenEmp);
      if (position == 0 || unSub.substring(position - 1, position).matches("[^\\w]")) {
        sb.append("<b>").append(emp).append("</b>");
      } else {
        sb.append(emp);
      }
      position += lenEmp;
    }
    return Html.fromHtml(sb.toString());
  }

  public static String sanitize(String input) {
    if (TextUtils.isEmpty(input)) return input;
    return input.trim();
  }
}
