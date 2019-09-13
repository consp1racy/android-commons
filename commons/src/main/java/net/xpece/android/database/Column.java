package net.xpece.android.database;

import android.database.Cursor;

public class Column {
    final int mIndex;
    final Cursor mCursor;

    private Column(Cursor cursor, String name) {
        mIndex = cursor.getColumnIndex(name);
        mCursor = cursor;
    }

    public String getString() {
        return mCursor.getString(mIndex);
    }

    public short getShort() {
        return mCursor.getShort(mIndex);
    }

    public int getInt() {
        return mCursor.getInt(mIndex);
    }

    public long getLong() {
        return mCursor.getLong(mIndex);
    }

    public float getFloat() {
        return mCursor.getFloat(mIndex);
    }

    public double getDouble() {
        return mCursor.getDouble(mIndex);
    }

    public byte[] getBlob() {
        return mCursor.getBlob(mIndex);
    }
}
