package com.dicoding.lukman.catalogmovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.ProxyInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dicoding.lukman.catalogmovie.db.DatabaseContract;
import com.dicoding.lukman.catalogmovie.db.FavoriteHelper;

import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.AUTHORITY;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.CONTENT_URI;

/**
 * Created by #PemimpinMuda on 11/25/2018.
 */

public class FavoriteProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITE+ "/#", MOVIE_ID);
    }

    public FavoriteHelper favoriteHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = new FavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,  String[] strings,  String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = favoriteHelper.queryPrivider();
                break;
            case MOVIE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor !=null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);

        }
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
        long addFavorite;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                addFavorite = favoriteHelper.insertProvider(values);
                break;
            default:
                addFavorite =  0;
                break;
        }
        if (addFavorite>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI +"/"+addFavorite);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int removed;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                removed = favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                removed = 0;
                break;
        }

        if (removed>0){
            getContext().getContentResolver().notifyChange(uri, null);

        }

        return removed;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                updated = favoriteHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated>0){
            getContext().getContentResolver().notifyChange(uri, null);

        }
        return updated;
    }
}
