package com.example.tt.myapplication.callbacks;

import com.example.tt.myapplication.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by TT
 */
public interface BoxOfficeMoviesLoadedListener {
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies);
}
