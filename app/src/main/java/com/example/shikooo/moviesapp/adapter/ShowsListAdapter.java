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
import com.example.shikooo.moviesapp.Database.ShowsList;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.TvDetailsActivity;
import com.example.shikooo.moviesapp.model.Tv;

import java.util.List;

/**
 * Created by shikooo on 11/13/2018.
 */

public class ShowsListAdapter extends RecyclerView.Adapter<ShowsListAdapter.ViewHolder> {

    Context context;
    List<ShowsList> showsLists ;
    View view;

    public ShowsListAdapter(Context context, List<ShowsList> showsLists) {
        this.context = context;
        this.showsLists = showsLists;
    }

    @Override
    public ShowsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.movie_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowsListAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(showsLists.get(position).getPosterPath()).placeholder(R.drawable.loading).into(holder.watchListImag);
        holder.watchListName.setText(showsLists.get(position).getOriginalTitle());

    }

    @Override
    public int getItemCount() {
        return showsLists.size();
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

                    ShowsList clickedDataItem =  showsLists.get(pos);

                    Intent intent = new Intent(context, TvDetailsActivity.class);
                    intent.putExtra("original_name",showsLists.get(pos).getOriginalTitle());
                    intent.putExtra("poster_path",showsLists.get(pos).getPosterPath());
                    intent.putExtra("first_air_date",showsLists.get(pos).getReleaseDate());
                    intent.putExtra("backdrop_path",showsLists.get(pos).getBackdropPath());
                    intent.putExtra("vote_average",showsLists.get(pos).getVoteAverage());
                    intent.putExtra("overview",showsLists.get(pos).getOverview());
                    intent.putExtra("vote_count",showsLists.get(pos).getVoteAverage());
                    intent.putExtra("id",showsLists.get(pos).getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Tv tv = new Tv();
                    tv.setId(showsLists.get(pos).getId());
                    tv.setTitle(showsLists.get(pos).getOriginalTitle());
                    tv.setPosterPath(showsLists.get(pos).getPosterPath());
                    Common.select_tv = tv;
                    context.startActivity(intent);
                    Toast.makeText(view.getContext(),"you clicked "+clickedDataItem.getOriginalTitle(),Toast.LENGTH_LONG).show();


                }
            });
        }
    }
}
