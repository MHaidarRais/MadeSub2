package com.rais.haidar.madesub3.Model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rais.haidar.madesub3.DetailActivity;
import com.rais.haidar.madesub3.Network.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailViewModel extends ViewModel {

    private final String BASE_URL = "https://api.themoviedb.org/3/discover/";
    private final String API_KEY = "df5a1337aa04bd78af8e62f7586cf6bf";
    private final String language = ""+ Locale.getDefault().getLanguage();

    private final MutableLiveData<TvSeriesItem> mutableTvSeries = new MutableLiveData<>();
    private final MutableLiveData<MoviesItem> mutableMovies = new MutableLiveData<>();

    public void getMovieData(final String movieId, final View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ResponseMovies> itemCall = apiInterface.getMovies("movie?api_key="+API_KEY+"&language="+language);
        itemCall.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovies> call, @NonNull Response<ResponseMovies> response) {
//                Log.d("Retrofit result", "code: "+response.body());
                Log.d("ID MOVIE RESULT", "onResponse:"+movieId);
                ResponseMovies moviesItem = response.body();

                if (response.code() != 200) {
                    Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG);
                }

                ArrayList<MoviesItem> items = response.body().getResults();
//                Log.d("dataaaaa", items.toString());
                for (int i = 0; i < items.size(); i++){
                    MoviesItem item = items.get(i);
                    Log.e(getClass().getSimpleName(), String.valueOf(item.getId()));
                    if (movieId.equals(String.valueOf(item.getId()))) {
                        Log.e("statusRes", "true");
                        mutableMovies.postValue(item);
                    } else {
                        Log.e("statusRes", "false");
                        Log.e(getClass().getSimpleName(), String.valueOf(item.getId()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseMovies> call, @NonNull Throwable t) {
                Log.d("Retrofit movdetail", t.getMessage());
            }
        });
    }

    public void getTvData(final String movieId, final View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ResponseTvSeries> itemCall = apiInterface.getTvShows("tv?api_key="+API_KEY+"&language="+language);
        itemCall.enqueue(new Callback<ResponseTvSeries>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTvSeries> call, @NonNull Response<ResponseTvSeries> response) {
                Log.d("Retrofit result", "code: "+response.message());

                ResponseTvSeries tvSeriesItem = response.body();

                if (response.code() != 200) {
                    Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG);
                }

                ArrayList<TvSeriesItem> items = response.body().getResults();
//                Log.d("dataaaaa", items.toString());
                for (int i = 0; i < items.size(); i++){
                    TvSeriesItem item = items.get(i);
                    Log.e(getClass().getSimpleName(), String.valueOf(item.getId()));
                    if (movieId.equals(String.valueOf(item.getId()))) {
                        Log.e("statusRes", "true");
                        mutableTvSeries.postValue(item);
                    } else {
                        Log.e("statusRes", "false");
                        Log.e(getClass().getSimpleName(), String.valueOf(item.getId()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTvSeries> call, Throwable t) {
                Log.d("Retrofit tvdetail", t.getMessage());
            }

        });
    }

    public LiveData<MoviesItem> getMovies() {
        return  mutableMovies;
    }

    public LiveData<TvSeriesItem> getTvSeries() {
        return mutableTvSeries;
    }
}
