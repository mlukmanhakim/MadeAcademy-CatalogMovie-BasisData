package com.dicoding.lukman.catalogmovie.data.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dicoding.lukman.catalogmovie.BuildConfig;
import com.dicoding.lukman.catalogmovie.data.api.Repository;
import com.dicoding.lukman.catalogmovie.data.models.MovieResponse;
import com.dicoding.lukman.catalogmovie.data.models.Result;
import com.dicoding.lukman.catalogmovie.data.network.NetworkClient;
import com.dicoding.lukman.catalogmovie.ui.view.MovieView;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchMoviePresenter {

    private static final String TAG = "SearchMoviePresenter";
    private MovieView view;

    public SearchMoviePresenter(MovieView view){
        this.view = view;
    }
    public void getMovie(String film, String locale){
        if (locale.equals("us-US") ){
            locale = "en-US";
        }
        view.showLoading();
        Repository api = NetworkClient.getRetrofit().create(Repository.class);
        Call<MovieResponse> call = api.searchMovie(BuildConfig.MDB_API_KEY, locale , film);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
               MovieResponse movie = response.body();
                assert movie != null;
                List<Result> movieResult = movie.getResults();
                view.tampilMovie(movieResult);
                view.hideloading();
                view.hideError();
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                view.showError();
            }
        });


    }
}
