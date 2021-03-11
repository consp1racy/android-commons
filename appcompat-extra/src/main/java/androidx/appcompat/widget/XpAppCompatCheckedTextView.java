/*
 * Copyright (C) 2014 The Android Open Source Project
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

package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import net.xpece.android.appcompatextra.R;
import net.xpece.android.widget.TintableCheckMarkView;

/**
 * {@link android.widget.CheckedTextView} which supports compound drawable tint on all platforms.
 */
@SuppressWarnings("deprecation")
public class XpAppCompatCheckedTextView extends AppCompatCheckedTextView implements
    TintableCheckMarkView {

    private final XpAppCompatCheckMarkHelper mTextCheckMarkHelper;

    public XpAppCompatCheckedTextView(Context context) {
        this(context, null);
    }

    public XpAppCompatCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.checkedTextViewStyle);
    }

    public XpAppCompatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextCheckMarkHelper = new XpAppCompatCheckMarkHelper(this);
        mTextCheckMarkHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.applySupportTint();
        }
    }

    @Override
    public void setSupportCheckMarkTintList(@Nullable final ColorStateList tint) {
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.setSupportTintList(tint);
        }
    }

    @Nullable
    @Override
    public ColorStateList getSupportCheckMarkTintList() {
        if (mTextCheckMarkHelper != null) {
            return mTextCheckMarkHelper.getSupportTintList();
        }
        return null;
    }

    @Override
    public void setSupportCheckMarkTintMode(@Nullable final PorterDuff.Mode tintMode) {
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.setSupportTintMode(tintMode);
        }
    }

    @Nullable
    @Override
    public PorterDuff.Mode getSupportCheckMarkTintMode() {
        if (mTextCheckMarkHelper != null) {
            return mTextCheckMarkHelper.getSupportTintMode();
        }
        return null;
    }

    @Override
    public void setCheckMarkDrawable(@Nullable final Drawable d) {
        super.setCheckMarkDrawable(d);
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.applySupportTint();
        }
    }

    @Override
    public void setCheckMarkDrawable(@DrawableRes final int resId) {
        super.setCheckMarkDrawable(resId);
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.applySupportTint();
        }
    }
}
