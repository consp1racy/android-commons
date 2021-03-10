/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.xpece.android.text.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;

import java.util.WeakHashMap;

@SuppressWarnings("deprecation")
final class BulletSpanKitkat implements BulletSpanCompat {

    @Px
    private final int mGapWidth;
    @Px
    private final int mBulletRadius;
    private Path mBulletPath = null;
    @ColorInt
    private final int mColor;
    private final boolean mWantColor;

    BulletSpanKitkat(@Px int gapWidth, @ColorInt int color, boolean wantColor,
                     @Px int bulletRadius) {
        mGapWidth = gapWidth;
        mBulletRadius = bulletRadius;
        mColor = color;
        mWantColor = wantColor;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return 2 * mBulletRadius + mGapWidth;
    }

    @Override
    public int getGapWidth() {
        return mGapWidth;
    }

    @Override
    public int getBulletRadius() {
        return mBulletRadius;
    }

    @Override
    public int getColor() {
        return mColor;
    }

    private final WeakHashMap<Layout, StaticLayout> layouts = new WeakHashMap<>();

    @SuppressWarnings("deprecation")
    private StaticLayout getSimpleLayout(Layout layout) {
        StaticLayout out = layouts.get(layout);
        if (out == null) {
            out = new StaticLayout(layout.getText(), layout.getPaint(), layout.getWidth(), layout.getAlignment(), 1f, 0f, false);
            layouts.put(layout, out);
        }
        return out;
    }

    @Override
    public void drawLeadingMargin(
            @NonNull Canvas canvas, @NonNull Paint paint,
            int x, int dir, int top, int baseline, int bottom,
            @NonNull CharSequence text, int start, int end,
            boolean first, @Nullable Layout layout) {
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = paint.getStyle();
            int oldcolor = 0;

            if (mWantColor) {
                oldcolor = paint.getColor();
                paint.setColor(mColor);
            }

            paint.setStyle(Paint.Style.FILL);

            if (layout != null) {
                final int line = layout.getLineForOffset(start);
                top = baseline + layout.getLineAscent(line);
                bottom = baseline + layout.getLineDescent(line);

                boolean hasSpacing = layout.getSpacingMultiplier() != 1f || layout.getSpacingAdd() != 0f;
                if (hasSpacing) {
                    // Spacing is included in computed descent. Re-measure without spacing.
                    final int lineHeight = getSimpleLayout(layout).getLineBottom(0);
                    bottom = top + lineHeight;
                }

                if (line == 0) {
                    top -= layout.getTopPadding();
                    if (hasSpacing) {
                        bottom -= layout.getTopPadding();
                    }
                }
                if (line == layout.getLineCount() - 1) {
                    // TODO Verify.
                    if (hasSpacing) {
                        top -= layout.getBottomPadding();
                    }
                    bottom -= layout.getBottomPadding();
                }
            }


            final float yPosition = (top + bottom) / 2f;
            final float xPosition = x + dir * mBulletRadius;

            if (canvas.isHardwareAccelerated()) {
                if (mBulletPath == null) {
                    mBulletPath = new Path();
                    mBulletPath.addCircle(0.0f, 0.0f, mBulletRadius, Direction.CW);
                }

                canvas.save();
                canvas.translate(xPosition, yPosition);
                canvas.drawPath(mBulletPath, paint);
                canvas.restore();
            } else {
                canvas.drawCircle(xPosition, yPosition, mBulletRadius, paint);
            }

            if (mWantColor) {
                paint.setColor(oldcolor);
            }

            paint.setStyle(style);
        }
    }
}
