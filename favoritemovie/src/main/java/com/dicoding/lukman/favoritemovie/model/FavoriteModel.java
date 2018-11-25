package com.dicoding.lukman.favoritemovie.model;



import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.FavoriteColumns.DATE;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.FavoriteColumns.ID_MOVIE;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.FavoriteColumns.POPULARITY;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.FavoriteColumns.VOTE_COUNT;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.getColumnFloat;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.getColumnInt;
import static com.dicoding.lukman.favoritemovie.db.DatabaseContract.getColumnString;

public class FavoriteModel  {

    private int idMovie;
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private Float voteAverage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_count")
    private Integer voteCount;

    @SerializedName("popularity")
    private Float popularity;

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public FavoriteModel(){}

    public FavoriteModel(Cursor cursor){
        this.idMovie = getColumnInt(cursor, _ID);
        this.id = getColumnInt(cursor, ID_MOVIE);
        this.title = getColumnString(cursor, TITLE);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.releaseDate = getColumnString(cursor, DATE);
        this.voteCount = getColumnInt(cursor, VOTE_COUNT);
        this.voteAverage = getColumnFloat(cursor, VOTE_AVERAGE);
        this.popularity = getColumnFloat(cursor, POPULARITY);
        this.posterPath = getColumnString(cursor, POSTER);
    }



}


