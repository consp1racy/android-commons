package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.palette.graphics.Palette;
import android.util.Log;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Put this transformation always last, otherwise the bitmap lookup will not work.
 *
 * @author http://jakewharton.com/coercing-picasso-to-play-with-palette/
 */
public class PaletteTransformation implements Transformation {
    private static final String TAG = PaletteTransformation.class.getSimpleName();

    private static final PaletteTransformation INSTANCE = new PaletteTransformation();
    private static final Map<Bitmap, Palette> CACHE = new WeakHashMap<>();

    public static PaletteTransformation getInstance() {
        return INSTANCE;
    }

    public static Palette getPalette(Bitmap bitmap) {
        return CACHE.get(bitmap);
    }

    public static Palette getPalette(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return getPalette(bitmap);
        } else {
            Log.e(TAG, "Not a BitmapDrawable.");
            return null;
        }
    }

    @Override
    public Bitmap transform(Bitmap source) {
        Palette palette = Palette.from(source).generate();
        CACHE.put(source, palette);
        return source;
    }

    @Override
    public String key() {
        // This transformation does not modify bitmap.
        return "";
    }
}
