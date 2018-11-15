package com.example.shikooo.moviesapp.Database.DataSouce;

import com.example.shikooo.moviesapp.Database.ShowsList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shikooo on 11/13/2018.
 */

public interface IShowsListDataSource {

    Flowable<List<ShowsList>> getAllShowsList ();
    int isFavorite (int id);
    void insertShowslist (ShowsList... showsLists);
    void updateShowslist (ShowsList... showsLists);
    void deleteShowslist (ShowsList... showsLists);
    void deleteAllShowslist();
}
