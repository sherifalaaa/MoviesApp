package com.example.shikooo.moviesapp.Database.DataSouce;

import com.example.shikooo.moviesapp.Database.WatchList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shikooo on 11/8/2018.
 */

public interface IWatchlistDataSource {

    Flowable<List<WatchList>> getAllWatchList ();
    int isFavorite (int id);
    void insertWatchlist (WatchList... watchLists);
    void updateWatchlist (WatchList... watchLists);
    void deleteWatchlist (WatchList... watchLists);
    void deleteAllWatchlist();


 }
