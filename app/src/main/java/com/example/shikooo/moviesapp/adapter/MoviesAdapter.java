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
import com.example.shikooo.moviesapp.DetailActivity;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.model.Movie;

import java.util.List;

/**
 * Created by shikooo on 4/30/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>
{


    private List<Movie> movieList;
    Context context;

    public MoviesAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(movieList.get(position).getOriginalTitle());
        Glide.with(context).load(movieList.get(position).getPosterPath()).placeholder(R.drawable.loading).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private ImageView imageView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Movie clickedDataItem =  movieList.get(pos);
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("original_title" , movieList.get(pos).getOriginalTitle());
                        intent.putExtra("poster_path",movieList.get(pos).getPosterPath());
                        intent.putExtra("overview",movieList.get(pos).getOverview());
                        intent.putExtra("vote_average",movieList.get(pos).getVoteAverage());
                        intent.putExtra("release_date",movieList.get(pos).getReleaseDate());
                        intent.putExtra("original_language",movieList.get(pos).getOriginalLanguage());
                        intent.putExtra("backdrop_path",movieList.get(pos).getBackdropPath());
                        intent.putExtra("id",movieList.get(pos).getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(view.getContext(),"you clicked "+clickedDataItem.getOriginalTitle(),Toast.LENGTH_LONG).show();
                    }
                }
            });



        }
    }
}

