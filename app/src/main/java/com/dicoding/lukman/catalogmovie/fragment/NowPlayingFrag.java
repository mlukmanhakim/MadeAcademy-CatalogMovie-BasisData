package com.dicoding.lukman.catalogmovie.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.adapter.MovieAdapter;
import com.dicoding.lukman.catalogmovie.models.Result;
import com.dicoding.lukman.catalogmovie.presenter.NowPlayingMoviePresenter;
import com.dicoding.lukman.catalogmovie.view.MovieView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFrag extends Fragment implements MovieView{

    NowPlayingMoviePresenter presenter;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_movie_frag)
    RecyclerView rvMovie;
    @BindView(R.id.progressbar)
    ProgressBar progressBar = null;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    ArrayList<Result> movieList  = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    String locale = Locale.getDefault().getCountry();
    String languageCode = locale.toLowerCase()+"-"+locale;



    public NowPlayingFrag() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new NowPlayingMoviePresenter(this);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MovieAdapter(movieList);
        rvMovie.setAdapter(mAdapter);
        Log.d("Locale", "Locale "+ languageCode);
        presenter.getMovie(languageCode);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMovie(languageCode);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

    @Override
    public void hideError() {
        tvMessage.setVisibility(View.GONE);

    }

    @Override
    public void showError() {
        tvMessage.setVisibility(View.VISIBLE);

    }
    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideloading() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void tampilMovie(List<Result> data) {
        if (data!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
        movieList.clear();
        movieList.addAll(data);
        mAdapter.notifyDataSetChanged();


    }
}
