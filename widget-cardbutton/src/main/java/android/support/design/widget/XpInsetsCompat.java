package android.support.design.widget;

import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;

/**
 * An Insets instance holds four integer offsets which describe changes to the four
 * edges of a Rectangle. By convention, positive values move edges towards the
 * centre of the rectangle.
 * <p>
 * Insets are immutable so may be treated as values.
 *
 * @hide
 */
@RequiresApi(16)
@RestrictTo(RestrictTo.Scope.LIBRARY)
final class XpInsetsCompat {
    private static final boolean SAFE = Build.VERSION.SDK_INT < 28 && !"P".equals(Build.VERSION.CODENAME);

    public static final Insets NONE = of(0, 0, 0, 0);

    private XpInsetsCompat() {
    }

    /**
     * Return an Insets instance with the appropriate values.
     *
     * @param left   the left inset
     * @param top    the top inset
     * @param right  the right inset
     * @param bottom the bottom inset
     * @return Insets instance with the appropriate values
     */
    @NonNull
    public static Insets of(int left, int top, int right, int bottom) {
        if (SAFE) {
            return Insets.of(left, top, right, bottom);
        } else {
            final InsetDrawable insets = new InsetDrawable(null, left, top, right, bottom);
            return XpInsetsHelper.getOpticalBounds(insets);
        }
    }

    /**
     * Return an Insets instance with the appropriate values.
     *
     * @param r the rectangle from which to take the values
     * @return an Insets instance with the appropriate values
     */
    @NonNull
    public static Insets of(@Nullable Rect r) {
        if (r == null) {
            return NONE;
        } else {
            return of(r.left, r.top, r.right, r.bottom);
        }
    }
}
