package com.rais.haidar.madesub3.Network;

import com.rais.haidar.madesub3.Model.MoviesItem;
import com.rais.haidar.madesub3.Model.ResponseMovies;
import com.rais.haidar.madesub3.Model.ResponseTvSeries;
import com.rais.haidar.madesub3.Model.TvSeriesItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {
    @GET()
    Call<ResponseMovies> getMovies(@Url String url);

    @GET
    Call<ResponseTvSeries> getTvShows(@Url String url);

    @GET
    Call<MoviesItem> getMovieItem(@Url String url);

    @GET
    Call<TvSeriesItem> getTvItem(@Url String url);
}
