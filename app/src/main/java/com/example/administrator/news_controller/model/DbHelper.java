package com.example.administrator.news_controller.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.news_controller.model.DbContract.CategoryEntries;
import com.example.administrator.news_controller.model.DbContract.NewsEntries;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";
    private static final String CREATE_CATEGORY_ENTRIES = "CREATE TABLE " + CategoryEntries.TABLE_NAME + " ("
            + CategoryEntries.CATEGORY_ID + " INTEGER PRIMARY KEY, "
            + CategoryEntries.CATEGORY_NAME + " TEXT);";

    private static final String CREATE_NEWS_ENTRIES = "CREATE TABLE " + NewsEntries.TABLE_NAME + " ("
            + NewsEntries.NEWS_ID + " INTEGER PRIMARY KEY, "
            + NewsEntries.NEWS_TITLE + " TEXT, "
            + NewsEntries.NEWS_DATE + " TEXT, "
            + NewsEntries.NEWS_DESCRIPTION + " TEXT, "
            + NewsEntries.FULL_DESCRIPTION + " TEXT, "
            + NewsEntries.CATEGORY_ID + " INTEGER);";

    private static final String SQL_DELETE_CATEGORIES = "DROP TABLE IF EXISTS " + CategoryEntries.TABLE_NAME;
    private static final String SQL_DELETE_NEWS = "DROP TABLE IF EXISTS " + NewsEntries.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CATEGORY_ENTRIES);
        sqLiteDatabase.execSQL(CREATE_NEWS_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_CATEGORIES);
        sqLiteDatabase.execSQL(SQL_DELETE_NEWS);
        onCreate(sqLiteDatabase);
    }
}
