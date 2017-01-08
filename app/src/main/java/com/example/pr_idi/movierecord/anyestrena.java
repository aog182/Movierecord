package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    private String titol_Text = "";
    private String pais_Text = "";
    private int any_Text;
    private String director_Text = "";
    private String prota_Text = "";
    private int puntuacio_Text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.anyestrena,container,false);

        filmData = new FilmData(getActivity()); //context de l'activitat superior
        filmData.open();
        movieList = filmData.getAllFilmsYear();

        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);
        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fabany);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAfegirPeli();

            }
        });

        return myView;
    }

    public void refresh(){
        movieList = filmData.getAllFilmsYear();
        mAdapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(mAdapter);
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
        input6.setHint("Puntuació de l'1 al 5");
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
                if (titol_Text.isEmpty() || input3.getText().toString().isEmpty() || pais_Text.isEmpty() || director_Text.isEmpty() || prota_Text.isEmpty() || input6.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "S'han d'omplir tots els camps", Toast.LENGTH_LONG).show();
                } else {
                    any_Text = Integer.parseInt(input3.getText().toString());
                    puntuacio_Text = Integer.parseInt(input6.getText().toString());
                    filmData.createFilm(titol_Text, director_Text, pais_Text, prota_Text, any_Text, puntuacio_Text);
                    Toast.makeText(getActivity(), "'" + titol_Text + "'" + " afegida correctament", Toast.LENGTH_LONG).show();
                    refresh();
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
