package com.example.shikooo.moviesapp.Database.LocalDatabase;

import com.example.shikooo.moviesapp.Database.DataSouce.IShowsListDataSource;
import com.example.shikooo.moviesapp.Database.ShowsList;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shikooo on 11/8/2018.
 */

public class ShowsListDataSource implements IShowsListDataSource {

    private  ShowsListDAO showsListDAO ;
    private static ShowsListDataSource instance;

    public ShowsListDataSource(ShowsListDAO showsListDAO) {
        this.showsListDAO = showsListDAO;
    }

    public static ShowsListDataSource getInstance (ShowsListDAO showsListDAO)
    {
        if (instance == null)
        {
            instance = new ShowsListDataSource(showsListDAO);
        }
        return instance;
    }


    @Override
    public Flowable<List<ShowsList>> getAllShowsList() {
        return showsListDAO.getAllShowsList();
    }

    @Override
    public int isFavorite(int id) {
        return showsListDAO.isFavorite(id);
    }

    @Override
    public void insertShowslist(ShowsList... showsLists) {
        showsListDAO.insertShowslist(showsLists);
    }

    @Override
    public void updateShowslist(ShowsList... showsLists) {
        showsListDAO.updateShowslist(showsLists);
    }

    @Override
    public void deleteShowslist(ShowsList... showsLists) {
        showsListDAO.deleteShowslist(showsLists);
    }

    @Override
    public void deleteAllShowslist() {
        showsListDAO.getAllShowsList();
    }
}
