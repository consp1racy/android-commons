/*
 * Copyright (C) 2008 The Android Open Source Project
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

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;

import net.xpece.android.content.XpResources;
import net.xpece.android.graphics.WeightTypefaceCompat;

/**
 * Sets the text appearance using the given
 * {@code TextAppearance} attributes.
 * By default {@code TextAppearanceSpan} only changes the specified attributes in XML.
 * {@code textColorHighlight},
 * {@code textColorHint},
 * {@code textAllCaps} and
 * {@code fallbackLineSpacing}
 * are not supported by {@code TextAppearanceSpan}.
 *
 * @see android.widget.TextView#setTextAppearance(int)
 * @see android.R.attr#fontFamily
 * @see android.R.attr#textColor
 * @see android.R.attr#textColorLink
 * @see android.R.attr#textFontWeight
 * @see android.R.attr#textSize
 * @see android.R.attr#textStyle
 * @see android.R.attr#typeface
 * @see android.R.attr#shadowColor
 * @see android.R.attr#shadowDx
 * @see android.R.attr#shadowDy
 * @see android.R.attr#shadowRadius
 * @see android.R.attr#elegantTextHeight
 * @see android.R.attr#letterSpacing
 * @see android.R.attr#fontFeatureSettings
 * @see android.R.attr#fontVariationSettings
 */
final class TextAppearanceSpanCompatImpl extends MetricAffectingSpan {

    private static final String TAG = "TextAppearanceSpan";

    /**
     * A maximum weight value for the font
     */
    private static final int FONT_WEIGHT_MAX = 1000;

    private final String mFamilyName;
    private final int mStyle;
    private final int mTextSize;
    private final ColorStateList mTextColor;
    private final ColorStateList mTextColorLink;
    private final Typeface mTypeface;

    private final int mTextFontWeight;
    private final LocaleList mTextLocales;

    private final float mShadowRadius;
    private final float mShadowDx;
    private final float mShadowDy;
    private final int mShadowColor;

    private final boolean mHasElegantTextHeight;
    private final boolean mElegantTextHeight;
    private final boolean mHasLetterSpacing;
    private final float mLetterSpacing;

    private final String mFontFeatureSettings;
    private final String mFontVariationSettings;

    // Context is required to create styled typefaces on Android 4.x.
    private final Context mContext;

    /**
     * Uses the specified TextAppearance resource to determine the
     * text appearance.  The <code>appearance</code> should be, for example,
     * <code>android.R.style.TextAppearance_Small</code>.
     */
    public TextAppearanceSpanCompatImpl(@NonNull Context context, @StyleRes int appearance) {
        final TypedArray a = context.obtainStyledAttributes(appearance, R.styleable.TextAppearanceSpanCompat);

        mTextColor = XpResources.getColorStateListCompat(a, context, R.styleable.TextAppearanceSpanCompat_android_textColor);
        mTextColorLink = XpResources.getColorStateListCompat(a, context, R.styleable.TextAppearanceSpanCompat_android_textColorLink);
        mTextSize = a.getDimensionPixelSize(R.styleable.TextAppearanceSpanCompat_android_textSize, -1);

        mStyle = a.getInt(R.styleable.TextAppearanceSpanCompat_android_textStyle, 0);
        if (!context.isRestricted()) {
            final int resId = a.getResourceId(R.styleable.TextAppearanceSpanCompat_android_fontFamily, 0);
            mTypeface = ResourcesCompat.getFont(context, resId);
        } else {
            mTypeface = null;
        }
        if (mTypeface != null) {
            mFamilyName = null;
        } else {
            final String family = a.getString(R.styleable.TextAppearanceSpanCompat_android_fontFamily);
            if (family != null) {
                mFamilyName = family;
            } else {
                final int tf = a.getInt(R.styleable.TextAppearanceSpanCompat_android_typeface, 0);
                switch (tf) {
                    case 1:
                        mFamilyName = "sans";
                        break;
                    case 2:
                        mFamilyName = "serif";
                        break;
                    case 3:
                        mFamilyName = "monospace";
                        break;
                    default:
                        mFamilyName = null;
                        break;
                }
            }
        }

        final int textFontWeight;
        if (a.hasValue(R.styleable.TextAppearanceSpanCompat_textFontWeight)) {
            textFontWeight = a.getInt(R.styleable.TextAppearanceSpanCompat_textFontWeight, -1);
        } else {
            textFontWeight = a.getInt(R.styleable.TextAppearanceSpanCompat_android_textFontWeight, -1);
        }
        mTextFontWeight = textFontWeight;

        if (Build.VERSION.SDK_INT >= 24) {
            final String localeString = a.getString(R.styleable.TextAppearanceSpanCompat_android_textLocale);
            if (localeString != null) {
                final LocaleList localeList = LocaleList.forLanguageTags(localeString);
                if (!localeList.isEmpty()) {
                    mTextLocales = localeList;
                } else {
                    mTextLocales = null;
                }
            } else {
                mTextLocales = null;
            }
        } else {
            mTextLocales = null;
        }

        mShadowRadius = a.getFloat(R.styleable.TextAppearanceSpanCompat_android_shadowRadius, 0.0f);
        mShadowDx = a.getFloat(R.styleable.TextAppearanceSpanCompat_android_shadowDx, 0.0f);
        mShadowDy = a.getFloat(R.styleable.TextAppearanceSpanCompat_android_shadowDy, 0.0f);
        mShadowColor = XpResources.getColorCompat(a, context, R.styleable.TextAppearanceSpanCompat_android_shadowColor, Color.TRANSPARENT);

        if (Build.VERSION.SDK_INT >= 21) {
            mHasElegantTextHeight = a.hasValue(R.styleable.TextAppearanceSpanCompat_android_elegantTextHeight);
            mElegantTextHeight = a.getBoolean(R.styleable.TextAppearanceSpanCompat_android_elegantTextHeight, false);

            mHasLetterSpacing = a.hasValue(R.styleable.TextAppearanceSpanCompat_android_letterSpacing);
            mLetterSpacing = a.getFloat(R.styleable.TextAppearanceSpanCompat_android_letterSpacing, 0.0f);

            mFontFeatureSettings = a.getString(R.styleable.TextAppearanceSpanCompat_android_fontFeatureSettings);
        } else {
            mHasElegantTextHeight = false;
            mElegantTextHeight = false;

            mHasLetterSpacing = false;
            mLetterSpacing = 0.0f;

            mFontFeatureSettings = null;
        }

        if (Build.VERSION.SDK_INT >= 26) {
            mFontVariationSettings = a.getString(R.styleable.TextAppearanceSpanCompat_android_fontVariationSettings);
        } else {
            mFontVariationSettings = null;
        }

        a.recycle();

        if (needsContext()) {
            mContext = context;
        } else {
            mContext = null;
        }
    }

    private boolean needsContext() {
        return (Build.VERSION.SDK_INT < 21 && mTypeface != null && mTypeface.getStyle() != mStyle)
                || (Build.VERSION.SDK_INT < 21 && mTextFontWeight >= 0 && mTypeface != null);
    }

//    /**
//     * Returns the typeface family specified by this span, or <code>null</code>
//     * if it does not specify one.
//     */
//    @Nullable
//    public String getFamily() {
//        return mFamilyName;
//    }
//
//    /**
//     * Returns the text color specified by this span, or <code>null</code>
//     * if it does not specify one.
//     */
//    @Nullable
//    public ColorStateList getTextColor() {
//        return mTextColor;
//    }
//
//    /**
//     * Returns the link color specified by this span, or <code>null</code>
//     * if it does not specify one.
//     */
//    @Nullable
//    public ColorStateList getLinkTextColor() {
//        return mTextColorLink;
//    }
//
//    /**
//     * Returns the text size specified by this span, or <code>-1</code>
//     * if it does not specify one.
//     */
//    @Px
//    public int getTextSize() {
//        return mTextSize;
//    }
//
//    /**
//     * Returns the text style specified by this span, or <code>0</code>
//     * if it does not specify one.
//     */
//    public int getTextStyle() {
//        return mStyle;
//    }
//
//    /**
//     * Returns the text font weight specified by this span, or <code>-1</code>
//     * if it does not specify one.
//     */
//    public int getTextFontWeight() {
//        return mTextFontWeight;
//    }
//
//    /**
//     * Returns the {@link android.os.LocaleList} specified by this span, or <code>null</code>
//     * if it does not specify one.
//     */
//    @RequiresApi(24)
//    @Nullable
//    public LocaleList getTextLocales() {
//        return mTextLocales;
//    }
//
//    /**
//     * Returns the typeface specified by this span, or <code>null</code>
//     * if it does not specify one.
//     */
//    @Nullable
//    public Typeface getTypeface() {
//        return mTypeface;
//    }
//
//    /**
//     * Returns the color of the text shadow specified by this span, or <code>0</code>
//     * if it does not specify one.
//     */
//    @ColorInt
//    public int getShadowColor() {
//        return mShadowColor;
//    }
//
//    /**
//     * Returns the horizontal offset of the text shadow specified by this span, or <code>0.0f</code>
//     * if it does not specify one.
//     */
//    @Px
//    public float getShadowDx() {
//        return mShadowDx;
//    }
//
//    /**
//     * Returns the vertical offset of the text shadow specified by this span, or <code>0.0f</code>
//     * if it does not specify one.
//     */
//    @Px
//    public float getShadowDy() {
//        return mShadowDy;
//    }
//
//    /**
//     * Returns the blur radius of the text shadow specified by this span, or <code>0.0f</code>
//     * if it does not specify one.
//     */
//    @Px
//    public float getShadowRadius() {
//        return mShadowRadius;
//    }
//
//    /**
//     * Returns the font feature settings specified by this span, or <code>null</code>
//     * if it does not specify one.
//     */
//    @Nullable
//    public String getFontFeatureSettings() {
//        return mFontFeatureSettings;
//    }
//
//    /**
//     * Returns the font variation settings specified by this span, or <code>null</code>
//     * if it does not specify one.
//     */
//    @Nullable
//    public String getFontVariationSettings() {
//        return mFontVariationSettings;
//    }
//
//    /**
//     * Returns the value of elegant height metrics flag specified by this span,
//     * or <code>false</code> if it does not specify one.
//     */
//    public boolean isElegantTextHeight() {
//        return mElegantTextHeight;
//    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        updateMeasureState(ds);

        if (mTextColor != null) {
            ds.setColor(mTextColor.getColorForState(ds.drawableState, 0));
        }

        if (mTextColorLink != null) {
            ds.linkColor = mTextColorLink.getColorForState(ds.drawableState, 0);
        }

        if (mShadowColor != 0) {
            ds.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
        }
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint ds) {
        final Typeface styledTypeface;
        int style = 0;

        if (mTypeface != null) {
            style = mStyle;
            if (mTypeface.getStyle() == style) {
                // Return early if we're asked for the same face/style
                styledTypeface = mTypeface;
            } else if (Build.VERSION.SDK_INT >= 21) {
                styledTypeface = Typeface.create(mTypeface, style);
            } else {
                // Context must not be null in this path
                styledTypeface = TypefaceCompat.create(mContext, mTypeface, style);
            }
        } else if (mFamilyName != null || mStyle != 0) {
            final Typeface tf = ds.getTypeface();

            if (tf != null) {
                style = tf.getStyle();
            }

            style |= mStyle;

            if (mFamilyName != null) {
                styledTypeface = Typeface.create(mFamilyName, style);
            } else if (tf == null) {
                styledTypeface = Typeface.defaultFromStyle(style);
            } else {
                styledTypeface = Typeface.create(tf, style);
            }
        } else {
            styledTypeface = null;
        }

        if (styledTypeface != null) {
            Typeface readyTypeface;
            if (mTextFontWeight >= 0) {
                final int weight = Math.min(FONT_WEIGHT_MAX, mTextFontWeight);
                final boolean italic = (style & Typeface.ITALIC) != 0;
                readyTypeface = WeightTypefaceCompat.createInternal(mContext, styledTypeface, weight, italic);
            } else {
                readyTypeface = styledTypeface;
            }

            final int fake = style & ~readyTypeface.getStyle();

            if ((fake & Typeface.BOLD) != 0) {
                ds.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                ds.setTextSkewX(-0.25f);
            }

            ds.setTypeface(readyTypeface);
        }

        if (mTextSize > 0) {
            ds.setTextSize(mTextSize);
        }

        if (Build.VERSION.SDK_INT >= 24 && mTextLocales != null) {
            ds.setTextLocales(mTextLocales);
        }

        if (Build.VERSION.SDK_INT >= 21 && mHasElegantTextHeight) {
            ds.setElegantTextHeight(mElegantTextHeight);
        }

        if (Build.VERSION.SDK_INT >= 21 && mHasLetterSpacing) {
            ds.setLetterSpacing(mLetterSpacing);
        }

        if (Build.VERSION.SDK_INT >= 21 && mFontFeatureSettings != null) {
            ds.setFontFeatureSettings(mFontFeatureSettings);
        }

        if (Build.VERSION.SDK_INT >= 26 && mFontVariationSettings != null) {
            ds.setFontVariationSettings(mFontVariationSettings);
        }
    }
}
