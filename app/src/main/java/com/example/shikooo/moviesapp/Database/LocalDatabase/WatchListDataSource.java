package com.example.shikooo.moviesapp.Database.LocalDatabase;

import com.example.shikooo.moviesapp.Database.DataSouce.IWatchlistDataSource;
import com.example.shikooo.moviesapp.Database.WatchList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shikooo on 11/8/2018.
 */

public class WatchListDataSource implements IWatchlistDataSource {

    private  WatchListDAO watchListDAO ;
    private static  WatchListDataSource instance;

    public WatchListDataSource(WatchListDAO watchListDAO) {
        this.watchListDAO = watchListDAO;
    }

    public static WatchListDataSource getInstance (WatchListDAO watchListDAO)
    {
        if (instance == null)
        {
            instance = new WatchListDataSource(watchListDAO);
        }
        return instance;
    }

    @Override
    public Flowable<List<WatchList>> getAllWatchList() {
        return watchListDAO.getAllWatchList();
    }

    @Override
    public int isFavorite(int id) {
        return watchListDAO.isFavorite(id);
    }

    @Override
    public void insertWatchlist(WatchList... watchLists) {
            watchListDAO.insertWatchlist(watchLists);
    }

    @Override
    public void updateWatchlist(WatchList... watchLists) {
            watchListDAO.updateWatchlist(watchLists);
    }

    @Override
    public void deleteWatchlist(WatchList... watchLists) {
            watchListDAO.deleteWatchlist(watchLists);
    }

    @Override
    public void deleteAllWatchlist() {
            watchListDAO.deleteAllWatchlist();
    }
}
