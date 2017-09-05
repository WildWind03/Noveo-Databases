package com.noveo.internship.databaseexample.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

public class ExampleContentProvider extends ContentProvider {

    private static final String LOG_TAG = ExampleContentProvider.class.getSimpleName();
    private ExampleOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = ExampleOpenHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        int uriType = ContentDescriptor.uriMatcher.match(uri);
        String tableName = ContentDescriptor.getTableName(uriType);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(tableName, projection, selection, selectionArgs, null, null, orderBy);

        Context context = getContext();

        if (null != context) {
            ContentResolver contentResolver = context.getContentResolver();
            cursor.setNotificationUri(contentResolver, uri);
        }

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        int uriType = ContentDescriptor.uriMatcher.match(uri);
        String tableName = ContentDescriptor.getTableName(uriType);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insertWithOnConflict(tableName, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        Context context = getContext();

        if (null != context) {
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.notifyChange(uri, null);
        }


        if (TextUtils.equals(tableName, ContentDescriptor.Toys.TABLE_NAME)) {
            return Uri.parse(ContentDescriptor.Toys.TABLE_URI + "/" + id);
        } else {
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int uriType = ContentDescriptor.uriMatcher.match(uri);
        String tableName = ContentDescriptor.getTableName(uriType);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(tableName, selection, selectionArgs);

        Context context = getContext();

        if (null != context) {
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.notifyChange(uri, null);
        }

        return result;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int uriType = ContentDescriptor.uriMatcher.match(uri);
        String tableName = ContentDescriptor.getTableName(uriType);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result;
        try {
            result = db.update(tableName, contentValues, selection, selectionArgs);
        } catch (Throwable e) {
            Log.e(LOG_TAG, "Unable to update SQLiteDatabase");
            result = 0;
        }

        return result;
    }

}
