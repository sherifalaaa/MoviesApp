package com.example.shikooo.moviesapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shikooo on 4/30/2018.
 */

public class Client {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "2a012958dc19d5c620aec582aa127912";
    public static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

}
