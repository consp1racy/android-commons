package net.xpece.android;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.telephony.TelephonyManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by pechanecjr on 4. 1. 2015.
 *
 * @hide
 */
@Deprecated
public final class AndroidUtils {
    public static final boolean API_8 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    public static final boolean API_9 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    public static final boolean API_10 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    public static final boolean API_11 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    public static final boolean API_12 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    public static final boolean API_13 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    public static final boolean API_14 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    public static final boolean API_15 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    public static final boolean API_16 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    public static final boolean API_17 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    public static final boolean API_18 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    public static final boolean API_19 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    public static final boolean API_21 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    public static final boolean API_22 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    public static final boolean API_23 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    public static final boolean API_24 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;

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

    /**
     * Requires {@link android.Manifest.permission#READ_PHONE_STATE} permission.
     *
     * @param context
     * @return
     */
    public static boolean isMobileDataEnabled(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled

        if (Build.VERSION.SDK_INT >= 21) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                Class<?> tmClass = Class.forName(tm.getClass().getName());
                Method method = tmClass.getDeclaredMethod("getDataEnabled");
                method.setAccessible(true);
                mobileDataEnabled = (Boolean) method.invoke(tm);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                Class<?> cmClass = Class.forName(cm.getClass().getName());
                Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
                method.setAccessible(true);
                mobileDataEnabled = (Boolean) method.invoke(cm);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        return mobileDataEnabled;
    }

    /**
     * Requires {@link android.Manifest.permission#ACCESS_WIFI_STATE}
     * and {@link android.Manifest.permission#CHANGE_WIFI_STATE} permissions.
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static int disconnectWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (ni != null && ni.isConnectedOrConnecting()) {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            int id = wm.getConnectionInfo().getNetworkId();
            return wm.disconnect() ? id : -1;
        }

        return -1;
    }

    /**
     * Requires {@link android.Manifest.permission#ACCESS_WIFI_STATE}
     * and {@link android.Manifest.permission#CHANGE_WIFI_STATE} permissions.
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static void reconnectWifi(Context context, int networkId) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (ni != null) {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (networkId > -1) {
                wm.enableNetwork(networkId, true);
            }
        }
    }
}
