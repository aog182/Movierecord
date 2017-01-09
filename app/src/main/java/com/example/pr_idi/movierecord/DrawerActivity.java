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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filmData = new FilmData(getApplicationContext());
        filmData.open();


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment newFragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (id == R.id.nav_titol) {
           // Handle the camera action
           // fragmentManager.beginTransaction().replace(R.id.content_frame,new titol());
            newFragment = new titol();
            transaction.replace(R.id.content_frame, newFragment);
            transaction.addToBackStack("TITOL");
            transaction.commit();


        } else if (id == R.id.nav_anyestrena) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame,new anyestrena());
                 newFragment = new anyestrena();
                 transaction.replace(R.id.content_frame, newFragment);
                 transaction.addToBackStack("ANY");
                 transaction.commit();
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


}
