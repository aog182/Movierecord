package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miquel on 28/12/2016.
 */

public class anyestrena extends Fragment {
    View myView;

    private List<Film> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private FilmData filmData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.anyestrena,container,false);

        filmData = new FilmData(getActivity().getApplicationContext()); //context de l'activitat superior
        filmData.open();
        movieList = filmData.getAllFilmsYear();

        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);
        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return myView;
    }

    public void refresh(){
        movieList = filmData.getAllFilmsYear();
        mAdapter.notifyDataSetChanged();
    }


}
