package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * @hide
 */

public final class XpImageViewCompat {
    private static final XpImageViewCompatImpl IMPL;

    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            IMPL = new LollipopXpImageViewCompatImpl();
        } else {
            IMPL = new BaseXpImageViewCompatImpl();
        }
    }

    private XpImageViewCompat() {}

    public static void setImageTintList(@NonNull ImageView imageView, @Nullable ColorStateList tint) {
        IMPL.setImageTintList(imageView, tint);
    }

    @Nullable
    public static ColorStateList getImageTintList(@NonNull ImageView imageView) {
        return IMPL.getImageTintList(imageView);
    }

    public static void setImageTintMode(@NonNull ImageView imageView, @Nullable PorterDuff.Mode tintMode) {
        IMPL.setImageTintMode(imageView, tintMode);
    }

    @Nullable
    public static PorterDuff.Mode getImageTintMode(@NonNull ImageView imageView) {
        return IMPL.getImageTintMode(imageView);
    }

    interface XpImageViewCompatImpl {
        void setImageTintList(@NonNull ImageView imageView, @Nullable ColorStateList tint);

        @Nullable
        ColorStateList getImageTintList(@NonNull ImageView imageView);

        void setImageTintMode(@NonNull ImageView imageView, @Nullable PorterDuff.Mode tintMode);

        @Nullable
        PorterDuff.Mode getImageTintMode(@NonNull ImageView imageView);
    }

    static class BaseXpImageViewCompatImpl implements XpImageViewCompatImpl {
        @Override
        public void setImageTintList(@NonNull ImageView imageView, @Nullable ColorStateList tint) {
            if (imageView instanceof TintableImageView) {
                ((TintableImageView) imageView).setSupportImageTintList(tint);
            }
        }

        @Nullable
        @Override
        public ColorStateList getImageTintList(@NonNull ImageView imageView) {
            if (imageView instanceof TintableImageView) {
                return ((TintableImageView) imageView).getSupportImageTintList();
            }
            return null;
        }

        @Override
        public void setImageTintMode(@NonNull ImageView imageView, @Nullable PorterDuff.Mode tintMode) {
            if (imageView instanceof TintableImageView) {
                ((TintableImageView) imageView).setSupportImageTintMode(tintMode);
            }
        }

        @Nullable
        @Override
        public PorterDuff.Mode getImageTintMode(@NonNull ImageView imageView) {
            if (imageView instanceof TintableImageView) {
                return ((TintableImageView) imageView).getSupportImageTintMode();
            }
            return null;
        }
    }

    @TargetApi(21)
    static class LollipopXpImageViewCompatImpl extends BaseXpImageViewCompatImpl {
        @Override
        public void setImageTintList(@NonNull ImageView imageView, @Nullable ColorStateList tint) {
            imageView.setImageTintList(tint);
        }

        @Nullable
        @Override
        public ColorStateList getImageTintList(@NonNull ImageView imageView) {
            return imageView.getImageTintList();
        }

        @Override
        public void setImageTintMode(@NonNull ImageView imageView, @Nullable PorterDuff.Mode tintMode) {
            imageView.setImageTintMode(tintMode);
        }

        @Nullable
        @Override
        public PorterDuff.Mode getImageTintMode(@NonNull ImageView imageView) {
            return imageView.getImageTintMode();
        }
    }
}
