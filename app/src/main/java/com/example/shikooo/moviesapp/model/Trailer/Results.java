package com.example.shikooo.moviesapp.model.Trailer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shikooo on 11/6/2018.
 */

public class Results {

    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
