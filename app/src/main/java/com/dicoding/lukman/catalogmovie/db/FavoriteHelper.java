package com.dicoding.lukman.catalogmovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.TABLE_FAVORITE;

/**
 * Created by #PemimpinMuda on 11/25/2018.
 */

public class FavoriteHelper  {

        private static String DATABASE_TABLE = TABLE_FAVORITE;
        private Context context;
        private DatabaseHelper databaseHelper;
        private SQLiteDatabase database;

        public FavoriteHelper(Context context){
            this.context = context;
        }

        public FavoriteHelper open() throws SQLException{
            databaseHelper = new DatabaseHelper(context);
            database = databaseHelper.getWritableDatabase();
            return this;
        }

        public void close(){
            databaseHelper.close();
        }

        public Cursor   queryByIdProvider(String id){
            return database.query(DATABASE_TABLE, null
            ,_ID+ " = ?"
            ,new String[]{id}
            ,null
            ,null
            ,null
            ,null
            );
        }

        public Cursor queryPrivider(){
            return database.query(DATABASE_TABLE
            ,null
                    ,null
                    ,null
                    ,null
                    ,null
                    , BaseColumns._ID+ " DESC"
            );

        }

        public long insertProvider(ContentValues contentValues){
            return database.insert(DATABASE_TABLE, null, contentValues);
        }

        public int updateProvider(String id, ContentValues contentValues){
            return database.update(DATABASE_TABLE,contentValues, _ID+ " = ?", new String[]{id});
        }

        public int deleteProvider(String id){
            return database.delete(DATABASE_TABLE,_ID+" = ?", new String[]{id});
        }
}
