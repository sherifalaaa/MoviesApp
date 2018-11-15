package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shikooo on 11/8/2018.
 */

public class Seasons {


    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("air_date")
    private String airDate;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private Integer id;

    @SerializedName("season_number")
    private Integer seasonNumber;

    @SerializedName("episode_count")
    private Integer episode_count;

    public String getPosterPath() {
        return  "https://image.tmdb.org/t/p/w500" +  posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(Integer episode_count) {
        this.episode_count = episode_count;
    }
}

