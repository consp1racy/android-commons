package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * @hide
 */

public final class XpTextViewCompat {
    private static final XpTextViewCompatImpl IMPL;

    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 24) {
            IMPL = new NougatXpTextViewCompatImpl();
        } else if (version >= 18) {
            IMPL = new JbMr2XpTextViewCompatImpl();
        } else if (version >= 17) {
            IMPL = new JbMr1XpTextViewCompatImpl();
        } else {
            IMPL = new BaseXpTextViewCompatImpl();
        }
    }

    private XpTextViewCompat() {}

    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return IMPL.getCompoundDrawablesRelative(textView);
    }

    public static void setCompoundDrawableTintList(@NonNull TextView textView, @Nullable ColorStateList tint) {
        IMPL.setCompoundDrawableTintList(textView, tint);
    }

    @Nullable
    public static ColorStateList getCompoundDrawableTintList(@NonNull TextView textView) {
        return IMPL.getCompoundDrawableTintList(textView);
    }

    public static void setCompoundDrawableTintMode(@NonNull TextView textView, @Nullable PorterDuff.Mode tintMode) {
        IMPL.setCompoundDrawableTintMode(textView, tintMode);
    }

    @Nullable
    public static PorterDuff.Mode getCompoundDrawableTintMode(@NonNull TextView textView) {
        return IMPL.getCompoundDrawableTintMode(textView);
    }

    interface XpTextViewCompatImpl {
        Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView);

        void setCompoundDrawableTintList(@NonNull TextView textView, @Nullable ColorStateList tint);

        @Nullable
        ColorStateList getCompoundDrawableTintList(@NonNull TextView textView);

        void setCompoundDrawableTintMode(@NonNull TextView textView, @Nullable PorterDuff.Mode tintMode);

        @Nullable
        PorterDuff.Mode getCompoundDrawableTintMode(@NonNull TextView textView);
    }

    @TargetApi(4)
    static class BaseXpTextViewCompatImpl implements XpTextViewCompatImpl {
        @Override
        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawables();
        }

        @Override
        public void setCompoundDrawableTintList(@NonNull TextView textView, @Nullable ColorStateList tint) {
            if (textView instanceof TintableCompoundDrawableView) {
                ((TintableCompoundDrawableView) textView).setSupportCompoundDrawableTintList(tint);
            }
        }

        @Nullable
        @Override
        public ColorStateList getCompoundDrawableTintList(@NonNull TextView textView) {
            if (textView instanceof TintableCompoundDrawableView) {
                return ((TintableCompoundDrawableView) textView).getSupportCompoundDrawableTintList();
            }
            return null;
        }

        @Override
        public void setCompoundDrawableTintMode(@NonNull TextView textView, @Nullable PorterDuff.Mode tintMode) {
            if (textView instanceof TintableCompoundDrawableView) {
                ((TintableCompoundDrawableView) textView).setSupportCompoundDrawableTintMode(tintMode);
            }
        }

        @Nullable
        @Override
        public PorterDuff.Mode getCompoundDrawableTintMode(@NonNull TextView textView) {
            if (textView instanceof TintableCompoundDrawableView) {
                return ((TintableCompoundDrawableView) textView).getSupportCompoundDrawableTintMode();
            }
            return null;
        }
    }

    @TargetApi(17)
    static class JbMr1XpTextViewCompatImpl extends BaseXpTextViewCompatImpl {
        @Override
        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            final boolean rtl = textView.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
            final Drawable[] result = textView.getCompoundDrawables();
            if (rtl) {
                Drawable temp = result[0]; // temp = left
                result[0] = result[2]; // left = right
                result[2] = temp; // right = temp
            }
            return result;
        }
    }

    @TargetApi(18)
    static class JbMr2XpTextViewCompatImpl extends JbMr1XpTextViewCompatImpl {
        @Override
        public Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }
    }

    /**
     * This should work since Marshmallow but tinting start and end compound drawables is broken.
     */
    @TargetApi(23)
    static class NougatXpTextViewCompatImpl extends JbMr2XpTextViewCompatImpl {
        @Override
        public void setCompoundDrawableTintList(@NonNull TextView textView, @Nullable ColorStateList tint) {
            textView.setCompoundDrawableTintList(tint);
        }

        @Nullable
        @Override
        public ColorStateList getCompoundDrawableTintList(@NonNull TextView textView) {
            return textView.getCompoundDrawableTintList();
        }

        @Override
        public void setCompoundDrawableTintMode(@NonNull TextView textView, @Nullable PorterDuff.Mode tintMode) {
            textView.setCompoundDrawableTintMode(tintMode);
        }

        @Nullable
        @Override
        public PorterDuff.Mode getCompoundDrawableTintMode(@NonNull TextView textView) {
            return textView.getCompoundDrawableTintMode();
        }
    }
}
