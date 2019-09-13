package net.xpece.android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.ColorInt;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.os.ParcelableCompat;
import androidx.core.os.ParcelableCompatCreatorCallbacks;
import androidx.customview.view.AbsSavedState;
import androidx.core.widget.TextViewCompat;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.TintTypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.xpece.android.R;

import kotlin.collections.ArraysKt;


@SuppressWarnings("RestrictedApi")
public class ArbitraryInputLayout extends LinearLayoutCompat {
    private static final int[] TEXT_COLOR_ATTR = {android.R.attr.textColor};

    private boolean mHintEnabled;
    private TextView mHintView;
    private int mHintTextAppearance = -1;
    private ColorStateList mHintTextColor = null;
    private CharSequence mHint;

    private boolean mErrorEnabled;
    private TextView mErrorView;
    private int mErrorTextAppearance = -1;
    private ColorStateList mErrorTextColor = null;
    private CharSequence mError;

    private boolean mInDrawableStateChanged;

    public ArbitraryInputLayout(Context context) {
        this(context, null);
    }

    public ArbitraryInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArbitraryInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);

        setOrientation(VERTICAL);
        setAddStatesFromChildren(true);

        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.ArbitraryInputLayout, defStyleAttr, R.style.Widget_Xpece_ArbitraryInputLayout);

        mHint = a.getText(R.styleable.ArbitraryInputLayout_android_hint);

        mHintTextAppearance = a.getResourceId(R.styleable.ArbitraryInputLayout_hintTextAppearance, 0);
        mErrorTextAppearance = a.getResourceId(R.styleable.ArbitraryInputLayout_errorTextAppearance, 0);

        if (a.hasValue(R.styleable.ArbitraryInputLayout_android_textColorHint)) {
            mHintTextColor = a.getColorStateList(R.styleable.ArbitraryInputLayout_android_textColorHint);
        }
        if (a.hasValue(R.styleable.ArbitraryInputLayout_android_textColorHint)) {
            mErrorTextColor = a.getColorStateList(R.styleable.ArbitraryInputLayout_textColorError);
        }

        mErrorEnabled = a.getBoolean(R.styleable.ArbitraryInputLayout_errorEnabled, false);
        mHintEnabled = a.getBoolean(R.styleable.ArbitraryInputLayout_hintEnabled, true);

        a.recycle();

        if (isInEditMode() && mErrorEnabled) {
            mError = "Error goes here.";
        }

        onSetHint();
        onSetError();
    }

    public void setHint(@StringRes int resId) {
        if (resId != 0) {
            setHint(getContext().getString(resId));
        } else {
            setHint(null);
        }
    }

    public void setHint(CharSequence hint) {
        mHint = hint;
        onSetHint();
    }

    private void onSetHint() {
        if (mHint == null) {
            if (mHintView != null) {
                if (mHintEnabled) {
                    mHintView.setText(null);
                } else {
                    mHintView.setVisibility(GONE);
                }
            }
        } else {
            ensureHintView();
            mHintView.setText(mHint);
            setHintEnabled(true);
        }
    }

    private void ensureHintView() {
        if (mHintView == null) {
            TextView hintView = new AppCompatTextView(getContext());
            if (mHintTextAppearance > 0) {
                TextViewCompat.setTextAppearance(hintView, mHintTextAppearance);
            }
            if (mHintTextColor != null) {
                hintView.setTextColor(mHintTextColor);
            }
            hintView.setVisibility(GONE);

            mHintView = hintView;

            final boolean wasInDrawableStateChanged = mInDrawableStateChanged;
            mInDrawableStateChanged = true;
            final int[] state = getDrawableState();
            updateLabelState(state);
            mInDrawableStateChanged = wasInDrawableStateChanged;

            addView(mHintView);
        }
    }

    public void setError(@StringRes int resId) {
        if (resId != 0) {
            setError(getContext().getString(resId));
        } else {
            setError(null);
        }
    }

    public void setError(CharSequence error) {
        mError = error;
        onSetError();
    }

    private void onSetError() {
        if (TextUtils.isEmpty(mError)) {
            if (mErrorView != null) {
                if (mErrorEnabled) {
                    mErrorView.setText(null);
                } else {
                    mErrorView.setVisibility(GONE);
                }
            }
        } else {
            ensureErrorView();
            mErrorView.setText(mError);
            setErrorEnabled(true);
        }
    }

    private void ensureErrorView() {
        if (mErrorView == null) {
            TextView errorView = new AppCompatTextView(getContext());
            if (mErrorTextAppearance > 0) {
                TextViewCompat.setTextAppearance(errorView, mErrorTextAppearance);
            }
            if (mErrorTextColor != null) {
                errorView.setTextColor(mErrorTextColor);
            }
            errorView.setVisibility(GONE);
            mErrorView = errorView;
            addView(mErrorView);
        }
    }

    public void setHintTextAppearance(@StyleRes int resId) {
        mHintTextAppearance = resId;

        if (resId > 0) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), resId, TEXT_COLOR_ATTR);
            if (a.hasValue(0)) {
                mHintTextColor = null;
            }
            a.recycle();
        }

        if (mHintView != null) {
            if (resId > 0) {
                TextViewCompat.setTextAppearance(mHintView, resId);
            }
            if (mHintTextColor != null) {
                mHintView.setTextColor(mHintTextColor);
            }
        }
    }

    public void setErrorTextAppearance(@StyleRes int resId) {
        mErrorTextAppearance = resId;

        if (resId > 0) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), resId, TEXT_COLOR_ATTR);
            if (a.hasValue(0)) {
                mErrorTextColor = null;
            }
            a.recycle();
        }

        if (mErrorView != null) {
            if (resId > 0) {
                TextViewCompat.setTextAppearance(mErrorView, resId);
            }
            if (mErrorTextColor != null) {
                mErrorView.setTextColor(mErrorTextColor);
            }
        }
    }

    public void setHintTextColor(@ColorInt int color) {
        ColorStateList csl = ColorStateList.valueOf(color);
        setHintTextColor(csl);
    }

    public void setHintTextColor(ColorStateList color) {
        mHintTextColor = color;

        if (mHintView != null) {
            TextViewCompat.setTextAppearance(mHintView, mHintTextAppearance);
            mHintView.setTextColor(color);
        }
    }

    public void setErrorTextColor(@ColorInt int color) {
        ColorStateList csl = ColorStateList.valueOf(color);
        setErrorTextColor(csl);
    }

    public void setErrorTextColor(ColorStateList color) {
        mErrorTextColor = color;

        if (mErrorView != null) {
            TextViewCompat.setTextAppearance(mErrorView, mErrorTextAppearance);
            mErrorView.setTextColor(color);
        }
    }

    public CharSequence getError() {
        return mError;
    }

    public CharSequence getHint() {
        return mHint;
    }

    public void hideHint() {
        if (mHintView != null) {
            mHintView.setVisibility(INVISIBLE);
        }
    }

    public void hideError() {
        if (mErrorView != null) {
            mErrorView.setVisibility(INVISIBLE);
        }
        mError = null;
    }

    public void showHint() {
        setHintEnabled(true);
    }

    public void setHintEnabled(boolean hintEnabled) {
        mHintEnabled = hintEnabled;
        if (hintEnabled) {
            if (mHintView == null) {
                ensureHintView();
            }
            mHintView.setVisibility(VISIBLE);
        } else {
            if (mHintView != null) {
                mHintView.setVisibility(GONE);
            }
        }
    }

    public boolean isHintEnabled() {
        return mHintEnabled;
    }

    public void setErrorEnabled(boolean errorEnabled) {
        mErrorEnabled = errorEnabled;
        if (errorEnabled) {
            if (mErrorView == null) {
                ensureErrorView();
            }
            mErrorView.setVisibility(VISIBLE);
        } else {
            if (mErrorView != null) {
                mErrorView.setVisibility(GONE);
            }
        }
        mError = null;
    }

    public boolean isErrorEnabled() {
        return mErrorEnabled;
    }

    public boolean isHintShown() {
        return mHintView != null && mHintView.getVisibility() == VISIBLE;
    }

    public boolean isErrorShown() {
        return mErrorView != null && mErrorView.getVisibility() == VISIBLE;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (mErrorView != null) {
            final int errorIndex = indexOfChild(mErrorView);
            if (errorIndex >= 0 && index < 0) {
                index = errorIndex;
            }
        }

        super.addView(child, index, params);
    }

    @Override
    protected void drawableStateChanged() {
        if (mInDrawableStateChanged) {
            // Some of the calls below will update the drawable state of child views. Since we're
            // using addStatesFromChildren we can get into infinite recursion, hence we'll just
            // exit in this instance
            return;
        }

        mInDrawableStateChanged = true;

        super.drawableStateChanged();

        final int[] state = getDrawableState();

        // Drawable state has changed so see if we need to update the label
        updateLabelState(state);

        invalidate();

        mInDrawableStateChanged = false;
    }

    private void updateLabelState(int[] state) {
        if (mHintView != null) {
            mHintView.setActivated(ArraysKt.contains(state, android.R.attr.state_focused));
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Since we're set to addStatesFromChildren, we need to make sure that we set all
        // children to enabled/disabled otherwise any enabled children will wipe out our disabled
        // drawable state
        recursiveSetEnabled(this, enabled);
        super.setEnabled(enabled);
    }

    private static void recursiveSetEnabled(final ViewGroup vg, final boolean enabled) {
        for (int i = 0, count = vg.getChildCount(); i < count; i++) {
            final View child = vg.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) child, enabled);
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        if (isErrorShown()) {
            ss.error = getError();
        }
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setError(ss.error);
        requestLayout();
    }

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(
            new ParcelableCompatCreatorCallbacks<SavedState>() {
                @Override
                public ArbitraryInputLayout.SavedState createFromParcel(Parcel in, ClassLoader loader) {
                    return new ArbitraryInputLayout.SavedState(in, loader);
                }

                @Override
                public ArbitraryInputLayout.SavedState[] newArray(int size) {
                    return new ArbitraryInputLayout.SavedState[size];
                }
            });

        CharSequence error;

        SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            error = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);

        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            TextUtils.writeToParcel(error, dest, flags);
        }

        @Override
        public String toString() {
            return "ArbitraryInputLayout.SavedState{"
                + Integer.toHexString(System.identityHashCode(this))
                + " error=" + error + "}";
        }
    }
}
