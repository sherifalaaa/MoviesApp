package com.example.shikooo.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.TvDetailsActivity;
import com.example.shikooo.moviesapp.model.Tv;

import java.util.List;

/**
 * Created by shikooo on 11/7/2018.
 */

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    Context context;
    private List<Tv> tvList;

    public TvAdapter(Context context, List<Tv> tvList) {
        this.context = context;
        this.tvList = tvList;
    }

    @Override
    public TvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(tvList.get(position).getPosterPath()).placeholder(R.drawable.loading).into(holder.tvImage);
        holder.tvName.setText(tvList.get(position).getOriginal_name());

    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView tvImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.title);
            tvImage = (ImageView) itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Intent intent = new Intent(context, TvDetailsActivity.class);
                        intent.putExtra("original_name",tvList.get(pos).getOriginal_name());
                        intent.putExtra("poster_path",tvList.get(pos).getPosterPath());
                        intent.putExtra("first_air_date",tvList.get(pos).getFirst_air_date());
                        intent.putExtra("backdrop_path",tvList.get(pos).getBackdropPath());
                        intent.putExtra("vote_average",tvList.get(pos).getVoteAverage());
                        intent.putExtra("overview",tvList.get(pos).getOverview());
                        intent.putExtra("vote_count",tvList.get(pos).getVoteCount());
                        intent.putExtra("id",tvList.get(pos).getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);


                    }

                }
            });
        }
    }

}
