package com.rais.haidar.madesub3.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rais.haidar.madesub3.Adapter.TVSeriesAdapter;
import com.rais.haidar.madesub3.Model.MainViewModel;
import com.rais.haidar.madesub3.Model.TvSeriesItem;
import com.rais.haidar.madesub3.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVSeriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    private TVSeriesAdapter adapter;
    private MainViewModel mainViewModel;

    public TVSeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmentview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel .getListsTvShows().observe(this,getData);
        mainViewModel.setListTvShows(view);

        recyclerView = view.findViewById(R.id.list_film);
        progressBar = view.findViewById(R.id.progress_film);
        refreshLayout = view.findViewById(R.id.refresh_film);

        adapter = new TVSeriesAdapter(getContext());

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                mainViewModel.setListTvShows(view);
                mainViewModel.getListsTvShows().observe(TVSeriesFragment.this, getData);
            }
        });

        rcViewSetup();
        showLoading(true);
    }

    private void showLoading(Boolean bool) {
        if (bool) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void rcViewSetup() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private Observer<ArrayList<TvSeriesItem>> getData = new Observer<ArrayList<TvSeriesItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvSeriesItem> tvSeriesItems) {
            adapter.setTVList(tvSeriesItems);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            showLoading(false);
            refreshLayout.setRefreshing(false);
        }
    };
}
