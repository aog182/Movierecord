package com.example.pr_idi.movierecord;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Arnau on 03/01/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private List<Film> movieList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, director, protagonist, country, rate;
        public RatingBar critica;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            director = (TextView) view.findViewById(R.id.director);
            country = (TextView) view.findViewById(R.id.country);
            protagonist = (TextView) view.findViewById(R.id.protagonist);
            year = (TextView) view.findViewById(R.id.year);
            critica = (RatingBar) view.findViewById(R.id.estrella);
        }
    }


    public MoviesAdapter(List<Film> movieList) {
        this.movieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Film movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.director.setText(movie.getDirector());
        holder.protagonist.setText(movie.getProtagonist());
        holder.country.setText(movie.getCountry());
        holder.year.setText(Integer.toString(movie.getYear()));
        holder.critica.setRating(movie.getCritics_rate());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
