package support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;

public interface TintableImageView {

    void setSupportImageTintList(@Nullable ColorStateList tint);

    @Nullable
    ColorStateList getSupportImageTintList();

    void setSupportImageTintMode(@Nullable PorterDuff.Mode tintMode);

    @Nullable
    PorterDuff.Mode getSupportImageTintMode();
}
