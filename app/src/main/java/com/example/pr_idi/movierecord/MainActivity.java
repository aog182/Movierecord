package com.example.pr_idi.movierecord;


import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
    private FilmData filmData;
    Film film;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.list_content, values);
        setListAdapter(adapter);
    }
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        film = (Film) getListAdapter().getItem(0);


            menu.setHeaderTitle(film.getTitle());
            menu.add(Menu.NONE, 1, 1, "Modificar");
            menu.add(Menu.NONE, 2, 2, "Esborrar");
            //pos = info.position;
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) getListAdapter();
        int menuItemIndex = item.getItemId();
        if (menuItemIndex == 1) { //modificar dades film

        }
        else if (menuItemIndex == 2) { //esborrar element
            filmData.deleteFilm(film);
            adapter.remove(film);
        }

        adapter.notifyDataSetChanged();
        return true;
    }



    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) getListAdapter();
        Film film;
        switch (view.getId()) {
            case R.id.add:
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
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

}