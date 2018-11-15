package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shikooo on 11/5/2018.
 */

public class Cast {

    @SerializedName("cast_id")
    private int cast_id;

    @SerializedName("order")
    private int order;

    @SerializedName("gender")
    private int gender;

    @SerializedName("name")
    private String name;

    @SerializedName("character")
    private String character;

    @SerializedName("profile_path")
    private String profile_path;

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfile_path() {
        return "https://image.tmdb.org/t/p/w500" + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
