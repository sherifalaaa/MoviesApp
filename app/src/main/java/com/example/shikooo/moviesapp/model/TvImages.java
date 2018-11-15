package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shikooo on 11/7/2018.
 */

public class TvImages {

    @SerializedName("id")
    private int id;
    @SerializedName("backdrops")
    private List<Backdrops> backdrops = new ArrayList<Backdrops>();
    @SerializedName("posters")
    private List<Posters> posters = new ArrayList<Posters>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Backdrops> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrops> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Posters> getPosters() {
        return posters;
    }

    public void setPosters(List<Posters> posters) {
        this.posters = posters;
    }
}
