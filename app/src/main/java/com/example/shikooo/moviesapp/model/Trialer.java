package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shikooo on 11/6/2018.
 */

public class Trialer {

    @SerializedName("id")
    private Integer id;

    @SerializedName("results")
    private List<com.example.shikooo.moviesapp.model.Trailer.Results> results = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<com.example.shikooo.moviesapp.model.Trailer.Results> getResults() {
        return results;
    }

    public void setResults(List<com.example.shikooo.moviesapp.model.Trailer.Results> results) {
        this.results = results;
    }
}
