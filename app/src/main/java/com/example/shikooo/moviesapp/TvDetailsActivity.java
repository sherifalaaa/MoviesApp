package com.example.shikooo.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.shikooo.moviesapp.Database.DataSouce.ShowsListRepository;
import com.example.shikooo.moviesapp.Database.LocalDatabase.LocalDatabase;
import com.example.shikooo.moviesapp.Database.LocalDatabase.ShowsListDataSource;
import com.example.shikooo.moviesapp.Database.ShowsList;
import com.example.shikooo.moviesapp.Interface.Service;
import com.example.shikooo.moviesapp.adapter.CastAdapter;
import com.example.shikooo.moviesapp.adapter.ImagesAdapter;
import com.example.shikooo.moviesapp.adapter.SeasonAdapter;
import com.example.shikooo.moviesapp.api.Client;
import com.example.shikooo.moviesapp.model.Cast;
import com.example.shikooo.moviesapp.model.Credits;
import com.example.shikooo.moviesapp.model.Posters;
import com.example.shikooo.moviesapp.model.Seasons;
import com.example.shikooo.moviesapp.model.Trialer;
import com.example.shikooo.moviesapp.model.TvDetails;
import com.example.shikooo.moviesapp.model.TvImages;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDetailsActivity extends AppCompatActivity {

    private TextView nameofmovie,movieDuration,languageOfMovie,ratingNum,seasonNum ;
    private ImageView imageView,imageView2,saveButton;
    private RatingBar ratingBar;
    private ImageButton trailerButton;
    private ReadMoreTextView plotsynopsis;

    private RecyclerView imagesRecyclerview , castRecyclerview , seasonRecyclerView;
    private List<Posters> images_recycler;
    private List<Cast> cast_recycler;
    private List<Seasons> season_recycler;

    private ImagesAdapter imagesAdapter;
    private CastAdapter castAdapter;
    private SeasonAdapter seasonAdapter;

    CompositeDisposable compositeDisposable;
    ShowsListRepository showsListRepository;

    int tvId;
    int isfavorite;
    String thumbnail,tvName,overview,firstAirDate,language0fTv,backdropsPath;
    Double tvRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_details);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initcollapsingToolbar();

        imageView = (ImageView) findViewById(R.id.thumbnail_image_header);
        imageView2 = (ImageView) findViewById(R.id.layout_image);
        nameofmovie = (TextView) findViewById(R.id.movietitle);
        languageOfMovie = (TextView) findViewById(R.id.movie_Lang);
        plotsynopsis = (ReadMoreTextView) findViewById(R.id.plotsynopsis);
        movieDuration = (TextView) findViewById(R.id.movie_duration);
        ratingBar = (RatingBar) findViewById(R.id.movie_rating);
        ratingNum = (TextView) findViewById(R.id.rating_num);
        seasonNum = (TextView) findViewById(R.id.similar_title);
        trailerButton = (ImageButton) findViewById(R.id.trailer_button);
        saveButton = (ImageView) findViewById(R.id.save_button);

        seasonNum.setText("SEASONS");

        initView();

        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(this);
        showsListRepository = ShowsListRepository.getInstance(ShowsListDataSource.getInstance(database.showsListDAO()));

        Intent intentTheactivity = getIntent();
        if (intentTheactivity.hasExtra("original_name"))
        {
             thumbnail = getIntent().getExtras().getString("poster_path");
             tvName = getIntent().getExtras().getString("original_name");
             overview = getIntent().getExtras().getString("overview");
             tvRating = getIntent().getExtras().getDouble("vote_average");
             firstAirDate = getIntent().getExtras().getString("first_air_date");
             language0fTv = getIntent().getExtras().getString("original_language");
             backdropsPath = getIntent().getExtras().getString("backdrop_path");
             tvId = getIntent().getExtras().getInt("id");

            float movieRating = Float.parseFloat(Double.toString(tvRating));
            ratingBar.setRating(movieRating);

            Glide.with(this).load(thumbnail).placeholder(R.drawable.loading).into(imageView);

            Glide.with(this).load(backdropsPath).placeholder(R.drawable.loading).into(imageView2);

            nameofmovie.setText(tvName);
            plotsynopsis.setText(overview);
            movieDuration.setText(firstAirDate);
            languageOfMovie.setText("Lang : "+language0fTv);
            ratingNum.setText("("+Double.toString(tvRating)+")");

        }
        else
        {
            Toast.makeText(this,"No Api Data",Toast.LENGTH_LONG).show();
        }

        new IsFavorite().execute();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isfavorite != 1)
                {
                    addToWatchList();
                    saveButton.setImageResource(R.drawable.ic_favorite_color_34dp);
                }
                else
                {
                    deleteFromWatchList();
                    saveButton.setImageResource(R.drawable.ic_favorite_border_color_34dp);
                }
            }
        });
    }

    private void initcollapsingToolbar()
    {

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                    isShow = true;

                }
                else if (isShow)
                {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    private void initView ()
    {
        loadMovieVideo();

        images_recycler = new ArrayList<>();
        imagesAdapter = new ImagesAdapter(this,images_recycler);

        imagesRecyclerview = (RecyclerView)findViewById(R.id.images_recyclerview);
        imagesRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        imagesRecyclerview.setAdapter(imagesAdapter);
        imagesAdapter.notifyDataSetChanged();

        loadImagesJson();

        cast_recycler = new  ArrayList<>();
        castAdapter = new CastAdapter(this,cast_recycler);

        castRecyclerview = (RecyclerView)findViewById(R.id.cast_recyclerview);
        castRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        castRecyclerview.setAdapter(castAdapter);
        castAdapter.notifyDataSetChanged();

        loadCastJson();

        season_recycler = new ArrayList<>();
        seasonAdapter = new SeasonAdapter(this,season_recycler);

        seasonRecyclerView = (RecyclerView) findViewById(R.id.similar_recyclerview);
        seasonRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        seasonRecyclerView.setAdapter(seasonAdapter);
        seasonRecyclerView.setFitsSystemWindows(true);
        seasonAdapter.notifyDataSetChanged();

        loadTvDetails();
    }

    private void loadImagesJson()
    {

        int tvId = getIntent().getExtras().getInt("id");
        Service apiService = Client.getClient().create(Service.class);
        Call<TvImages> call = apiService.getTvImages(tvId,"2a012958dc19d5c620aec582aa127912");
        call.enqueue(new Callback<TvImages>() {
            @Override
            public void onResponse(Call<TvImages> call, Response<TvImages> response) {
                List<Posters> postersList = response.body().getPosters();
                imagesRecyclerview.setAdapter(new ImagesAdapter(TvDetailsActivity.this,postersList));
                imagesRecyclerview.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<TvImages> call, Throwable t) {
                Toast.makeText(TvDetailsActivity.this, "Error to fetch images!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCastJson()
    {

        int tvId = getIntent().getExtras().getInt("id");
        Service apiService = Client.getClient().create(Service.class);
        Call<Credits> call = apiService.getTvCredites(tvId,"2a012958dc19d5c620aec582aa127912");
        call.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                List<Cast> castsList = response.body().getCast();
                castRecyclerview.setAdapter(new CastAdapter(TvDetailsActivity.this,castsList));
                castRecyclerview.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Toast.makeText(TvDetailsActivity.this, "Error to fetch images!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadMovieVideo()
    {
        int tvId = getIntent().getExtras().getInt("id");
        Service apiService = Client.getClient().create(Service.class);
        final Call<Trialer> resultsCall = apiService.getTvVideos(tvId,"2a012958dc19d5c620aec582aa127912");
        resultsCall.enqueue(new Callback<Trialer>() {
            @Override
            public void onResponse(Call<Trialer> call, Response<Trialer> response) {
                List<com.example.shikooo.moviesapp.model.Trailer.Results> resultsList = response.body().getResults();

                final String key = resultsList.get(0).getKey();
                trailerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+key));
                        startActivity(intent);

                    }
                });



            }

            @Override
            public void onFailure(Call<Trialer> call, Throwable t) {

            }
        });

    }

    private void loadTvDetails ()
    {

        int tvId = getIntent().getExtras().getInt("id");
        Service apiService = Client.getClient().create(Service.class);
        final Call<TvDetails> tvDetailsCall = apiService.getTvSeasons(tvId,"2a012958dc19d5c620aec582aa127912");
        tvDetailsCall.enqueue(new Callback<TvDetails>() {
            @Override
            public void onResponse(Call<TvDetails> call, Response<TvDetails> response) {
                List<Seasons> seasonsList = response.body().getSeasons();
                seasonRecyclerView.setAdapter(new SeasonAdapter(TvDetailsActivity.this,seasonsList) );
                seasonRecyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<TvDetails> call, Throwable t) {

            }
        });


    }

    private void addToWatchList ()
    {
        Disposable disposables = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {


            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

                ShowsList showsList = new ShowsList(tvName,language0fTv
                        ,backdropsPath,overview,firstAirDate,tvRating,thumbnail,tvId,String.valueOf(System.currentTimeMillis()));

                showsListRepository.insertShowslist(showsList);

                e.onComplete();


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.e("Error",throwable.getMessage());

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

        compositeDisposable.add(disposables);
    }

    private void deleteFromWatchList()
    {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

                ShowsList showsList = new ShowsList(tvName,language0fTv
                        ,backdropsPath,overview,firstAirDate,tvRating,thumbnail,tvId,String.valueOf(System.currentTimeMillis()));

                showsListRepository.deleteShowslist(showsList);
                e.onComplete();

            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    private class IsFavorite extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {


            if (showsListRepository.isFavorite(tvId) == 1) {
                saveButton.setImageResource(R.drawable.ic_favorite_color_34dp);
                isfavorite = showsListRepository.isFavorite(tvId);
            }

            else
                saveButton.setImageResource(R.drawable.ic_favorite_border_color_34dp);

            return null;
        }
    }

}
