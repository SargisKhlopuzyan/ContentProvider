package com.example.sargiskh.contentproviderapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sargiskh on 4/25/2017.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String COMPANY_DATABASE_NAME = "CompanyDb.db";
    public static final int COMPANY_DATABASE_VERSION = 1;
    //////////////////////////////////////////////////////
    public static final String WORKERS_TABLE_NAME = "workers";
    public static final String STUFF_TABLE_NAME = "stuff";
    //////////////////////////////////////////////////////
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String WORK_EXPERIENCE = "work_experience";

    public MySQLiteOpenHelper(Context context) {
        super(context, COMPANY_DATABASE_NAME, null, COMPANY_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_STUFF_TABLE = "CREATE TABLE " + STUFF_TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT NOT NULL,"
                + AGE + " INTEGER NOT NULL);";

        final String CREATE_WORKERS_TABLE = "CREATE TABLE " + WORKERS_TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT NOT NULL,"
                + AGE + " INTEGER NOT NULL,"
                + WORK_EXPERIENCE  + " INTEGER NOT NULL);";

        db.execSQL(CREATE_STUFF_TABLE);
        db.execSQL(CREATE_WORKERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + STUFF_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + WORKERS_TABLE_NAME);
        onCreate(db);
    }

}
