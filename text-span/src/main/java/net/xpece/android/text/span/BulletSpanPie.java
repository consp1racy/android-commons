package net.xpece.android.text.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.text.Layout;
import android.text.style.BulletSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(28)
final class BulletSpanPie extends BulletSpan implements BulletSpanCompat {

    private final boolean enableColorChange;

    private LockedPaint tempPaint;

    BulletSpanPie(
            int gapWidth,
            @ColorInt int color,
            boolean wantColor,
            @IntRange(from = 0) int bulletRadius) {
        super(gapWidth, color, bulletRadius);
        this.enableColorChange = wantColor;
    }

    public BulletSpanPie(@NonNull Parcel src) {
        super(src);
        this.enableColorChange = true;
    }

    @Override
    public void drawLeadingMargin(@NonNull Canvas canvas, @NonNull Paint paint, int x, int dir, int top, int baseline, int bottom, @NonNull CharSequence text, int start, int end, boolean first, @Nullable Layout layout) {
        if (!enableColorChange) {
            if (tempPaint == null) {
                tempPaint = new LockedPaint();
            }
            tempPaint.set(paint);
            paint = tempPaint;
        }

        if (layout != null) {
            final int line = layout.getLineForOffset(start);
            if (line == 0) {
                top -= layout.getTopPadding();
            }
            if (line == layout.getLineCount() - 1) {
                // TODO Verify.
                bottom -= layout.getBottomPadding();
            }
        }

        super.drawLeadingMargin(canvas, paint, x, dir, top, baseline, bottom, text, start, end, first, layout);
    }

    private static class LockedPaint extends Paint {

        @Override
        public void setColor(int color) {
            // No-op.
        }
    }
}
