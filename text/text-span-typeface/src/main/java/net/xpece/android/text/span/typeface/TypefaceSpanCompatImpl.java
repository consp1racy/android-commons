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

package net.xpece.android.text.span.typeface;

import android.graphics.Typeface;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

final class TypefaceSpanCompatImpl extends TypefaceSpan {

    private final Typeface mTypeface;

    TypefaceSpanCompatImpl(@NonNull Typeface typeface) {
        super((String) null);
        mTypeface = typeface;
    }

    TypefaceSpanCompatImpl(@NonNull Parcel parcel) {
        super(parcel);
        mTypeface = null;
    }

    @Nullable
    @Override
    public Typeface getTypeface() {
        return mTypeface;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        if (mTypeface != null) {
            ds.setTypeface(mTypeface);
        } else {
            super.updateDrawState(ds);
        }
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint paint) {
        if (mTypeface != null) {
            paint.setTypeface(mTypeface);
        } else {
            super.updateMeasureState(paint);
        }
    }

    public static final Creator<TypefaceSpanCompatImpl> CREATOR = new Creator<TypefaceSpanCompatImpl>() {

        @Override
        public TypefaceSpanCompatImpl createFromParcel(Parcel source) {
            return new TypefaceSpanCompatImpl(source);
        }

        @Override
        public TypefaceSpanCompatImpl[] newArray(int size) {
            return new TypefaceSpanCompatImpl[size];
        }
    };
}
