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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Span that updates the typeface of the text it's attached to. The <code>TypefaceSpan</code> can
 * be constructed either based on a font family or based on a <code>Typeface</code>. When
 * {@link #TypefaceSpanCompatImpl(String)} is used, the previous style of the <code>TextView</code> is kept.
 * When {@link #TypefaceSpanCompatImpl(Typeface)} is used, the <code>Typeface</code> style replaces the
 * <code>TextView</code>'s style.
 * <p>
 * For example, let's consider a <code>TextView</code> with
 * <code>android:textStyle="italic"</code> and a typeface created based on a font from resources,
 * with a bold style. When applying a <code>TypefaceSpan</code> based the typeface, the text will
 * only keep the bold style, overriding the <code>TextView</code>'s textStyle. When applying a
 * <code>TypefaceSpan</code> based on a font family: "monospace", the resulted text will keep the
 * italic style.
 * <pre>
 * Typeface myTypeface = Typeface.create(ResourcesCompat.getFont(context, R.font.acme),
 * Typeface.BOLD);
 * SpannableString string = new SpannableString("Text with typeface span.");
 * string.setSpan(new TypefaceSpan(myTypeface), 10, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 * string.setSpan(new TypefaceSpan("monospace"), 19, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 * </pre>
 * <img src="{@docRoot}reference/android/images/text/style/typefacespan.png" />
 * <figcaption>Text with <code>TypefaceSpan</code>s constructed based on a font from resource and
 * from a font family.</figcaption>
 */
final class TypefaceSpanCompatImpl extends TypefaceSpan {

    @Nullable
    private final Typeface mTypeface;

    /**
     * Constructs a {@link TypefaceSpanCompatImpl} based on the font family. The previous style of the
     * TextPaint is kept. If the font family is null, the text paint is not modified.
     *
     * @param family The font family for this typeface.  Examples include
     *               "monospace", "serif", and "sans-serif"
     */
    public TypefaceSpanCompatImpl(@Nullable String family) {
        this(family, null);
    }

    /**
     * Constructs a {@link TypefaceSpanCompatImpl} from a {@link Typeface}. The previous style of the
     * TextPaint is overridden and the style of the typeface is used.
     *
     * @param typeface the typeface
     */
    public TypefaceSpanCompatImpl(@NonNull Typeface typeface) {
        this(null, typeface);
    }

    private TypefaceSpanCompatImpl(@Nullable String family, @Nullable Typeface typeface) {
        super(family);
        mTypeface = typeface;
    }

    /**
     * Returns the font family name set in the span.
     *
     * @return the font family name
     * @see #TypefaceSpanCompatImpl(String)
     */
    @Nullable
    @Override
    public String getFamily() {
        return super.getFamily();
    }

    /**
     * Returns the typeface set in the span.
     *
     * @return the typeface set
     * @see #TypefaceSpanCompatImpl(Typeface)
     */
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
}
