package com.dicoding.lukman.favoritemovie.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.dicoding.lukman.favoritemovie.view.FavoriteView;

import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.CONTENT_URI;


public class FavoritePresenter {

    private Context context;

    private FavoriteView view;
    private static final String TAG = "FavoritePresenter";


    public FavoritePresenter(FavoriteView v, Context context){
        this.view = v;
        this.context = context;
    }

    public void getData(){
        new LoadFavoriteAsync().execute();
    }

       @SuppressLint("StaticFieldLeak")
       public class LoadFavoriteAsync extends AsyncTask<Void, Void, Cursor>{

           @Override
           protected void onPreExecute() {
               super.onPreExecute();
               view.showLoading();
           }

           @Override
            protected Cursor doInBackground(Void... voids) {

                return context.getContentResolver().query(
                        CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            }

           @Override
           protected void onPostExecute(Cursor cursor) {
               super.onPostExecute(cursor);
               view.hideloading();
               view.tampilMovie(cursor);
               Log.d(TAG, "onPostExecute: "+ cursor.getCount());
           }
       }
    }

