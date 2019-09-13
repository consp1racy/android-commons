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

package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;

import net.xpece.android.appcompatextra.R;

/**
 * {@link android.widget.ImageButton} which supports image drawable tint on all platforms.
 */
public class XpAppCompatImageButton extends AppCompatImageButton {

    private XpAppCompatImageHelper mImageTintHelper;

    public XpAppCompatImageButton(Context context) {
        this(context, null);
    }

    public XpAppCompatImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.imageButtonStyle);
    }

    public XpAppCompatImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mImageTintHelper = new XpAppCompatImageHelper(this);
        mImageTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }
}
