package android.support.v7.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author Eugen on 05.08.2016.
 */

public class XpAppCompatButton2 extends XpAppCompatButton {
    public XpAppCompatButton2(final Context context) {
        this(context, null);
    }

    public XpAppCompatButton2(final Context context, final AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public XpAppCompatButton2(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(XpTintContextWrapper.wrapIfNeeded(context), attrs, defStyleAttr);
    }
}
