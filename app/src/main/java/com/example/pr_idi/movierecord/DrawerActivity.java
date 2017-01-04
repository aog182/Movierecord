package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FilmData filmData;
    private String titol_Text = "";
    private String pais_Text = "";
    private int any_Text;
    private String director_Text = "";
    private String prota_Text = "";
    private int puntuacio_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filmData = new FilmData(getApplicationContext());
        filmData.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAfegirPeli();
                refreshfrag();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //------------------Posem ordre de titol per defecte--------
        setFragment(new titol());

    }


    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    void refreshfrag(){

        titol fragment = (titol) getFragmentManager().findFragmentByTag("TITOL");
        if (fragment != null){
            Log.v("9", "trobem titol");
            fragment.datainicial();
        }

        anyestrena fragment2 = (anyestrena) getFragmentManager().findFragmentByTag("ANY");
        if (fragment2 != null){
            Log.v("9", "trobem anyestrena");
            fragment2.refresh();
        }

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        Fragment newFragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

             if (id == R.id.nav_titol) {
           // Handle the camera action
           // fragmentManager.beginTransaction().replace(R.id.content_frame,new titol());
            newFragment = new titol();
            transaction.replace(R.id.content_frame, newFragment);
            transaction.addToBackStack("TITOL");
            transaction.commit();
            Log.v("1", String.valueOf("apretem titol"));

        } else if (id == R.id.nav_anyestrena) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame,new anyestrena());
                 newFragment = new anyestrena();
                 transaction.replace(R.id.content_frame, newFragment);
                 transaction.addToBackStack("ANY");
                 transaction.commit();
            Log.v("3", String.valueOf("apretem any"));
        } else if (id == R.id.nav_about) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame,new about());
                 newFragment = new about();
                 transaction.replace(R.id.content_frame, newFragment);
                 transaction.addToBackStack("ABOUT");
                 transaction.commit();
        } else if (id == R.id.nav_help) {
           // fragmentManager.beginTransaction().replace(R.id.content_frame,new help());
                 newFragment = new help();
                 transaction.replace(R.id.content_frame, newFragment);
                 transaction.addToBackStack("HELP");
                 transaction.commit();
             }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void dialogAfegirPeli(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Afegir peli");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("*Títol");
        layout.addView(input);

        final EditText input2 = new EditText(this);
        input2.setInputType(InputType.TYPE_CLASS_TEXT);
        input2.setHint("*País");
        layout.addView(input2);

        final EditText input3 = new EditText(this);
        input3.setInputType(InputType.TYPE_CLASS_NUMBER);
        input3.setHint("*Any d'estrena");
        layout.addView(input3);

        final EditText input4 = new EditText(this);
        input4.setInputType(InputType.TYPE_CLASS_TEXT);
        input4.setHint("*Director");
        layout.addView(input4);

        final EditText input5 = new EditText(this);
        input5.setInputType(InputType.TYPE_CLASS_TEXT);
        input5.setHint("*Actor protagonista");
        layout.addView(input5);

        final EditText input6 = new EditText(this);
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
                any_Text = Integer.parseInt(input3.getText().toString());
                director_Text = input4.getText().toString();
                prota_Text = input5.getText().toString();
                puntuacio_Text = Integer.parseInt(input6.getText().toString());

                filmData.createFilm(titol_Text,director_Text,pais_Text,prota_Text,any_Text,puntuacio_Text);
                Toast.makeText(getApplicationContext(), "'"+titol_Text+"'" + " afegida correctament",Toast.LENGTH_LONG).show();
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
