package net.xpece.android.text.span;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.Px;

import kotlin.jvm.functions.Function4;

import static android.os.Build.VERSION.SDK_INT;
import static android.text.style.BulletSpan.STANDARD_GAP_WIDTH;

@SuppressWarnings("deprecation")
final class BulletSpanCompatFactory {

    // Bullet is slightly bigger to avoid aliasing artifacts on mdpi devices.
    private static final int STANDARD_BULLET_RADIUS = 4;

    private static final Function4<Integer, Integer, Boolean, Integer, BulletSpanCompat> INSTANCE;

    static {
        if (SDK_INT >= 28) {
            INSTANCE = BulletSpanPie::create;
        } else {
            INSTANCE = BulletSpanKitkat::new;
        }
    }

    static BulletSpanCompat create(
            @Px int gapWidth,
            @Nullable @ColorInt Integer color,
            @Px int bulletRadius) {
        boolean wantColor = color != null;
        return INSTANCE.invoke(gapWidth, wantColor ? color : 0, wantColor, bulletRadius);
    }

    static BulletSpanCompat create(
            @Px int gapWidth,
            @ColorInt int color) {
        return create(gapWidth, color, STANDARD_BULLET_RADIUS);
    }

    static BulletSpanCompat create(
            @Px int gapWidth) {
        return create(gapWidth, null, STANDARD_BULLET_RADIUS);
    }

    static BulletSpanCompat create() {
        return create(STANDARD_GAP_WIDTH, null, STANDARD_BULLET_RADIUS);
    }

    private BulletSpanCompatFactory() {
        throw new AssertionError();
    }
}
