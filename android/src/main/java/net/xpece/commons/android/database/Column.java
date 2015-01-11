package net.xpece.commons.android.database;

import android.database.Cursor;

/**
 * Created by pechanecjr on 29. 11. 2014.
 */
public class Column {
  final int mIndex;
  final Cursor mCursor;

  public Column(Cursor cursor, String name) {
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
