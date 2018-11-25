package com.dicoding.lukman.favoritemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;



public class DatabaseContract {

    static String TABLE_FAVORITE = "favorite";
    public static final class FavoriteColumns implements BaseColumns{
        public static String ID_MOVIE = "idmovie";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String DATE = "date";
        public static String VOTE_COUNT = "vote_count";
        public static String POPULARITY = "poularity";
        public static String VOTE_AVERAGE = "vote_average";
        public static String POSTER = "poster";

    }

    private static final String AUTHORITY = "com.dicoding.lukman.catalogmovie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static float getColumnFloat(Cursor cursor, String columnName){
        return cursor.getFloat(cursor.getColumnIndex(columnName));
    }

}
