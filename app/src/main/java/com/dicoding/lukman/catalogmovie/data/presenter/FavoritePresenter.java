package com.dicoding.lukman.catalogmovie.data.presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.dicoding.lukman.catalogmovie.ui.view.FavoriteView;

import static com.dicoding.lukman.catalogmovie.data.db.DatabaseContract.CONTENT_URI;

/**
 * Created by #PemimpinMuda on 11/25/2018.
 */

public class FavoritePresenter {

    private Cursor list;
    private Context context;

    private FavoriteView view;
    private static final String TAG = "FavoritePresenter";

    public Context getContext() {
        return this.context;
    }

    public FavoritePresenter(FavoriteView v, Context context){
        this.view = v;
        this.context = context;
    }

    public void getData(){
        new LoadFavoriteAsync().execute();
    }

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
               list = cursor;
               view.hideloading();
               view.tampilMovie(list);
               Log.d(TAG, "onPostExecute: "+list.getCount());
           }
       }
    }

