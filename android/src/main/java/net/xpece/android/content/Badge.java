/**
 *
 */
package net.xpece.android.content;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.Locale;

/**
 * Instances of class {@code BadgeUtil} represent...
 *
 * @author pechanecjr
 * @version 0.0.0000 - 3. 12. 2013
 */
public enum Badge {
  // == VALUES OF ENUMERATED TYPE ============================================
  SAMSUNG {
    @Override
    protected void show(Context context, int num) {
      // http://stackoverflow.com/questions/20136483/how-do-you-interface-with-badgeprovider-on-samsung-phones-to-add-a-count-to-the/20136484#20136484
      if (!android.os.Build.MANUFACTURER.toUpperCase(Locale.getDefault()).contains("SAMSUNG"))
        return;

      String packageName = context.getPackageName();
      Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
      String className = launchIntent.getComponent().getClassName();

      Uri uri = Uri.parse("content://com.sec.badge/apps");
      String selection = "package = ?";
      String[] selectionArgs = new String[]{packageName};

      boolean already;
      Cursor c = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
      if (c == null) {
        // no samsung badge available
        return;
      } else {
        already = c.getCount() > 0;
        c.close();
      }

      ContentValues cv = new ContentValues();
      cv.put("package", packageName);
      cv.put("class", className);
      cv.put("badgecount", num);

      try { // pro jistotu
        if (num > 0) {
          // if there is a number
          if (!already) {
            // and badge is not yet set up -> create it
            context.getContentResolver().insert(uri, cv);
          } else {
            // and badge is already shown -> update it
            context.getContentResolver().update(uri, cv, selection, selectionArgs);
          }
        } else {
          // if there is no number
          if (already) {
            // and badge was set up already -> hide it
            context.getContentResolver().update(uri, cv, selection, selectionArgs);
          }
        }
      } catch (Exception ex) {
        Log.e(TAG, "SAMSUNG badge to ICON exception: " + ex.getMessage());
      }
    }
  },
  SONY {
    @Override
    protected void show(Context context, int num) {
      // http://stackoverflow.com/questions/20216806/how-to-add-a-notification-badge-count-to-application-icon-on-sony-xperia-devices
      if (!android.os.Build.MANUFACTURER.toUpperCase(Locale.getDefault()).contains("SONY")) return;

      PackageManager pm = context.getPackageManager();
      String pkg = context.getPackageName();
      Intent launcher = pm.getLaunchIntentForPackage(pkg);
      String mainActivity = launcher.getComponent().getClassName();

      Intent intent = new Intent();
      intent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
      intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", mainActivity);
      intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", num > 0);
      intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", num + "");
      intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", pkg);

      context.sendBroadcast(intent);
    }
  };

  // == CONSTANT CLASS ATTRIBUTES ============================================

  private static final String TAG = Badge.class.getSimpleName();

  // == VARIABLE CLASS ATTRIBUTES ============================================
  // == STATIC INITIALIZER (CLASS CONSTRUCTOR) ===============================
  // == CONSTANT INSTANCE ATTRIBUTES =========================================
  // == VARIABLE INSTANCE ATTRIBUTES =========================================
  // == CLASS GETTERS AND SETTERS ============================================
  // == OTHER NON-PRIVATE CLASS METHODS ======================================

  private Badge() {
  }

  // #########################################################################
  // == CONSTUCTORS AND FACTORY METHODS ======================================

  public static void showAll(Context context, int num) {
    for (Badge badge : Badge.values()) {
      badge.show(context, num);
    }
  }

  // == ABSTRACT METHODS =====================================================

  protected abstract void show(Context context, int num);

  // == INSTANCE GETTERS AND SETTERS =========================================
  // == OTHER NON-PRIVATE INSTANCE METHODS ===================================
  // == PRIVATE AND AUXILIARY CLASS METHODS ==================================
  // == PRIVATE AND AUXILIARY INSTANCE METHODS ===============================
  // == EMBEDDED TYPES AND INNER CLASSES =====================================
  // == TESTING CLASSES AND METHODS ==========================================
  //
  // /**
  // * Testing method.
  // */
  // public static void test() {
  // NewClass inst = new NewClass();
  // }
  //
  // /** @param args Command line arguments - not used. */
  // public static void main( String[] args ) { test(); }
}
