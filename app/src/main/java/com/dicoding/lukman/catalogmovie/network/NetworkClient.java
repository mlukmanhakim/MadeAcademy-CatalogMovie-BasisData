package com.dicoding.lukman.catalogmovie.network;

import com.dicoding.lukman.catalogmovie.BuildConfig;
import com.dicoding.lukman.catalogmovie.api.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by #PemimpinMuda on 11/4/2018.
 */

public class NetworkClient {

    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
