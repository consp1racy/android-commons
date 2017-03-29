-dontwarn net.xpece.**

-keepclassmembernames class android.support.v7.widget.Toolbar {
    android.widget.TextView mTitleTextView;
    android.widget.TextView mSubtitleTextView;
    int mTitleTextAppearance;
    int mSubtitleTextAppearance;
    android.graphics.drawable.Drawable mCollapseIcon;
}

-dontwarn com.birbit.android.jobqueue.scheduling.**
-dontwarn com.squareup.picasso.ExifContentStreamRequestHandler
-dontwarn com.squareup.picasso.PaletteTransformation
-dontwarn com.squareup.picasso.RotateTransformation

-dontwarn org.sufficientlysecure.htmltextview.AppCompatHtmlTextView
-dontwarn org.sufficientlysecure.htmltextview.AppCompatJellyBeanSpanFixHtmlTextView
