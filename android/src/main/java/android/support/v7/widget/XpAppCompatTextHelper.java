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
import android.os.Build;
import android.support.v7.text.AllCapsTransformationMethod;
import android.support.v7.widget.TintTypedArray;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

class XpAppCompatTextHelper {

    final TextView mView;

    XpAppCompatTextHelper(TextView view) {
        mView = view;
    }

    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        final Context context = mView.getContext();

        // First read the TextAppearance style id
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
                android.support.v7.appcompat.R.styleable.AppCompatTextHelper, defStyleAttr, 0);
        final int ap = a.getResourceId(android.support.v7.appcompat.R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        a.recycle();

        // PasswordTransformationMethod wipes out all other TransformationMethod instances
        // in TextView's constructor, so we should only set a new transformation method
        // if we don't have a PasswordTransformationMethod currently...
        final boolean hasPwdTm =
                mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        boolean allCaps = false;
        boolean allCapsSet = false;
        ColorStateList textColor = null;

        // First check TextAppearance's textAllCaps value
        if (ap != -1) {
            a = TintTypedArray.obtainStyledAttributes(context, ap, android.support.v7.appcompat.R.styleable.TextAppearance);
            if (!hasPwdTm && a.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_textAllCaps)) {
                allCapsSet = true;
                allCaps = a.getBoolean(android.support.v7.appcompat.R.styleable.TextAppearance_textAllCaps, false);
            }
            if (Build.VERSION.SDK_INT < 23
                    && a.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor)) {
                // If we're running on < API 23, the text color may contain theme references
                // so let's re-set using our own inflater
                textColor = a.getColorStateList(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor);
            }
            a.recycle();
        }

        // Now read the style's values
        a = TintTypedArray.obtainStyledAttributes(context, attrs, android.support.v7.appcompat.R.styleable.TextAppearance,
                defStyleAttr, 0);
        if (!hasPwdTm && a.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_textAllCaps)) {
            allCapsSet = true;
            allCaps = a.getBoolean(android.support.v7.appcompat.R.styleable.TextAppearance_textAllCaps, false);
        }
        if (Build.VERSION.SDK_INT < 23
                && a.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor)) {
            // If we're running on < API 23, the text color may contain theme references
            // so let's re-set using our own inflater
            textColor = a.getColorStateList(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor);
        }
        a.recycle();

        if (textColor != null) {
            mView.setTextColor(textColor);
        }

        if (!hasPwdTm && allCapsSet) {
            setAllCaps(allCaps);
        }
    }

    void onSetTextAppearance(Context context, int resId) {
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context,
                resId, android.support.v7.appcompat.R.styleable.TextAppearance);
        if (a.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_textAllCaps)) {
            // This breaks away slightly from the logic in TextView.setTextAppearance that serves
            // as an "overlay" on the current state of the TextView. Since android:textAllCaps
            // may have been set to true in this text appearance, we need to make sure that
            // app:textAllCaps has the chance to override it
            setAllCaps(a.getBoolean(android.support.v7.appcompat.R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23
                && a.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor)) {
            // If we're running on < API 23, the text color may contain theme references
            // so let's re-set using our own inflater
            final ColorStateList textColor
                    = a.getColorStateList(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor);
            if (textColor != null) {
                mView.setTextColor(textColor);
            }
        }
        a.recycle();
    }

    void setAllCaps(boolean allCaps) {
        mView.setTransformationMethod(allCaps
                ? new AllCapsTransformationMethod(mView.getContext())
                : null);
    }
}
