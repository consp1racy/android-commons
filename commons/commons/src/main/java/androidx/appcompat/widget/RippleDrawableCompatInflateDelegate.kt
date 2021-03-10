package androidx.appcompat.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import net.xpece.android.graphics.drawable.RippleDrawableCompat
import org.xmlpull.v1.XmlPullParser

// The InflateDelegate interface is private but the subclasses are not, so extend one of them.
@RequiresApi(21)
internal object RippleDrawableCompatInflateDelegate :
    ResourceManagerInternal.AsldcInflateDelegate() {

    override fun createFromXmlInner(
        context: Context,
        parser: XmlPullParser,
        attrs: AttributeSet,
        theme: Resources.Theme?
    ): Drawable {
        return RippleDrawableCompat()
            .apply { inflate(context.resources, parser, attrs, theme) }
    }
}