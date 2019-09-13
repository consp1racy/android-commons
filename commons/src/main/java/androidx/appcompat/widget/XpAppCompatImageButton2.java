package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import net.xpece.android.R;

/**
 * @author Eugen on 05.08.2016.
 */

public class XpAppCompatImageButton2 extends XpAppCompatImageButton {
    public XpAppCompatImageButton2(final Context context) {
        this(context, null);
    }

    public XpAppCompatImageButton2(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, R.attr.imageButtonStyle);
    }

    public XpAppCompatImageButton2(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(XpTintContextWrapper.wrap(context), attrs, defStyleAttr);
    }
}
