package com.example.pr_idi.movierecord;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Miquel on 28/12/2016.
 */

public class actor extends Fragment {
    View myView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.actor,container,false);
        Log.v("5", String.valueOf("estem a actor"));
        return myView;
    }
}
