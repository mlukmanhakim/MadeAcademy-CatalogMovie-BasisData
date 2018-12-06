package com.dicoding.lukman.catalogmovie.ui.activiy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.ui.adapter.MovieAdapter;
import com.dicoding.lukman.catalogmovie.data.models.Result;
import com.dicoding.lukman.catalogmovie.data.presenter.SearchMoviePresenter;
import com.dicoding.lukman.catalogmovie.ui.view.MovieView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivitySearch extends AppCompatActivity implements MovieView {

   
    private static final String TAG = "ActivitySearch";

    @BindView(R.id.rv_movie) RecyclerView listMovie;
    @BindView(R.id.progressbar) ProgressBar progressBar = null;
    @BindView(R.id.tv_message) TextView tvMessage;
    Toolbar toolbar;
    SearchView searchView;
    MenuItem searchMenuItem;

    String locale = Locale.getDefault().getCountry();
    String languageCode;
    {
        languageCode = locale.toLowerCase() + "-" + locale;
    }

    //    @BindView(R.id.search) SearchView svMovie;
    SearchMoviePresenter presenter;
    RecyclerView.Adapter mAdapter;

    ArrayList<Result> movieList  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        presenter = new SearchMoviePresenter(this);
        ButterKnife.bind(this);
        listMovie.setHasFixedSize(true);
        listMovie.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MovieAdapter(movieList);
        listMovie.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        searchMenuItem = menu.findItem(R.id.search_id);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchMenuItem.expandActionView();
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return false;
            }

        });
        searchMovie();

        return true;
    }

        private void searchMovie() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: "+query);
                if (searchView.getQuery().length() != 0){
                    presenter.getMovie(query, languageCode);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if (searchView.getQuery().length() != 0){
                    presenter.getMovie(newText, languageCode);
                }
                return false;
            }
        });

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        tvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        tvMessage.setVisibility(View.GONE);
    }

    @Override
    public void hideloading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void tampilMovie(List<Result> data) {
        movieList.clear();
        movieList.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

}
