package com.example.shikooo.moviesapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shikooo.moviesapp.Database.DataSouce.ShowsListRepository;
import com.example.shikooo.moviesapp.Database.DataSouce.WatchListRepository;
import com.example.shikooo.moviesapp.Database.LocalDatabase.LocalDatabase;
import com.example.shikooo.moviesapp.Database.LocalDatabase.ShowsListDataSource;
import com.example.shikooo.moviesapp.Database.LocalDatabase.WatchListDataSource;
import com.example.shikooo.moviesapp.Database.ShowsList;
import com.example.shikooo.moviesapp.Database.WatchList;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.adapter.MoviesListAdapter;
import com.example.shikooo.moviesapp.adapter.ShowsListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WatchListFragment extends Fragment {

    Context context;
    View view;
    RecyclerView movieListRecyclerView,showListRecyclerView;
    List<WatchList> watchLists;
    List<ShowsList> showsLists;
    MoviesListAdapter moviesListAdapter;
    ShowsListAdapter showsListAdapter;


    CompositeDisposable compositeDisposable;
    WatchListRepository watchListRepository;
    ShowsListRepository showsListRepository;

    @SuppressLint("ValidFragment")
    public WatchListFragment(Context context)
    {
        this.context = context;

    }


    public WatchListFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_watch_list, container, false);

        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(getContext());
        watchListRepository = WatchListRepository.getInstance(WatchListDataSource.getInstance(database.watchListDAO()));
        showsListRepository = ShowsListRepository.getInstance(ShowsListDataSource.getInstance(database.showsListDAO()));

        movieListRecyclerView = (RecyclerView)view.findViewById(R.id.movie_recycler);
        movieListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        movieListRecyclerView.smoothScrollToPosition(0);

        showListRecyclerView = (RecyclerView)view.findViewById(R.id.shows_recycler);
        showListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        showListRecyclerView.smoothScrollToPosition(0);

        watchLists = new ArrayList<>();
        showsLists = new ArrayList<>();

        moviesListAdapter = new MoviesListAdapter(getActivity(),watchLists);
        showsListAdapter = new ShowsListAdapter(getActivity(),showsLists);

        movieListRecyclerView.setAdapter(moviesListAdapter);
        showListRecyclerView.setAdapter(showsListAdapter);

        loadWatchList();
        loadShowsList();

        return view;

    }

    public void loadWatchList()
    {

        Disposable disposable = watchListRepository.getAllWatchList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<WatchList>>() {
                    @Override
                    public void accept(List<WatchList> watchLists) throws Exception {
                        onGetAllWatchListSucess(watchLists);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.d("Error",throwable.getMessage());

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void onGetAllWatchListSucess (List<WatchList> watch)
    {
        watchLists.clear();
        watchLists.addAll(watch);
        moviesListAdapter.notifyDataSetChanged();
        showsListAdapter.notifyDataSetChanged();
    }

    public void loadShowsList()
    {

        Disposable disposable = showsListRepository.getAllShowsList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<ShowsList>>() {
                    @Override
                    public void accept(List<ShowsList> showsLists) throws Exception {
                        onGetAllShowsListSucess(showsLists);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.d("Error",throwable.getMessage());

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void onGetAllShowsListSucess (List<ShowsList> shows)
    {
        showsLists.clear();
        showsLists.addAll(shows);
        showsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
