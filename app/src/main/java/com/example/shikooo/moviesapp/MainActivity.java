package com.example.shikooo.moviesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.shikooo.moviesapp.Fragment.MoviesFragment;
import com.example.shikooo.moviesapp.Fragment.TvShowsFragment;
import com.example.shikooo.moviesapp.Fragment.WatchListFragment;
import com.example.shikooo.moviesapp.adapter.MoviesAdapter;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    Context context;

    public static final String LOG_TAG = MoviesAdapter.class.getName();
    public static final String API_KEY = "2a012958dc19d5c620aec582aa127912";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Movies App");;
        setSupportActionBar(toolbar);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSectected);


        loadFragment(new MoviesFragment());

   }

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSectected
         = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            switch (item.getItemId())
            {
                case R.id.menu_movies:

                    fragment = new MoviesFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.menu_shows:

                    fragment = new TvShowsFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.menu_watchlist:

                    fragment = new WatchListFragment(context);
                    loadFragment(fragment);
                    return true;


            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.relative_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
