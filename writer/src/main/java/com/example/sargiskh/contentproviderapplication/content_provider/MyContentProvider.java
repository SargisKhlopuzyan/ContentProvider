package com.example.sargiskh.contentproviderapplication.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.sargiskh.contentproviderapplication.database.MySQLiteOpenHelper;

/**
 * Created by sargiskh on 4/25/2017.
 */

public class MyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "company.content.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_STUFF = "stuff";
    public static final String PATH_WORKERS = "workers";

    public static final String STUFF_TABLE_NAME = "stuff";
    public static final String WORKERS_TABLE_NAME = "workers";

    // TaskEntry content URI = base content URI + path
    public static final Uri CONTENT_URI_STUFF = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUFF).build();
    public static final Uri CONTENT_URI_WORKERS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKERS).build();

    private static final int KEY_STUFF = 100;
    private static final int KEY_STUFF_WITH_ID = 101;
    private static final int KEY_WORKERS = 200;
    private static final int KEY_WORKERS_WITH_ID = 201;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();
    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, PATH_STUFF, KEY_STUFF);
        uriMatcher.addURI(AUTHORITY, PATH_STUFF + "/#", KEY_STUFF_WITH_ID);

        uriMatcher.addURI(AUTHORITY, PATH_WORKERS, KEY_WORKERS);
        uriMatcher.addURI(AUTHORITY, PATH_WORKERS + "/#", KEY_WORKERS_WITH_ID);
        return uriMatcher;
    }

    MySQLiteOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new MySQLiteOpenHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = URI_MATCHER.match(uri);

        Cursor cursor;
        switch (match) {
            case KEY_STUFF:
                cursor = db.query(STUFF_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case KEY_WORKERS:
                cursor = db.query(WORKERS_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri (getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Uri returnUri;

        int match = URI_MATCHER.match(uri);
        long id;
        switch (match) {
            case KEY_STUFF:
                id = db.insert(STUFF_TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = BASE_CONTENT_URI.buildUpon().appendPath(STUFF_TABLE_NAME).appendPath("" + id).build();
                    //Both work fine !!!
//                    getContext().getContentResolver().notifyChange(returnUri, null);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return returnUri;
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
//                break;
            case KEY_WORKERS:
                id = db.insert(WORKERS_TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = BASE_CONTENT_URI.buildUpon().appendPath(WORKERS_TABLE_NAME).appendPath("" + id).build();
                    //Both work fine !!!
//                    getContext().getContentResolver().notifyChange(returnUri, null);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return returnUri;
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
//                break;

            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
