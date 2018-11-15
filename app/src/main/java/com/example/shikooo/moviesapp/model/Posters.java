package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shikooo on 11/4/2018.
 */

public class Posters {

    @SerializedName("file_path")
    private String file_path;

    public String getFile_path() {
        return "https://image.tmdb.org/t/p/w500" + file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
