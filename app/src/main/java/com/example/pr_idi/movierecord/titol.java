package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miquel on 28/12/2016.
 */

public class titol extends Fragment {
    View myView;
    private FilmData filmData;
    Film film;
    ListView listView; //llista que somplira amb la query
    EditText editText;//quadre de text de la cerca
    ArrayAdapter<String> listViewAdapter; //Adaptador de la listview
    List<Film> values = new ArrayList<>(); //lista de valors que posem a la listview
    List<String> titols = new ArrayList<>();
    List<String> pelis = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.titol,container,false);
        Log.v("1", String.valueOf("estem a titol"));

        listView = (ListView) myView.findViewById(R.id.listtitol);
        editText = (EditText) myView.findViewById(R.id.txtsearch);

        filmData = new FilmData(getActivity().getApplicationContext()); //context de l'activitat superior
        filmData.open();

        datainicial();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    //totes les pelis sense filtre
                    datainicial();
                    //listViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                buscarelement(s.toString());

            }
        });

        return myView;
    }

    public void buscarelement(String textabuscar){
        int i = 0;
        String nomactor;
        while(i < values.size()) {
            nomactor =  values.get(i).getProtagonist();
            film = values.get(i);
            if (!nomactor.contains(textabuscar)) values.remove(film);
            ++i;
        }
        nomestitols();
        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        filmData.close();
        super.onPause();
    }

    void datainicial(){
        values = filmData.getAllFilmstitol();
        nomestitols();
        listViewAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                R.layout.list_item,R.id.txtitem, titols);

        listView.setAdapter(listViewAdapter);
    }

    void nomestitols(){
        titols.clear();
        int i = 0;
        String titolpeli;
        while(i < values.size()){
            titolpeli = values.get(i).getTitle();
            titols.add(i,titolpeli);
            ++i;
        }
    }

}
