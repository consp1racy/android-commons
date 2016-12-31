/*
 * Copyright (C) 2015 The Android Open Source Project
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

package android.support.design.widget;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * Lollipop version of {@link RoundedRectangleBorderDrawable}.
 */
@RequiresApi(21)
@TargetApi(21)
class RoundedRectangleBorderDrawableLollipop extends RoundedRectangleBorderDrawable {
    static class BorderDrawableStateLollipop extends BorderDrawableState {
        @NonNull
        @Override
        public Drawable newDrawable() {
            RoundedRectangleBorderDrawable d = new RoundedRectangleBorderDrawable();
            d.setBorderWidth(mBorderWidth);
            d.setCornerRadius(mCornerRadius);
            d.setBorderTint(mBorderTint);
            return d;
        }
    }

    @Override
    public void getOutline(@NonNull Outline outline) {
        copyBounds(mRect);
        outline.setRoundRect(mRect, mDrawableState.mCornerRadius);
    }

    @Override
    protected BorderDrawableState createConstantState() {
        return new BorderDrawableStateLollipop();
    }
}
