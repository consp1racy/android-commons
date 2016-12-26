package net.xpece.android.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.GROUP_ID)
public interface TintableImageView {

    void setSupportImageTintList(@Nullable ColorStateList tint);

    @Nullable
    ColorStateList getSupportImageTintList();

    void setSupportImageTintMode(@Nullable PorterDuff.Mode tintMode);

    @Nullable
    PorterDuff.Mode getSupportImageTintMode();
}
