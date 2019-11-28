package com.rais.haidar.madesub3.Model;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.rais.haidar.madesub3.Network.ApiInterface;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private  final String BASE_URL = "https://api.themoviedb.org/3/discover/";
    private String API_KEY = "df5a1337aa04bd78af8e62f7586cf6bf";
    private final String language = ""+ Locale.getDefault().getDisplayCountry();

    private MutableLiveData<ArrayList<TvSeriesItem>> listMutableTvShows = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MoviesItem>> listMutableMovies = new MutableLiveData<>();

    public void setListTvShows(final View view) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ResponseTvSeries> tvShowsCall = apiInterface.getTvShows("tv?api_key="+API_KEY+"&language="+language);
        tvShowsCall.enqueue(new Callback<ResponseTvSeries>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTvSeries> call, @NonNull Response<ResponseTvSeries> response) {
                Log.d("Retrofit result", "code: "+response.code());

                if (response.code() != 200) {
                    Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG);
                }

                if (response.body() != null) {
                    ArrayList<TvSeriesItem> tvSeriesItems = response.body().getResults();
                    listMutableTvShows.postValue(tvSeriesItems);
                }
            }

            @Override
            public void onFailure(Call<ResponseTvSeries> call, Throwable t) {
                Log.e("Retrofit result tv", t.toString());
            }

        });
    }

    public void setListMovies(final View view) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ResponseMovies> moviesCall = apiInterface.getMovies("movie?api_key="+API_KEY+"&language="+language);
        moviesCall.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovies> call, @NonNull Response<ResponseMovies> response) {
                Log.d("Retrofit result", "code: "+response.code());

                if (response.code() != 200) {
                    Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG);
                }
                if (response.body() != null) {
                    ArrayList<MoviesItem> moviesItemList= response.body().getResults();
                    listMutableMovies.postValue(moviesItemList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMovies> call, @NonNull Throwable t) {
                Log.e("Retrofit Result mov", t.toString());
            }
        });
    }

    public LiveData<ArrayList<TvSeriesItem>> getListsTvShows() {
        return listMutableTvShows;
    }

    public LiveData<ArrayList<MoviesItem>> getListsMovies() {
        return listMutableMovies;
    }
}
