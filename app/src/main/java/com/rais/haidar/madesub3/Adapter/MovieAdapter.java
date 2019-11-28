package com.rais.haidar.madesub3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rais.haidar.madesub3.DetailActivity;
import com.rais.haidar.madesub3.Model.MoviesItem;
import com.rais.haidar.madesub3.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    final private Context mContext;
    private ArrayList<MoviesItem> movieList;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<MoviesItem> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<MoviesItem> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.onBind(getMovieList().get(position));
    }

    @Override
    public int getItemCount() {
        return getMovieList().size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul,txtRilis;
        ImageView imgBanner;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.text_title);
            txtRilis = itemView.findViewById(R.id.text_release);
            imgBanner = itemView.findViewById(R.id.image_photo);
        }
        void onBind(final MoviesItem moviesItem) {
            if (moviesItem.getTitle() == null){
                txtJudul.setText(moviesItem.getOriginalTitle());
            }else {
                txtJudul.setText(moviesItem.getTitle());
            }
            txtRilis.setText(moviesItem.getReleaseDate());

            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185/"+moviesItem.getPosterPath())
                    .into(imgBanner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);

                    intent.putExtra(DetailActivity.movieId_key, moviesItem.getId()+"");
                    intent.putExtra(DetailActivity.type,"MOVIE");
                    mContext.startActivity(intent);
                }
            });
            if (moviesItem.getOverview() == null || moviesItem.getOverview().isEmpty() || moviesItem.getOverview().equalsIgnoreCase("")) {
                txtJudul.setText(R.string.empty_description);
            }
        }
    }
}
