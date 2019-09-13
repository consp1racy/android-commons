package net.xpece.android.widget;

import android.content.Context;
import android.graphics.drawable.Animatable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * @author Eugen on 15.03.2017.
 */

public class WrapContentNonRefreshingDraweeView extends NonRefreshingDraweeView {

    private ControllerListener listener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            onUpdateViewSize(imageInfo);
        }

        @Override
        public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
            onUpdateViewSize(imageInfo);
        }
    };

    public WrapContentNonRefreshingDraweeView(final Context context) {
        super(context);
        init(context, null, 0);
    }

    public WrapContentNonRefreshingDraweeView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public WrapContentNonRefreshingDraweeView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(final Context context, final AttributeSet attrs, final int defStyle) {
        final DraweeController controller = ((PipelineDraweeControllerBuilder) getControllerBuilder())
            .setControllerListener(listener)
            .setOldController(getController())
            .build();
        setController(controller);
    }

    public void onUpdateViewSize(@Nullable ImageInfo imageInfo) {
        if (imageInfo != null) {
            setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
        }
    }
}
