package com.rais.haidar.madesub3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rais.haidar.madesub3.DetailActivity;
import com.rais.haidar.madesub3.Model.TvSeriesItem;
import com.rais.haidar.madesub3.R;

import java.util.ArrayList;

public class TVSeriesAdapter extends RecyclerView.Adapter<TVSeriesAdapter.TvViewHolder> {
    final private Context mContext;
    private ArrayList<TvSeriesItem> TVList;

    public ArrayList<TvSeriesItem> getTVList() {
        return TVList;
    }

    public void setTVList(ArrayList<TvSeriesItem> TVList) {
        this.TVList = TVList;
        notifyDataSetChanged();
    }

    public TVSeriesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new TvViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        holder.nBind(getTVList().get(position));
    }

    @Override
    public int getItemCount() {
        return getTVList().size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul,txtRilis;
        ImageView imgBanner;
        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.text_title);
            txtRilis = itemView.findViewById(R.id.text_release);
            imgBanner = itemView.findViewById(R.id.image_photo);
        }
        void nBind(final TvSeriesItem tvSeriesItem) {
            txtJudul.setText(tvSeriesItem.getName());
            txtRilis.setText(tvSeriesItem.getFirstAirDate());

            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185/"+tvSeriesItem.getPosterPath())
                    .into(imgBanner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(DetailActivity.movieId_key, tvSeriesItem.getId()+"");
                    intent.putExtra(DetailActivity.type,"TV");
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
