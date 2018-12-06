package com.dicoding.lukman.catalogmovie.ui.view;

import android.database.Cursor;

/**
 * Created by #PemimpinMuda on 11/25/2018.
 */

public interface FavoriteView {

    void showLoading();
    void hideloading();
    void tampilMovie(Cursor data);

}
