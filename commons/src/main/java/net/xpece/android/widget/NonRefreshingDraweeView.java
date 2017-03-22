package net.xpece.android.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.common.internal.Objects;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import javax.annotation.Nullable;

/**
 * @author Eugen on 19.12.2016.
 */

public class NonRefreshingDraweeView extends SimpleDraweeView {

    private Uri lastUri = null;
    private boolean hasLastUri = false;

    public NonRefreshingDraweeView(final Context context) {
        super(context);
    }

    public NonRefreshingDraweeView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public NonRefreshingDraweeView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageURI(final Uri uri, @Nullable final Object callerContext) {
        if (!hasLastUri || !Objects.equal(uri,lastUri)) {
            super.setImageURI(uri, callerContext);
            lastUri = uri;
            hasLastUri = true;
        }
    }

    @Override
    public void setController(@Nullable final DraweeController draweeController) {
        super.setController(draweeController);
        lastUri = null;
        hasLastUri = false;
    }
}
