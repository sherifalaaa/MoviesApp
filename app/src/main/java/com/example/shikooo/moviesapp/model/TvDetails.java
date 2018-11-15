package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shikooo on 11/8/2018.
 */

public class TvDetails {

    @SerializedName("seasons")
    private List<Seasons> seasons = new ArrayList<Seasons>();

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
    }
}
