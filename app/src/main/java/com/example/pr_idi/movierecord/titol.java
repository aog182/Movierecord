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
    ArrayAdapter<Film> listViewAdapter; //Adaptador de la listview
    List<Film> values; //lista de valors que posem a la listview


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
        String item;
        while(i < values.size()) {
            item = values.get(i).getProtagonist();
            if (!item.contains(textabuscar)) values.remove(item);
            ++i;
        }
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
        listViewAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                R.layout.list_item,R.id.txtitem, values);

        listView.setAdapter(listViewAdapter);
    }

}
