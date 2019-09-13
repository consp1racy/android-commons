package net.xpece.android.mail;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;


public class UriDataSource implements DataSource {
    private static final String[] PROJECTION_NAME = {OpenableColumns.DISPLAY_NAME};

    private final Context context;
    private final Uri uri;

    public UriDataSource(final Context context, final Uri uri) {
        this.context = context.getApplicationContext();
        this.uri = uri;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return context.getContentResolver().openInputStream(uri);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return context.getContentResolver().openOutputStream(uri);
    }

    @Override
    public String getContentType() {
        return context.getContentResolver().getType(uri);
    }

    @Override
    public String getName() {
        String name = null;
        Cursor c = null;
        try {
            c = context.getContentResolver().query(uri, PROJECTION_NAME, null, null, null);
            if (c != null && c.moveToFirst()) {
                name = c.getString(0);
            }
        } finally {
            if (c != null) c.close();
        }
        return name;
    }
}
