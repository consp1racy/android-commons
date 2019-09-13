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

package com.google.android.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Utility methods to check Theme compatibility with components.
 *
 * Utils class for custom views.
 */
@RestrictTo(LIBRARY)
final class CardButtonUtils {

    private static final int[] APPCOMPAT_CHECK_ATTRS = {net.xpece.android.widget.cardbutton.R.attr.colorPrimary};
    private static final String APPCOMPAT_THEME_NAME = "Theme.AppCompat";

    private CardButtonUtils() {
    }

    public static void checkAppCompatTheme(@NonNull Context context) {
        checkTheme(context, APPCOMPAT_CHECK_ATTRS, APPCOMPAT_THEME_NAME);
    }

    private static void checkTheme(@NonNull Context context, @NonNull int[] themeAttributes, @NonNull String themeName) {
        TypedArray a = context.obtainStyledAttributes(themeAttributes);
        final boolean failed = !a.hasValue(0);
        a.recycle();

        if (failed) {
            throw new IllegalArgumentException(
                    "The style on this component requires your app theme to be "
                            + themeName
                            + " (or a descendant).");
        }
    }

    @NonNull
    public static PorterDuff.Mode parseTintMode(int value, @NonNull PorterDuff.Mode defaultMode) {
        switch (value) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return defaultMode;
        }
    }
}
