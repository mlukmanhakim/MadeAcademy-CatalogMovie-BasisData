package com.dicoding.lukman.catalogmovie.fragment;

import android.database.Cursor;
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
import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.adapter.FavoriteAdapter;
import com.dicoding.lukman.catalogmovie.presenter.FavoritePresenter;
import com.dicoding.lukman.catalogmovie.view.FavoriteView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoriteFrag extends Fragment  implements FavoriteView{

    FavoritePresenter presenter;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_movie_frag)
    RecyclerView rvMovie;
    @BindView(R.id.progressbar)
    ProgressBar progressBar = null;
    private Cursor list;
    private FavoriteAdapter adapter;
    private static final String TAG = "FavoriteFrag";

    public FavoriteFrag(){}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new FavoritePresenter(this, getContext());
        setUpItems();
        presenter.getData();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

    private void setUpItems() {
        adapter = new FavoriteAdapter(list);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(adapter);
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
    public void tampilMovie(Cursor data) {
        if (data != null){
            swipeRefreshLayout.setRefreshing(false);
        }
        list = data;
        adapter.replaceAll(list);
        assert data != null;
            Log.d(TAG, "tampilMovie: " +data.getCount());
    }
}
