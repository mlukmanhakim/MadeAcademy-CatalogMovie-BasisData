package com.dicoding.lukman.catalogmovie.view;

import com.dicoding.lukman.catalogmovie.models.Result;

import java.util.List;

public interface MovieView {
    void hideError();
    void showLoading();
    void showError();
    void hideloading();
    void tampilMovie(List<Result> data);
}
