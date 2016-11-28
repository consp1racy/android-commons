package net.xpece.commons.sample;

import net.xpece.android.content.EmptyContentProvider;
import net.xpece.commons.android.sample.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * @author Eugen on 10.09.2016.
 */

public class MyInitProvider extends EmptyContentProvider {
    @Override
    public boolean onCreate() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setFontAttrId(R.attr.fontPath)
            .build()
        );
        return false;
    }
}
