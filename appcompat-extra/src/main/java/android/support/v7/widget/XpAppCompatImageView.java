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

package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import net.xpece.android.widget.TintableImageView;

/**
 * {@link android.widget.ImageView} which supports image drawable tint on all platforms.
 */
public class XpAppCompatImageView extends AppCompatImageView implements TintableImageView {

    private XpAppCompatImageHelper mImageHelper;

    public XpAppCompatImageView(Context context) {
        this(context, null);
    }

    public XpAppCompatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XpAppCompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mImageHelper = new XpAppCompatImageHelper(this);
        mImageHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        if (mImageHelper != null) {
            mImageHelper.onSetImageResource(resId);
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (mImageHelper != null) {
            mImageHelper.onSetImageDrawable(drawable);
        }
    }

    /**
     * @hide
     */
    @Override
    public void setSupportImageTintList(@Nullable ColorStateList tint) {
        if (mImageHelper != null) {
            mImageHelper.setSupportTintList(tint);
        }
    }

    /**
     * @hide
     */
    @Override
    @Nullable
    public ColorStateList getSupportImageTintList() {
        return mImageHelper != null
            ? mImageHelper.getSupportTintList() : null;
    }

    /**
     * @hide
     */
    @Override
    public void setSupportImageTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mImageHelper != null) {
            mImageHelper.setSupportTintMode(tintMode);
        }
    }

    /**
     * @hide
     */
    @Override
    @Nullable
    public PorterDuff.Mode getSupportImageTintMode() {
        return mImageHelper != null
            ? mImageHelper.getSupportTintMode() : null;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mImageHelper != null) {
            mImageHelper.applySupportTint();
        }
    }
}
