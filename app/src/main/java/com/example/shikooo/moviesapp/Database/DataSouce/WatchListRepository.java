package com.example.shikooo.moviesapp.Database.DataSouce;

import com.example.shikooo.moviesapp.Database.WatchList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shikooo on 11/8/2018.
 */

public class WatchListRepository implements IWatchlistDataSource {

    private IWatchlistDataSource mLocalDataource;
    private static WatchListRepository instance;

    public WatchListRepository(IWatchlistDataSource mLocalDataource) {
        this.mLocalDataource = mLocalDataource;
    }

    public static WatchListRepository getInstance (IWatchlistDataSource mLocalDataource) {
        if (instance == null)
        {
            instance  = new WatchListRepository(mLocalDataource);
        }

        return instance;
    }

    @Override
    public Flowable<List<WatchList>> getAllWatchList() {
        return mLocalDataource.getAllWatchList();
    }

    @Override
    public int isFavorite(int id) {
        return mLocalDataource.isFavorite(id);
    }

    @Override
    public void insertWatchlist(WatchList... watchLists) {
            mLocalDataource.insertWatchlist(watchLists);
    }

    @Override
    public void updateWatchlist(WatchList... watchLists) {
            mLocalDataource.updateWatchlist(watchLists);
    }

    @Override
    public void deleteWatchlist(WatchList... watchLists) {
            mLocalDataource.deleteWatchlist(watchLists);
    }

    @Override
    public void deleteAllWatchlist() {
            mLocalDataource.deleteAllWatchlist();
    }
}
