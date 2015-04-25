/**
 *
 */
package net.xpece.android.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import net.xpece.android.AndroidUtils;

/**
 * Instances of class {@code BasePreferences} represent...
 *
 * @author pechanecjr
 * @version 0.0.0000 - 2.10.2013
 */
public class BasePreferences {
    private final SharedPreferences mPrefs;

    protected BasePreferences(Context context, String preferenceFile) {
        int mode = Context.MODE_PRIVATE;

        this.mPrefs = context.getApplicationContext().getSharedPreferences(preferenceFile, mode);
    }

    public final SharedPreferences getSharedPreferences() {
        return mPrefs;
    }

    // == OTHER NON-PRIVATE INSTANCE METHODS ===================================

    protected synchronized final void remove(String key) {
        if (getSharedPreferences().contains(key)) {
            Editor editor = getSharedPreferences().edit();
            editor.remove(key);
            apply(editor);
        }
    }

    protected synchronized final void edit(String key, String value) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

    protected synchronized final void edit(String key, boolean value) {
        Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    protected synchronized final void edit(String key, int value) {
        Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    protected synchronized final void edit(String key, long value) {
        Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        apply(editor);
    }

    protected synchronized final void edit(String key, float value) {
        Editor editor = getSharedPreferences().edit();
        editor.putFloat(key, value);
        apply(editor);
    }

    protected synchronized final String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    protected synchronized final String getString(String key, String def) {
        return getSharedPreferences().getString(key, def);
    }

    protected synchronized final boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    protected synchronized final boolean getBoolean(String key, boolean def) {
        return getSharedPreferences().getBoolean(key, def);
    }

    protected synchronized final int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    protected synchronized final int getInt(String key, int def) {
        return getSharedPreferences().getInt(key, def);
    }

    protected synchronized final long getLong(String key) {
        return getSharedPreferences().getLong(key, 0l);
    }

    protected synchronized final long getLong(String key, long def) {
        return getSharedPreferences().getLong(key, def);
    }

    protected synchronized final float getFloat(String key, float def) {
        return getSharedPreferences().getFloat(key, def);
    }

    protected synchronized final float getFloat(String key) {
        return getSharedPreferences().getFloat(key, 0f);
    }

    @TargetApi(9)
    private void apply(Editor editor) {
        if (AndroidUtils.API_9) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
