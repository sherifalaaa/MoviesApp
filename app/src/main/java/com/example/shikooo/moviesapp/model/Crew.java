package com.example.shikooo.moviesapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shikooo on 11/5/2018.
 */

public class Crew {

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profile_path;

    @SerializedName("department")
    private String department;

    public String getName() {
        return  name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return "https://image.tmdb.org/t/p/w500" + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
