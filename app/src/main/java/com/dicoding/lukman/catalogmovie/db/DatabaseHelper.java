package com.dicoding.lukman.catalogmovie.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by #PemimpinMuda on 11/25/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfavoritemovie";
    public static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %S"
        + "(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "%s TEXT NOT NULL,"+
            "%s TEXT NOT NULL,"+
            "%s TEXT NOT NULL,"+
            "%s TEXT NOT NULL,"+
            "%s TEXT NOT NULL,"+
            "%s TEXT NOT NULL,"+
            "%s TEXT NOT NULL,"+
            "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE,
            DatabaseContract.FavoriteColumns._ID,
            DatabaseContract.FavoriteColumns.ID_MOVIE,
            DatabaseContract.FavoriteColumns.TITLE,
            DatabaseContract.FavoriteColumns.OVERVIEW,
            DatabaseContract.FavoriteColumns.DATE,
            DatabaseContract.FavoriteColumns.VOTE_COUNT,
            DatabaseContract.FavoriteColumns.POPULARITY,
            DatabaseContract.FavoriteColumns.VOTE_AVERAGE,
            DatabaseContract.FavoriteColumns.POSTER
    );
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ DatabaseContract.TABLE_FAVORITE);
        onCreate(db);
    }
}
