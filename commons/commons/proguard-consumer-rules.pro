-dontwarn net.xpece.**

-keepclassmembernames class androidx.appcompat.widget.Toolbar {
    android.widget.TextView mTitleTextView;
    android.widget.TextView mSubtitleTextView;
    int mTitleTextAppearance;
    int mSubtitleTextAppearance;
    android.graphics.drawable.Drawable mCollapseIcon;
}

-keepclassmembernames class androidx.appcompat.view.ContextThemeWrapper {
    int mThemeResource;
}
