package com.example.shikooo.moviesapp.Database.LocalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.shikooo.moviesapp.Database.WatchList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shikooo on 11/8/2018.
 */


@Dao
public interface WatchListDAO {

    @Query("SELECT * FROM watchlist ORDER BY saveTime DESC")
    Flowable<List<WatchList>> getAllWatchList ();

    @Insert
    void insertWatchlist (WatchList... watchLists);

    @Update
    void updateWatchlist (WatchList... watchLists);

    @Delete
    void deleteWatchlist (WatchList... watchLists);

    @Query("SELECT EXISTS (SELECT 1 FROM watchlist WHERE id=:id )")
    int isFavorite (int id);

    @Query("DELETE FROM watchlist")
    void deleteAllWatchlist();
}
