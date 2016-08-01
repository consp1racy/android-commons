package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;

public interface TintableCompoundDrawableView {

    void setSupportCompoundDrawableTintList(@Nullable ColorStateList tint);

    @Nullable
    ColorStateList getSupportCompoundDrawableTintList();

    void setSupportCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode);

    @Nullable
    PorterDuff.Mode getSupportCompoundDrawableTintMode();
}
