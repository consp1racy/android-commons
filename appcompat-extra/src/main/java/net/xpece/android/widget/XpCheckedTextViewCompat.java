package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.CheckedTextView;

import java.lang.reflect.Field;

@TargetApi(23)
public final class XpCheckedTextViewCompat {
    private static final XpCheckedTextViewCompatImpl IMPL;

    static final String TAG = XpCheckedTextViewCompat.class.getSimpleName();

    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            IMPL = new LollipopXpCheckedTextViewCompatImpl();
        } else if (version >= 16) {
            IMPL = new JellyBeanXpCheckedTextViewCompatImpl();
        } else {
            IMPL = new BaseXpCheckedTextViewCompatImpl();
        }
    }

    private XpCheckedTextViewCompat() {}

    @Nullable
    public static Drawable getCheckMarkDrawable(@NonNull CheckedTextView textView) {
        return IMPL.getCheckMarkDrawable(textView);
    }

    public static void setCheckMarkTintList(@NonNull CheckedTextView textView, @Nullable ColorStateList tint) {
        IMPL.setCheckMarkTintList(textView, tint);
    }

    @Nullable
    public static ColorStateList getCheckMarkTintList(@NonNull CheckedTextView textView) {
        return IMPL.getCheckMarkTintList(textView);
    }

    public static void setCheckMarkTintMode(@NonNull CheckedTextView textView, @Nullable PorterDuff.Mode tintMode) {
        IMPL.setCheckMarkTintMode(textView, tintMode);
    }

    @Nullable
    public static PorterDuff.Mode getCheckMarkTintMode(@NonNull CheckedTextView textView) {
        return IMPL.getCheckMarkTintMode(textView);
    }

    interface XpCheckedTextViewCompatImpl {
        @Nullable
        Drawable getCheckMarkDrawable(@NonNull CheckedTextView textView);

        void setCheckMarkTintList(@NonNull CheckedTextView textView, @Nullable ColorStateList tint);

        @Nullable
        ColorStateList getCheckMarkTintList(@NonNull CheckedTextView textView);

        void setCheckMarkTintMode(@NonNull CheckedTextView textView,
                @Nullable PorterDuff.Mode tintMode);

        @Nullable
        PorterDuff.Mode getCheckMarkTintMode(@NonNull CheckedTextView textView);
    }

    static class BaseXpCheckedTextViewCompatImpl implements XpCheckedTextViewCompatImpl {

        private static final Field CHECK_MARK_DRAWABLE_FIELD;

        static {
            Field f = null;
            try {
                f = CheckedTextView.class.getDeclaredField("mCheckMarkDrawable");
                f.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.w(TAG, "Couldn't find field CheckedTextView.mCheckMarkDrawable. Oh well...", e);
            }
            CHECK_MARK_DRAWABLE_FIELD = f;
        }

        @Nullable
        @Override
        public Drawable getCheckMarkDrawable(@NonNull final CheckedTextView textView) {
            if (CHECK_MARK_DRAWABLE_FIELD != null) {
                try {
                    return (Drawable) CHECK_MARK_DRAWABLE_FIELD.get(textView);
                } catch (IllegalAccessException e) {
                    Log.w(TAG, "Couldn't get value of CheckedTextView.mCheckMarkDrawable. Oh well...", e);
                }
            }
            return null;
        }

        @Override
        public void setCheckMarkTintList(@NonNull final CheckedTextView textView,
                @Nullable final ColorStateList tint) {
            if (textView instanceof TintableCheckMarkView) {
                ((TintableCheckMarkView) textView).setSupportCheckMarkTintList(tint);
            }
        }

        @Nullable
        @Override
        public ColorStateList getCheckMarkTintList(@NonNull final CheckedTextView textView) {
            if (textView instanceof TintableCheckMarkView) {
                return ((TintableCheckMarkView) textView).getSupportCheckMarkTintList();
            }
            return null;
        }

        @Override
        public void setCheckMarkTintMode(@NonNull final CheckedTextView textView,
                @Nullable final PorterDuff.Mode tintMode) {
            if (textView instanceof TintableCheckMarkView) {
                ((TintableCheckMarkView) textView).setSupportCheckMarkTintMode(tintMode);
            }
        }

        @Nullable
        @Override
        public PorterDuff.Mode getCheckMarkTintMode(@NonNull final CheckedTextView textView) {
            if (textView instanceof TintableCheckMarkView) {
                return ((TintableCheckMarkView) textView).getSupportCheckMarkTintMode();
            }
            return null;
        }
    }

    static class JellyBeanXpCheckedTextViewCompatImpl extends BaseXpCheckedTextViewCompatImpl {

        @Nullable
        @Override
        public Drawable getCheckMarkDrawable(@NonNull final CheckedTextView textView) {
            return textView.getCheckMarkDrawable();
        }
    }

    static class LollipopXpCheckedTextViewCompatImpl extends JellyBeanXpCheckedTextViewCompatImpl {
        @Override
        public void setCheckMarkTintList(@NonNull final CheckedTextView textView,
                @Nullable final ColorStateList tint) {
            textView.setCheckMarkTintList(tint);
        }

        @Nullable
        @Override
        public ColorStateList getCheckMarkTintList(@NonNull final CheckedTextView textView) {
            return textView.getCheckMarkTintList();
        }

        @Override
        public void setCheckMarkTintMode(@NonNull final CheckedTextView textView,
                @Nullable final PorterDuff.Mode tintMode) {
            textView.setCheckMarkTintMode(tintMode);
        }

        @Nullable
        @Override
        public PorterDuff.Mode getCheckMarkTintMode(@NonNull final CheckedTextView textView) {
            return textView.getCheckMarkTintMode();
        }
    }
}
