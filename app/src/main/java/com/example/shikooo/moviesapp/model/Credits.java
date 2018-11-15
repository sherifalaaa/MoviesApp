package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shikooo on 11/5/2018.
 */

public class Credits {

    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private List<Cast> cast = new ArrayList<Cast>();
    @SerializedName("crew")
    private List<Crew> crew = new ArrayList<Crew>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
