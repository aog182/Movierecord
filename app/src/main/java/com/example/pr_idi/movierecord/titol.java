package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        registerForContextMenu(listView);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.ic_menu_manage);
        menu.setHeaderTitle("EDITAR");
        menu.add(Menu.NONE, 0, menu.NONE, "Modificar critica");
        menu.add(Menu.NONE, 1, menu.NONE, "Esborrar");

    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                builder.setTitle("EDITAR CRITICA");

                LinearLayout layout = new LinearLayout(getActivity().getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText txtcritica = new EditText(getActivity().getApplicationContext());
               // txtcritica.setText();
                txtcritica .setInputType(InputType.TYPE_CLASS_NUMBER);
                txtcritica .setHint("Puntuació de l'1 al 5");
                layout.addView(txtcritica );

                builder.setView(layout);
                break;
            case 1:
                //TODO: codi d'esborrar la peli
                break;
        }

        return super.onContextItemSelected(item);
    }
}

