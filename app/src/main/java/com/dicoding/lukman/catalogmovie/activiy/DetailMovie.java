package com.dicoding.lukman.catalogmovie.activiy;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.db.FavoriteHelper;
import com.dicoding.lukman.catalogmovie.models.Result;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.CONTENT_URI;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.FavoriteColumns.DATE;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.FavoriteColumns.ID_MOVIE;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.FavoriteColumns.POPULARITY;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;
import static com.dicoding.lukman.catalogmovie.db.DatabaseContract.FavoriteColumns.VOTE_COUNT;

public class DetailMovie extends AppCompatActivity {

    private static final String TAG = "DetailMovie";

    public static final String EXTRA_RESULT = "EXTRA_RESULT";

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
    Result mResult;

    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
         mResult = getIntent().getParcelableExtra(EXTRA_RESULT);
        String voteCount = mResult.getVoteCount().toString();
        String popularity = mResult.getPopularity().toString();
        String voteAverage = mResult.getVoteAverage().toString();

        Glide.with(this).load("http://image.tmdb.org/t/p/w185"+mResult.getPosterPath()).into(ivPoster);
        tvVoteCount.setText(voteCount);
        tvPopularity.setText(popularity);
        tvVoteAverage.setText(voteAverage);
        tvSinopsis.setText(mResult.getOverview());
        tvReleaseDate.setText(mResult.getReleaseDate());
        System.out.println(mResult.getReleaseDate());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mResult.getTitle());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loadDataSQL();
//        setFavorite();

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite)saveFavorite();
                else removeFavorite();

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
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + mResult.getId()),
                null
                ,null
                ,null,
                null

        );

//        Cursor cursor = getContentResolver().
        Log.d(TAG, "loadDataSQL: "+cursor);
        if (cursor != null){
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        setFavorite();
    }

    private void saveFavorite(){
        ContentValues value = new ContentValues();
        value.put(_ID, mResult.getId());
        value.put(ID_MOVIE, mResult.getId());
        value.put(TITLE, mResult.getTitle());
        value.put(OVERVIEW, mResult.getOverview());
        value.put(DATE, mResult.getReleaseDate());
        value.put(VOTE_COUNT, mResult.getVoteCount());
        value.put(POPULARITY, mResult.getPopularity());
        value.put(VOTE_AVERAGE, mResult.getVoteAverage());
        value.put(POSTER, mResult.getPosterPath());

        getContentResolver().insert(CONTENT_URI, value);
        showMessage("Add to favorite");
    }

    private void removeFavorite(){
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + mResult.getId()),
                null,
                null
        );

        showMessage("Remove from favorite");

    }

    private void showMessage( String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}
