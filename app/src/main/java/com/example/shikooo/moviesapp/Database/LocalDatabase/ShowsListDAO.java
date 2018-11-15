package com.example.shikooo.moviesapp.Database.LocalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.shikooo.moviesapp.Database.ShowsList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shikooo on 11/8/2018.
 */


@Dao
public interface ShowsListDAO {

    @Query("SELECT * FROM showslist ORDER BY saveTime DESC")
    Flowable<List<ShowsList>> getAllShowsList();

    @Insert
    void insertShowslist(ShowsList... showsLists);

    @Update
    void updateShowslist(ShowsList... showsLists);

    @Delete
    void deleteShowslist(ShowsList... showsLists);

    @Query("SELECT EXISTS (SELECT 1 FROM showslist WHERE id=:id )")
    int isFavorite(int id);

    @Query("DELETE FROM showslist")
    void deleteAllShowslist();
}
