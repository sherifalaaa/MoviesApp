package com.example.shikooo.moviesapp.Database.DataSouce;

import com.example.shikooo.moviesapp.Database.ShowsList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shikooo on 11/8/2018.
 */

public class ShowsListRepository implements IShowsListDataSource {

    private IShowsListDataSource mLocalDataource;
    private static ShowsListRepository instance;

    public ShowsListRepository(IShowsListDataSource mLocalDataource) {
        this.mLocalDataource = mLocalDataource;
    }

    public static ShowsListRepository getInstance (IShowsListDataSource mLocalDataource) {
        if (instance == null)
        {
            instance  = new ShowsListRepository(mLocalDataource);
        }

        return instance;
    }


    @Override
    public Flowable<List<ShowsList>> getAllShowsList() {
        return mLocalDataource.getAllShowsList();
    }

    @Override
    public int isFavorite(int id) {
        return mLocalDataource.isFavorite(id);
    }

    @Override
    public void insertShowslist(ShowsList... showsLists) {
        mLocalDataource.insertShowslist(showsLists);
    }

    @Override
    public void updateShowslist(ShowsList... showsLists) {
        mLocalDataource.updateShowslist(showsLists);
    }

    @Override
    public void deleteShowslist(ShowsList... showsLists) {
        mLocalDataource.deleteShowslist(showsLists);
    }

    @Override
    public void deleteAllShowslist() {
        mLocalDataource.deleteAllShowslist();
    }
}
