package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.view.ContextThemeWrapper;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String title = values.get(info.position).getTitle();
        menu.setHeaderIcon(R.drawable.ic_menu_manage);
        menu.setHeaderTitle("EDITAR " + title);
        menu.add(Menu.NONE, 0, menu.NONE, "Modificar crítica");
        menu.add(Menu.NONE, 1, menu.NONE, "Esborrar");


    }

    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String title = values.get(info.position).getTitle();
        //busquem la peli amb el titol nompelieditar dins de values, pq la posicio de adaper pot ser diferent de la de values
        String nomPeli = listViewAdapter.getItem(info.position);
        int i = 0;
        while(i < values.size()){
            if (Objects.equals(values.get(i).getTitle(), nomPeli)) film2 = values.get(i);
            ++i;
        }
        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(myView.getContext(),R.style.AlertDialogCustom));
                builder.setTitle("CRÍTICA DE '" + title+"'");

                LinearLayout layout = new LinearLayout(getActivity().getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText txtcritica = new EditText(getActivity().getApplicationContext());
                txtcritica.setInputType(InputType.TYPE_CLASS_NUMBER);
                txtcritica.setText(String.valueOf(film2.getCritics_rate()));
                txtcritica.setTextColor(Color.BLACK);
                txtcritica.setHint("Puntuació de l'1 al 5");
                layout.addView(txtcritica);

                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int puntuacio_Text = Integer.parseInt(txtcritica.getText().toString());
                        int result = filmData.updateFilm(film2,puntuacio_Text);
                        if (result == 1) Toast.makeText(getActivity().getApplicationContext(), film2.getTitle() + " editada correctament",Toast.LENGTH_LONG).show();
                        else Toast.makeText(getActivity().getApplicationContext(), "ERROR AL EDITAR",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        datainicial();
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
                builder2.setMessage("ESBORRAR ' " + title+"'?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                filmData.deleteFilm(film2);
                                Toast.makeText(getActivity().getApplicationContext(), film2.getTitle() + " esborrada correctament",Toast.LENGTH_LONG).show();
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
}


/*case R.id.add:
                String[] newFilm = new String[] { "Blade Runner", "Ridley Scott", "Rocky Horror Picture Show", "Jim Sharman", "The Godfather", "Francis Ford Coppola", "Toy Story", "John Lasseter" };
                int nextInt = new Random().nextInt(4);
                // save the new film to the database
                film = filmData.createFilm(newFilm[nextInt*2], newFilm[nextInt*2 + 1]);
                adapter.add(film);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    film = (Film) getListAdapter().getItem(0);
                    filmData.deleteFilm(film);
                    adapter.remove(film);
                }
                break;
                */
