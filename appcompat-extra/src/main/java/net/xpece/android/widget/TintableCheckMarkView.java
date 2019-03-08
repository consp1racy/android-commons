package net.xpece.android.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.GROUP_ID)
public interface TintableCheckMarkView {

    void setSupportCheckMarkTintList(@Nullable ColorStateList tint);

    @Nullable
    ColorStateList getSupportCheckMarkTintList();

    void setSupportCheckMarkTintMode(@Nullable PorterDuff.Mode tintMode);

    @Nullable
    PorterDuff.Mode getSupportCheckMarkTintMode();
}
