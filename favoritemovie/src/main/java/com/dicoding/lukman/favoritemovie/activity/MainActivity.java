package com.dicoding.lukman.favoritemovie.activity;

import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dicoding.lukman.favoritemovie.R;
import com.dicoding.lukman.favoritemovie.adapter.FavoriteAdapter;
import com.dicoding.lukman.favoritemovie.presenter.FavoritePresenter;
import com.dicoding.lukman.favoritemovie.view.FavoriteView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FavoriteView{


    FavoritePresenter presenter;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_movie_frag)
    RecyclerView rvMovie;
    @BindView(R.id.progressbar)
    ProgressBar progressBar = null;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    private Cursor list;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new FavoritePresenter(this, getApplicationContext());
        setUpItem();
        presenter.getData();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData();
            }
        });


    }

    private void setUpItem() {
        adapter = new FavoriteAdapter(list);
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
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
        swipeRefreshLayout.setRefreshing(false);
        if (data.getCount()==0){
            tvMessage.setVisibility(View.VISIBLE);
        }else {
            list = data;
            adapter.replaceAll(list);
            tvMessage.setVisibility(View.GONE);
        }

    }
}
