package com.example.shikooo.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.model.Results;

import java.util.List;

/**
 * Created by shikooo on 11/5/2018.
 */

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.ViewHolder>
{
    Context context;
    List<Results> resultsList ;

    public SimilarAdapter(Context context, List<Results> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
    }

    @Override
    public SimilarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.similar_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimilarAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(resultsList.get(position).getPosterPath()).placeholder(R.drawable.loading).into(holder.simliarImage);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView simliarImage;


        public ViewHolder(View itemView) {
            super(itemView);

            simliarImage = (ImageView) itemView.findViewById(R.id.similar_image);
        }
    }
}
