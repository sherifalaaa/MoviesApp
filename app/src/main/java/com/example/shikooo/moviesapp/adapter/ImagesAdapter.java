package com.example.shikooo.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.model.Posters;

import java.util.List;

/**
 * Created by shikooo on 11/4/2018.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder>
{
    private Context context;
    private List<Posters> imagesPosters ;


    public ImagesAdapter(Context context, List<Posters> imagesPosters) {
        this.context = context;
        this.imagesPosters = imagesPosters;
    }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context).load(imagesPosters.get(position).getFile_path()).placeholder(R.drawable.loading).into(holder.movieImages);

    }


    @Override
    public int getItemCount() {
        return imagesPosters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

         private ImageView movieImages ;

        public ViewHolder(View itemView) {
            super(itemView);

            movieImages = (ImageView) itemView.findViewById(R.id.movie_image);
        }
    }
}
