package com.example.shikooo.moviesapp.Interface;

import com.example.shikooo.moviesapp.model.Credits;
import com.example.shikooo.moviesapp.model.MoviesImages;
import com.example.shikooo.moviesapp.model.MoviesResponse;
import com.example.shikooo.moviesapp.model.Similar;
import com.example.shikooo.moviesapp.model.Trialer;
import com.example.shikooo.moviesapp.model.TvDetails;
import com.example.shikooo.moviesapp.model.TvImages;
import com.example.shikooo.moviesapp.model.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shikooo on 4/30/2018.
 */

public interface Service {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies (@Query("api_key") String apikey);

    @GET("movie/{movie_id}/images")
    Call<MoviesImages> getMoviesImages (@Path("movie_id") int id , @Query("api_key") String apikey);

    @GET("movie/{movie_id}/credits")
    Call<Credits> getMoviesCredites (@Path("movie_id") int id , @Query("api_key") String apikey);

    @GET("movie/{movie_id}/similar")
    Call<Similar> getSimilarMovies (@Path("movie_id") int id , @Query("api_key") String apikey);

    @GET("movie/{movie_id}/videos")
    Call<Trialer> getVideosMovies (@Path("movie_id") int id , @Query("api_key") String apikey);

    @GET("tv/popular")
    Call<TvResponse> getTvShows (@Query("api_key") String apikey);

    @GET("tv/{tv_id}/images")
    Call<TvImages> getTvImages (@Path("tv_id") int id , @Query("api_key") String apikey);

    @GET("tv/{tv_id}/credits")
    Call<Credits> getTvCredites (@Path("tv_id") int id , @Query("api_key") String apikey);

    @GET("tv/{tv_id}/videos")
    Call<Trialer> getTvVideos (@Path("tv_id") int id , @Query("api_key") String apikey);

    @GET("tv/{tv_id}")
    Call<TvDetails> getTvSeasons (@Path("tv_id") int id , @Query("api_key") String apikey);














}
