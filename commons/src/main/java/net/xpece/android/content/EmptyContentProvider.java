package net.xpece.android.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Base class for initialization providers.
 * Override {@link #onCreate()}, do your work, and return {@code false}.
 */
public abstract class EmptyContentProvider extends ContentProvider {

    @Nullable
    @Override
    public Cursor query(@NonNull final Uri uri, final String[] projection, final String selection,
            final String[] selectionArgs, final String sortOrder) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public Uri insert(@NonNull final Uri uri, final ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(@NonNull final Uri uri, final String selection,
            final String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(@NonNull final Uri uri, final ContentValues values, final String selection,
            final String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
