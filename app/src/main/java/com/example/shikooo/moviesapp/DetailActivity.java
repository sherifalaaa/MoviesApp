package com.example.shikooo.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.shikooo.moviesapp.Database.DataSouce.WatchListRepository;
import com.example.shikooo.moviesapp.Database.LocalDatabase.LocalDatabase;
import com.example.shikooo.moviesapp.Database.LocalDatabase.WatchListDataSource;
import com.example.shikooo.moviesapp.Database.WatchList;
import com.example.shikooo.moviesapp.Interface.Service;
import com.example.shikooo.moviesapp.adapter.CastAdapter;
import com.example.shikooo.moviesapp.adapter.ImagesAdapter;
import com.example.shikooo.moviesapp.adapter.SimilarAdapter;
import com.example.shikooo.moviesapp.api.Client;
import com.example.shikooo.moviesapp.model.Cast;
import com.example.shikooo.moviesapp.model.Credits;
import com.example.shikooo.moviesapp.model.MoviesImages;
import com.example.shikooo.moviesapp.model.Posters;
import com.example.shikooo.moviesapp.model.Results;
import com.example.shikooo.moviesapp.model.Similar;
import com.example.shikooo.moviesapp.model.Trialer;

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

/**
 * Created by shikooo on 4/30/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private TextView nameofmovie,movieDuration,languageOfMovie,ratingNum ;
    private ReadMoreTextView plotsynopsis;
    private ImageView imageView,imageView2,saveButton;
    private RatingBar ratingBar;
    private ImageButton trailerButton;

    private RecyclerView imagesRecyclerview , castRecyclerview , similarRecyclerView;
    private List<Posters> images_recycler;
    private List<Cast> cast_recycler;
    private List<Results> similar_recycler;

    private ImagesAdapter imagesAdapter;
    private CastAdapter castAdapter;
    private SimilarAdapter similarAdapter;

    public String thumbnail,synopsis,dateOfMovie,language0fMovie,backdropsPath,movieName;
    public int movie_id,isfavorite;
    public Double rating;

    CompositeDisposable compositeDisposable;
    WatchListRepository watchListRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_detail);

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
        trailerButton = (ImageButton) findViewById(R.id.trailer_button);
        saveButton = (ImageView) findViewById(R.id.save_button);

        initView();

        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(this);
        watchListRepository = WatchListRepository.getInstance(WatchListDataSource.getInstance(database.watchListDAO()));

        Intent intentTheactivity = getIntent();
        if (intentTheactivity.hasExtra("original_title"))
        {
            thumbnail = getIntent().getExtras().getString("poster_path");
            movieName = getIntent().getExtras().getString("original_title");
            synopsis = getIntent().getExtras().getString("overview");
            rating = getIntent().getExtras().getDouble("vote_average");
            dateOfMovie = getIntent().getExtras().getString("release_date");
            language0fMovie = getIntent().getExtras().getString("original_language");
            backdropsPath = getIntent().getExtras().getString("backdrop_path");
            movie_id = getIntent().getExtras().getInt("id");

            float movieRating = Float.parseFloat(Double.toString(rating));
            ratingBar.setRating(movieRating);

            Glide.with(this).load(thumbnail).placeholder(R.drawable.loading).into(imageView);

            Glide.with(this).load(backdropsPath).placeholder(R.drawable.loading).into(imageView2);

            nameofmovie.setText(movieName);
            plotsynopsis.setText(synopsis);
            movieDuration.setText(dateOfMovie);
            languageOfMovie.setText("Lang : " + language0fMovie);
            ratingNum.setText("(" + Double.toString(rating) + ")");

        }
        else
        {
            Toast.makeText(this, "No Api Data", Toast.LENGTH_LONG).show();
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

    private void initView()
    {
        loadMovieVideo();

        images_recycler = new  ArrayList<>();
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

        similar_recycler = new  ArrayList<>();
        similarAdapter = new SimilarAdapter(this,similar_recycler);

        similarRecyclerView = (RecyclerView)findViewById(R.id.similar_recyclerview);
        similarRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        similarRecyclerView.setAdapter(similarAdapter);
        similarAdapter.notifyDataSetChanged();

        loadSimilarJson();
    }

    private void loadImagesJson ()
    {
        int id = getIntent().getExtras().getInt("id");

        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesImages> call = apiService.getMoviesImages(id,"2a012958dc19d5c620aec582aa127912");
        call.enqueue(new Callback<MoviesImages>() {
            @Override
            public void onResponse(Call<MoviesImages> call, Response<MoviesImages> response) {
                    List<Posters> images = response.body().getPosters();
                    imagesRecyclerview.setAdapter(new ImagesAdapter(getApplicationContext(), images));
                    imagesRecyclerview.smoothScrollToPosition(0);

            }

            @Override
            public void onFailure(Call<MoviesImages> call, Throwable t) {

                Log.d("Error",t.getMessage());
                Toast.makeText(DetailActivity.this, "error fetch api data", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadCastJson()
    {

        int movie_id = getIntent().getExtras().getInt("id");

        Service apiServce = Client.getClient().create(Service.class);
        Call<Credits> call = apiServce.getMoviesCredites(movie_id,"2a012958dc19d5c620aec582aa127912");
        call.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                List<Cast> casts = response.body().getCast();
                castRecyclerview.setAdapter(new CastAdapter(getApplicationContext(),casts));
                castRecyclerview.smoothScrollToPosition(0);
            }


            @Override
            public void onFailure(Call<Credits> call, Throwable t) {

                Log.d("Error",t.getMessage());
                Toast.makeText(DetailActivity.this, "error fetch api data", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void loadSimilarJson()
    {
        int movie_id = getIntent().getExtras().getInt("id");
        Service apiService = Client.getClient().create(Service.class);
        Call<Similar> similarCall = apiService.getSimilarMovies(movie_id,"2a012958dc19d5c620aec582aa127912");
        similarCall.enqueue(new Callback<Similar>() {
            @Override
            public void onResponse(Call<Similar> call, Response<Similar> response) {
                List<Results> resultsList = response.body().getResults();
                similarRecyclerView.setAdapter(new SimilarAdapter(getApplicationContext(),resultsList));
                similarRecyclerView.smoothScrollToPosition(0);

            }

            @Override
            public void onFailure(Call<Similar> call, Throwable t) {

            }
        });


    }

   private void loadMovieVideo()
   {
       int movie_id = getIntent().getExtras().getInt("id");
       Service apiService = Client.getClient().create(Service.class);
       final Call<Trialer> resultsCall = apiService.getVideosMovies(movie_id,"2a012958dc19d5c620aec582aa127912");
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

   private void addToWatchList ()
   {
       Disposable disposables = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {


           @Override
           public void subscribe(ObservableEmitter<Object> e) throws Exception {

               WatchList watchList = new WatchList(movieName,language0fMovie
                       ,backdropsPath,synopsis,dateOfMovie,rating,thumbnail,movie_id,String.valueOf(System.currentTimeMillis()));

               watchListRepository.insertWatchlist(watchList);

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

               WatchList watchList = new WatchList(movieName,language0fMovie
                       ,backdropsPath,synopsis,dateOfMovie,rating,thumbnail,movie_id,String.valueOf(System.currentTimeMillis()));

                watchListRepository.deleteWatchlist(watchList);
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

    @Override
    protected void onDestroy()
    {

        compositeDisposable.clear();
        super.onDestroy();
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
            if (watchListRepository.isFavorite(movie_id) == 1) {
                saveButton.setImageResource(R.drawable.ic_favorite_color_34dp);
                isfavorite = watchListRepository.isFavorite(movie_id);
            }

            else
                saveButton.setImageResource(R.drawable.ic_favorite_border_color_34dp);

            return null;
        }
    }
}
