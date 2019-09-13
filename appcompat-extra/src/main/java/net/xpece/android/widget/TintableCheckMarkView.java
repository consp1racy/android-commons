package net.xpece.android.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.GROUP_ID)
public interface TintableCheckMarkView {

    void setSupportCheckMarkTintList(@Nullable ColorStateList tint);

    @Nullable
    ColorStateList getSupportCheckMarkTintList();

    void setSupportCheckMarkTintMode(@Nullable PorterDuff.Mode tintMode);

    @Nullable
    PorterDuff.Mode getSupportCheckMarkTintMode();
}
