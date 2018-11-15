package com.example.shikooo.moviesapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Created by shikooo on 11/8/2018.
 */


@Entity(tableName = "watchlist",primaryKeys = {"id"})
public class WatchList {

    @ColumnInfo(name = "originalTitle")
    @NonNull
    private String originalTitle;

    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @ColumnInfo(name = "backdrop_path")
    @NonNull
    private String backdropPath;

    @ColumnInfo(name = "overview")
    @NonNull
    private String overview;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "posterPath")
    private String posterPath;

    @ColumnInfo(name = "id")
    @NonNull
    private Integer id;

    @ColumnInfo(name = "saveTime")
    private String saveTime;

    public WatchList(@NonNull String originalTitle, @NonNull String originalLanguage, @NonNull String backdropPath, @NonNull String overview, @NonNull String releaseDate, @NonNull Double voteAverage, @NonNull String posterPath, @NonNull Integer id, String saveTime) {
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.id = id;
        this.saveTime = saveTime;
    }

    public WatchList() {
    }

    @NonNull
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(@NonNull String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @NonNull
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(@NonNull String posterPath) {
        this.posterPath = posterPath;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    @NonNull
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(@NonNull String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @NonNull
    public String getOverview() {
        return overview;
    }

    public void setOverview(@NonNull String overview) {
        this.overview = overview;
    }

    @NonNull
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(@NonNull String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NonNull
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(@NonNull String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @NonNull
    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(@NonNull Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
