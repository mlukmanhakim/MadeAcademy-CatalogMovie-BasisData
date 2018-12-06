package com.dicoding.lukman.catalogmovie.data.api;

import com.dicoding.lukman.catalogmovie.data.models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface  Repository {

    @GET("search/movie?")
    Call<MovieResponse> searchMovie(@Query("api_key") String api,
                                    @Query("language") String language,
                                    @Query("query") String nama_movie);
    @GET("movie/now_playing?")
    Call<MovieResponse> getNowPlayingMovie(@Query("api_key") String api,
                                    @Query("language") String language);
    @GET("movie/upcoming?")
    Call<MovieResponse> getUpCommingMovie(@Query("api_key") String api,
                                    @Query("language") String language);



}
