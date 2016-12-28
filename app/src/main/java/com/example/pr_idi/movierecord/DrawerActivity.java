package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            transaction.addToBackStack(null);
            transaction.commit();
            Log.v("1", String.valueOf("apretem titol"));
        } else if (id == R.id.nav_actor) {
            //fragmentManager.beginTransaction().replace(R.id.content_drawer,new titol());
                 newFragment = new actor();
                 transaction.replace(R.id.content_frame, newFragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
            Log.v("2", String.valueOf("apretem actor"));
        } else if (id == R.id.nav_anyestrena) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame,new anyestrena());
                 newFragment = new anyestrena();
                 transaction.replace(R.id.content_frame, newFragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
            Log.v("3", String.valueOf("apretem any"));
        } else if (id == R.id.nav_about) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame,new about());
                 newFragment = new about();
                 transaction.replace(R.id.content_frame, newFragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
        } else if (id == R.id.nav_help) {
           // fragmentManager.beginTransaction().replace(R.id.content_frame,new help());
                 newFragment = new help();
                 transaction.replace(R.id.content_frame, newFragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
             }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}