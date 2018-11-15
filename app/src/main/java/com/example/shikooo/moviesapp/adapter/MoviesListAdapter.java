package com.example.shikooo.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shikooo.moviesapp.Common;
import com.example.shikooo.moviesapp.Database.WatchList;
import com.example.shikooo.moviesapp.DetailActivity;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.model.Movie;

import java.util.List;

/**
 * Created by shikooo on 11/8/2018.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    Context context;
    List<WatchList> watchLists ;
    View view;

    public MoviesListAdapter(Context context, List<WatchList> watchLists) {
        this.context = context;
        this.watchLists = watchLists;
    }

    @Override
    public MoviesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.movie_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesListAdapter.ViewHolder holder, int position) {

       Glide.with(context).load(watchLists.get(position).getPosterPath()).placeholder(R.drawable.loading).into(holder.watchListImag);
       holder.watchListName.setText(watchLists.get(position).getOriginalTitle());


    }

    @Override
    public int getItemCount() {
        return watchLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView watchListImag;
        TextView watchListName;

        public ViewHolder(View itemView) {
            super(itemView);

            watchListName = (TextView)itemView.findViewById(R.id.title);
            watchListImag = (ImageView)itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int pos = getAdapterPosition();

                    WatchList clickedDataItem =  watchLists.get(pos);

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("original_title" , watchLists.get(pos).getOriginalTitle());
                    intent.putExtra("poster_path",watchLists.get(pos).getPosterPath());
                    intent.putExtra("overview",watchLists.get(pos).getOverview());
                    intent.putExtra("vote_average",watchLists.get(pos).getVoteAverage());
                    intent.putExtra("release_date",watchLists.get(pos).getReleaseDate());
                    intent.putExtra("original_language",watchLists.get(pos).getOriginalLanguage());
                    intent.putExtra("backdrop_path",watchLists.get(pos).getBackdropPath());
                    intent.putExtra("id",watchLists.get(pos).getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Movie movie = new Movie();
                    movie.setId(watchLists.get(pos).getId());
                    movie.setTitle(watchLists.get(pos).getOriginalTitle());
                    movie.setPosterPath(watchLists.get(pos).getPosterPath());
                    Common.select_movie = movie;
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(),"you clicked "+clickedDataItem.getOriginalTitle(),Toast.LENGTH_LONG).show();


                }
            });


        }
    }
}
