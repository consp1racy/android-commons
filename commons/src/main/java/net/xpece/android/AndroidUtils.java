package net.xpece.android;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;

/**
 * Created by pechanecjr on 4. 1. 2015.
 *
 * @hide
 */
@Deprecated
public final class AndroidUtils {
    private AndroidUtils() {}

    public static ActivityManager.RunningServiceInfo getServiceInfo(Context context, Class<? extends Service> cls) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo info : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(info.service.getClassName())) {
                return info;
            }
        }
        return null;
    }

    public static String getAppVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        String versionName = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return versionName;
    }

    // http://stackoverflow.com/questions/4856955/how-to-programatically-clear-application-data
    public static boolean deleteDir(File dir) {
        if (dir != null) {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        }
        return true;
    }
}
