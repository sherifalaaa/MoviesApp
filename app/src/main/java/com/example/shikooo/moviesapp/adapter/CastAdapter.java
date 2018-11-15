package com.example.shikooo.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shikooo.moviesapp.R;
import com.example.shikooo.moviesapp.model.Cast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shikooo on 11/5/2018.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    Context context;
    List<Cast> castList ;

    public CastAdapter(Context context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(castList.get(position).getProfile_path()).placeholder(R.drawable.loading).dontAnimate().into(holder.castImage);
        holder.castName.setText(castList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView castImage;
        TextView castName;

        public ViewHolder(View itemView) {
            super(itemView);

            castImage = (CircleImageView) itemView.findViewById(R.id.cast_image);
            castName = (TextView) itemView.findViewById(R.id.castt_name);
        }
    }
}
