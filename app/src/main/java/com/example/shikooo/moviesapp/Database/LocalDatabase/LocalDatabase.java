package com.example.shikooo.moviesapp.Database.LocalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.shikooo.moviesapp.Database.ShowsList;
import com.example.shikooo.moviesapp.Database.WatchList;

import static com.example.shikooo.moviesapp.Database.LocalDatabase.LocalDatabase.DATABASE_VERSION;

/**
 * Created by shikooo on 11/8/2018.
 */

@Database(entities ={WatchList.class, ShowsList.class} ,version = DATABASE_VERSION)
public abstract class LocalDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME= "WatchListDatabase";

    public abstract WatchListDAO watchListDAO();
    public abstract ShowsListDAO showsListDAO();
    private static LocalDatabase instance ;

    public static LocalDatabase getInstance (Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context,LocalDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
