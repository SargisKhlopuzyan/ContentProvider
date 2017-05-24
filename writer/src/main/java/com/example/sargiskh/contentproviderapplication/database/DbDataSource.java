package com.example.sargiskh.contentproviderapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sargiskh on 5/4/2017.
 */

public class DbDataSource {

    private SQLiteDatabase database;
    private MySQLiteOpenHelper dbHelper;

    private String[] allColumnsStuff = {MySQLiteOpenHelper.ID, MySQLiteOpenHelper.NAME, MySQLiteOpenHelper.AGE};
    private String[] allColumnsWorkers = {MySQLiteOpenHelper.ID, MySQLiteOpenHelper.NAME, MySQLiteOpenHelper.AGE, MySQLiteOpenHelper.WORK_EXPERIENCE};

    public DbDataSource(Context context) {
        dbHelper = new MySQLiteOpenHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    //

    public long addStuff(String name, int age) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.NAME, name);
        contentValues.put(MySQLiteOpenHelper.AGE, age);

        long id = database.insert(MySQLiteOpenHelper.STUFF_TABLE_NAME, null, contentValues);
        return id;
    }

    public int deleteStuff(long id) {
        int removedCount = database.delete(MySQLiteOpenHelper.STUFF_TABLE_NAME, MySQLiteOpenHelper.ID + " = " + id, null);
        return removedCount;
    }

    public Cursor getAllStuff() {
        Cursor cursor = database.query(MySQLiteOpenHelper.STUFF_TABLE_NAME, allColumnsStuff, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    //

    public long addWorker(String name, int age, int workExperience) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.NAME, name);
        contentValues.put(MySQLiteOpenHelper.AGE, age);
        contentValues.put(MySQLiteOpenHelper.WORK_EXPERIENCE, age);

        long id = database.insert(MySQLiteOpenHelper.WORKERS_TABLE_NAME, null, contentValues);
        return id;
    }

    public int deleteWorker(long id) {
        int removedCount = database.delete(MySQLiteOpenHelper.WORKERS_TABLE_NAME, MySQLiteOpenHelper.ID + " = " + id, null);
        return removedCount;
    }

    public Cursor getAllWorker() {
        Cursor cursor = database.query(MySQLiteOpenHelper.WORKERS_TABLE_NAME, allColumnsWorkers, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}
