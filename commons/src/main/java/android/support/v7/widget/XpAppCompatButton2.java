package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import net.xpece.android.R;


/**
 * @author Eugen on 05.08.2016.
 */

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
