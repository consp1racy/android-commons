package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.TextViewCompat;

@TargetApi(23)
public final class XpTextViewCompat {
    private static final XpTextViewCompatImpl IMPL;

    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 24) {
            IMPL = new NougatXpTextViewCompatImpl();
        } else {
            IMPL = new BaseXpTextViewCompatImpl();
        }
    }

    private XpTextViewCompat() {}

    /**
     * @deprecated Use {@link TextViewCompat#getCompoundDrawablesRelative(TextView)}.
     */
    @Deprecated
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return TextViewCompat.getCompoundDrawablesRelative(textView);
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
        void setCompoundDrawableTintList(@NonNull TextView textView, @Nullable ColorStateList tint);

        @Nullable
        ColorStateList getCompoundDrawableTintList(@NonNull TextView textView);

        void setCompoundDrawableTintMode(@NonNull TextView textView, @Nullable PorterDuff.Mode tintMode);

        @Nullable
        PorterDuff.Mode getCompoundDrawableTintMode(@NonNull TextView textView);
    }

    static class BaseXpTextViewCompatImpl implements XpTextViewCompatImpl {
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

    /**
     * This should work since Marshmallow but tinting start and end compound drawables is broken.
     */
    @RequiresApi(23)
    static class NougatXpTextViewCompatImpl extends BaseXpTextViewCompatImpl {
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
