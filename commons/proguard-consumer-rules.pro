-dontwarn net.xpece.**

-keepclassmembernames class android.support.v7.widget.Toolbar {
    android.widget.TextView mTitleTextView;
    android.widget.TextView mSubtitleTextView;
    int mTitleTextAppearance;
    int mSubtitleTextAppearance;
    android.graphics.drawable.Drawable mCollapseIcon;
}
