package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import net.xpece.android.R;



public class XpAppCompatButton2 extends XpAppCompatButton {
    public XpAppCompatButton2(final Context context) {
        this(context, null);
    }

    public XpAppCompatButton2(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public XpAppCompatButton2(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(XpTintContextWrapper.wrap(context), attrs, defStyleAttr);
    }
}
