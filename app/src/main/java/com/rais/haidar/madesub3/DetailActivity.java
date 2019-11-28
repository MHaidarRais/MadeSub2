package com.rais.haidar.madesub3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rais.haidar.madesub3.Model.DetailViewModel;
import com.rais.haidar.madesub3.Model.MoviesItem;
import com.rais.haidar.madesub3.Model.TvSeriesItem;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgDetailBanner,imgDetailPoster;
    private ProgressBar progressBar;
    private TextView txtDetailJudul,txtDetailRilis,txtDetailDeskripsi;

    public static final String movieId_key= "MOVIE_ID";
    public static final String type = "TYPE";
    private final String imageUrl = "https://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailViewModel detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        imgDetailBanner = findViewById(R.id.detailBannerImage);
        imgDetailPoster = findViewById(R.id.detailImage);
        txtDetailJudul = findViewById(R.id.detailTxtJudul);
        txtDetailRilis = findViewById(R.id.detailTxtRilis);
        txtDetailDeskripsi = findViewById(R.id.detailTxtDeskripsi);
        progressBar = findViewById(R.id.detailProgress);
        View detailViewFrame = findViewById(R.id.detailViewFrame);


        String typeo = getIntent().getStringExtra(type);
        String movieId = getIntent().getStringExtra(movieId_key);
        showLoading(true);

        if (typeo.equalsIgnoreCase("MOVIE")){
            Log.d("onCreate: ",typeo);
            detailViewModel.getMovies().observe(this, getMovies);
            detailViewModel.getMovieData(movieId,detailViewFrame);
        }else {
            Log.d("onCreate: ",typeo);
            detailViewModel.getTvSeries().observe(this, getTvSeries);
            detailViewModel.getTvData(movieId,detailViewFrame);
        }

    }

    private Observer<MoviesItem> getMovies = new Observer<MoviesItem>() {
        @Override
        public void onChanged(@Nullable MoviesItem moviesItem) {
            if (moviesItem != null) {

                txtDetailJudul.setText(moviesItem.getTitle());
//                Log.d("isiOverview", moviesItem.getTitle());
                txtDetailDeskripsi.setText(moviesItem.getOverview());
                txtDetailRilis.setText(moviesItem.getReleaseDate());

                Glide.with(getApplicationContext()).load(imageUrl+moviesItem.getPosterPath()).into(imgDetailPoster);
                Glide.with(getApplicationContext()).load(imageUrl+moviesItem.getPosterPath()).into(imgDetailBanner);

                if (moviesItem.getOverview() == null || moviesItem.getOverview().isEmpty()) {
//                    Log.d("isiOverview", moviesItem.getTitle());
                    txtDetailDeskripsi.setText(getResources().getText(R.string.empty_description));
                }

                showLoading(false);
            }
        }
    };

    private Observer<TvSeriesItem> getTvSeries = new Observer<TvSeriesItem>() {
        @Override
        public void onChanged(@Nullable TvSeriesItem tvShowsItem) {
            if (tvShowsItem != null) {

                txtDetailJudul.setText(tvShowsItem.getName());
                txtDetailDeskripsi.setText(tvShowsItem.getOverview());
                txtDetailRilis.setText(tvShowsItem.getFirstAirDate());

                Glide.with(getApplicationContext()).load(imageUrl+tvShowsItem.getPosterPath()).into(imgDetailPoster);
                Glide.with(getApplicationContext()).load(imageUrl+tvShowsItem.getPosterPath()).into(imgDetailBanner);
                showLoading(false);


                if (tvShowsItem.getOverview() == null || tvShowsItem.getOverview().isEmpty()) {
                    txtDetailDeskripsi.setText(R.string.empty_description);
                }
            }
        }
    };

    private void showLoading(Boolean bool) {
        if (bool) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
