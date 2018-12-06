package com.dicoding.lukman.favoritemovie.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.dicoding.lukman.favoritemovie.BuildConfig;
import com.dicoding.lukman.favoritemovie.R;
import com.dicoding.lukman.favoritemovie.model.FavoriteModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailMovie extends AppCompatActivity {

    private static final String TAG = "DetailMovie";
    private Uri uri;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.tv_vote_count)
    TextView tvVoteCount;
    @BindView(R.id.tv_popularity)
    TextView tvPopularity;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.tv_sinopsis)
    TextView tvSinopsis;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.btn_favorite)
    Button btnFavorite;

    private Boolean isFavorite = false;
    FavoriteModel mResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        uri = getIntent().getData();
        loadDataSQL();
        String voteCount = mResult.getVoteCount().toString();
        String popularity = mResult.getPopularity().toString();
        String voteAverage = mResult.getVoteAverage().toString();

        Glide.with(this).load(BuildConfig.IMAGE_URL+mResult.getPosterPath()).into(ivPoster);
        tvVoteCount.setText(voteCount);
        tvPopularity.setText(popularity);
        tvVoteAverage.setText(voteAverage);
        tvSinopsis.setText(mResult.getOverview());
        tvReleaseDate.setText(mResult.getReleaseDate());
        System.out.println(mResult.getReleaseDate());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mResult.getTitle());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setFavorite();

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite)removeFavorite();
                isFavorite = !isFavorite;
                setFavorite();
            }
        });
    }

    private void setFavorite(){
            if (!isFavorite){
                btnFavorite.setBackgroundColor(getColor(R.color.colorAccent));
                btnFavorite.setText(getString(R.string.ad_to_favorite));
            }else{
                btnFavorite.setBackgroundColor(getColor(R.color.red));
                btnFavorite.setText(getString(R.string.remove_to_favorite));
            }

    }

    private void loadDataSQL(){
        if (uri == null)return;

        Cursor cursor = getContentResolver().query(uri,
                null
                ,null
                ,null,
                null

        );
        Log.d(TAG, "loadDataSQL: "+cursor);
        if (cursor != null){
            if (cursor.moveToFirst()){
                mResult = new FavoriteModel(cursor);
                isFavorite = true;
                cursor.close();
            }
        }
        setFavorite();
    }

    private void removeFavorite(){
        if (uri==null)return;
        getContentResolver().delete(
               uri,
                null,
                null
        );

        showMessage();

    }

    private void showMessage(){
        Toast.makeText(this, "Remove from favorite", Toast.LENGTH_SHORT).show();

    }
}
