package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.input;
import static android.content.ContentValues.TAG;

/**
 * Created by Miquel on 28/12/2016.
 */

public class titol extends Fragment {
    View myView;
    private FilmData filmData;
    Film film,film2;
    ListView listView; //llista que somplira amb la query
    EditText editText;//quadre de text de la cerca
    ArrayAdapter<Film> listViewAdapter; //Adaptador de la listview
    List<Film> values = new ArrayList<>(); //lista de valors que posem a la listview
    private String titol_Text = "";
    private String pais_Text = "";
    private int any_Text;
    private String director_Text = "";
    private String prota_Text = "";
    private int puntuacio_Text;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.titol,container,false);

        listView = (ListView) myView.findViewById(R.id.listtitol);
        editText = (EditText) myView.findViewById(R.id.txtsearch);

        filmData = new FilmData(getActivity()); //context de l'activitat superior
        filmData.open();

        datainicial();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.v("9", s.toString());
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

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fabtitol);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAfegirPeli();

            }
        });

        return myView;
    }

    public void buscarelement(String textabuscar){

        Log.v("9", textabuscar);
        int i = 0;
        String nomactor;
        while(i < values.size()) {
            nomactor =  values.get(i).getProtagonist();
            film = values.get(i);
            Log.v("9",nomactor.toLowerCase());
            if (!(nomactor.toLowerCase().contains(textabuscar.toLowerCase()))) values.remove(film);
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

    public void datainicial(){
        values = filmData.getAllFilmstitol();
        listViewAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item,R.id.txtitem, values);

        listView.setAdapter(listViewAdapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        film2 = listViewAdapter.getItem(info.position);
        String title = film2.getTitle();
        menu.setHeaderIcon(R.drawable.ic_menu_manage);
        menu.setHeaderTitle("EDITAR '" + title+"'");
        menu.add(Menu.NONE, 0, menu.NONE, "Modificar crítica");
        menu.add(Menu.NONE, 1, menu.NONE, "Esborrar");


    }

    public boolean onContextItemSelected(MenuItem item) {
        String title = film2.getTitle();
        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(myView.getContext(),R.style.AlertDialogCustom));
                builder.setTitle("CRÍTICA DE '" + title+"'");
              //  LayoutInflater layout = LayoutInflater.from(getActivity());

                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText num = new EditText(getActivity());

                num.setInputType(InputType.TYPE_CLASS_NUMBER);
                num.setText(String.valueOf(film2.getCritics_rate()));
              //  input6.setHint("Puntuació de l'1 al 10");
                layout.addView(num);

                /*final View vista = layout.inflate(R.layout.critica,null);
                final RatingBar rater = (RatingBar) vista.findViewById(R.id.rating);
                rater.setNumStars(10);
                rater.setRating(film2.getCritics_rate());*/
                builder.setView(layout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final int puntuacio = Integer.parseInt(num.getText().toString());
                        if (puntuacio <= 0 || puntuacio > 10)
                            Toast.makeText(getActivity(), "La puntuació ha de ser entre 1 i 10", Toast.LENGTH_LONG).show();
                        else {
                            int result = filmData.updateFilm(film2, puntuacio);
                            if (result == 1)
                                Toast.makeText(getActivity(), "'"+film2.getTitle()+"'" + " editada correctament", Toast.LENGTH_LONG).show();
                            else Toast.makeText(getActivity(), "ERROR A L'EDITAR", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            datainicial();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
                break;
            case 1:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setMessage("ESBORRAR '" + title +"'?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                filmData.deleteFilm(film2);
                                Toast.makeText(getActivity(), "'"+film2.getTitle() + "' esborrada correctament",Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                datainicial();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder2.show();
                break;
        }

        return super.onContextItemSelected(item);

    }

    public void dialogAfegirPeli(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Afegir pel·lícula");

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        // Set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Títol");
        layout.addView(input);

        final EditText input2 = new EditText(getActivity());
        input2.setInputType(InputType.TYPE_CLASS_TEXT);
        input2.setHint("País");
        layout.addView(input2);

        final EditText input3 = new EditText(getActivity());
        input3.setInputType(InputType.TYPE_CLASS_NUMBER);
        input3.setHint("Any d'estrena");
        layout.addView(input3);

        final EditText input4 = new EditText(getActivity());
        input4.setInputType(InputType.TYPE_CLASS_TEXT);
        input4.setHint("Director");
        layout.addView(input4);

        final EditText input5 = new EditText(getActivity());
        input5.setInputType(InputType.TYPE_CLASS_TEXT);
        input5.setHint("Actor protagonista");
        layout.addView(input5);

        final EditText input6 = new EditText(getActivity());
        input6.setInputType(InputType.TYPE_CLASS_NUMBER);
        input6.setHint("Puntuació de l'1 al 10");
        layout.addView(input6);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                titol_Text = input.getText().toString();
                pais_Text = input2.getText().toString();
                director_Text = input4.getText().toString();
                prota_Text = input5.getText().toString();
                if (titol_Text.isEmpty() || input3.getText().toString().isEmpty() || pais_Text.isEmpty() || director_Text.isEmpty() || prota_Text.isEmpty()) {
                    Toast.makeText(getActivity(), "S'han d'omplir tots els camps", Toast.LENGTH_LONG).show();
                    dialogAfegirPeli();
                } else if (!input6.getText().toString().isEmpty()) {
                    if (Integer.parseInt(input6.getText().toString()) <= 0 || Integer.parseInt(input6.getText().toString()) > 10) {
                        Toast.makeText(getActivity(), "Puntuació ha de ser entre 1 i 10", Toast.LENGTH_LONG).show();
                        dialogAfegirPeli();
                    } else {
                        any_Text = Integer.parseInt(input3.getText().toString());
                        puntuacio_Text = Integer.parseInt(input6.getText().toString());
                        filmData.createFilm(titol_Text, director_Text, pais_Text, prota_Text, any_Text, puntuacio_Text);
                        Toast.makeText(getActivity(), "'" + titol_Text + "'" + " afegida correctament", Toast.LENGTH_LONG).show();
                        datainicial();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



}