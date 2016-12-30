package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Miquel on 28/12/2016.
 */

public class titol extends Fragment {
    View myView;
    private FilmData filmData;
    Film film;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.titol,container,false);
        Log.v("1", String.valueOf("estem a titol"));

        ListView listView = (ListView) myView.findViewById(R.id.listtitol);

        filmData = new FilmData(getActivity().getApplicationContext()); //context de l'activitat superior
        filmData.open();

        List<Film> values = filmData.getAllFilmstitol();
        ArrayAdapter<Film> listViewAdapter = new ArrayAdapter<Film>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, values);

        listView.setAdapter(listViewAdapter);

        return myView;
    }


}
