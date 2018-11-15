package com.example.shikooo.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.model.Seasons;

import java.util.List;

/**
 * Created by shikooo on 11/7/2018.
 */

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private Context context;
    private List<Seasons> seasonsList ;

    public SeasonAdapter(Context context, List<Seasons> seasonsList) {
        this.context = context;
        this.seasonsList = seasonsList;
    }

    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.season_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeasonAdapter.ViewHolder holder, int position) {

        holder.seasonName.setText(seasonsList.get(position).getName());
        holder.episodesNum.setText("Eps Num : "+ seasonsList.get(position).getEpisode_count());
        holder.firstInAir.setText("OnAir : " +seasonsList.get(position).getAirDate());
        Glide.with(context).load(seasonsList.get(position).getPosterPath()).placeholder(R.drawable.loading).into(holder.seasonImage);

    }

    @Override
    public int getItemCount() {
        return seasonsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView seasonImage;
        TextView seasonName,episodesNum,firstInAir;

        public ViewHolder(View itemView) {
            super(itemView);

            seasonImage =(ImageView) itemView.findViewById(R.id.season_image);
            seasonName = (TextView) itemView.findViewById(R.id.season_name);
            episodesNum = (TextView) itemView.findViewById(R.id.episodes_num);
            firstInAir = (TextView) itemView.findViewById(R.id.first_on_air);


        }
    }
}
