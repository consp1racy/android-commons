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

package support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A {@link ImageView} which supports compatible features on older version of the platform,
 * including:
 * <ul>
 * <li>Allows dynamic tint of it background via the background tint methods in
 * {@link android.support.v4.view.ViewCompat}.</li>
 * <li>Allows setting of the background tint using {@link android.support.v7.appcompat.R.attr#backgroundTint} and
 * {@link android.support.v7.appcompat.R.attr#backgroundTintMode}.</li>
 * </ul>
 * <p>
 * <p>This will automatically be used when you use {@link ImageView} in your
 * layouts. You should only need to manually use this class when writing custom views.</p>
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
        super(TintContextWrapper.wrap(context), attrs, defStyleAttr);

        final AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
        mImageHelper = new XpAppCompatImageHelper(this, drawableManager);
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
