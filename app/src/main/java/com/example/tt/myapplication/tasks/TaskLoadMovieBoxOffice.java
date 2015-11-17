package com.example.tt.myapplication.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.example.tt.myapplication.callbacks.BoxOfficeMoviesLoadedListener;
import com.example.tt.myapplication.network.VolleySingleton;
import com.example.tt.myapplication.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by TT
 */
public class TaskLoadMovieBoxOffice extends AsyncTask<Void,Void,ArrayList<Movie>> {
    public BoxOfficeMoviesLoadedListener boxOfficeMoviesLoadedListener;
    public VolleySingleton volleySingleton;
    public RequestQueue requestQueue;

    public TaskLoadMovieBoxOffice(BoxOfficeMoviesLoadedListener myCompenent) {
        this.boxOfficeMoviesLoadedListener = myCompenent;
        volleySingleton = VolleySingleton.getsIntance();
        requestQueue = volleySingleton.getmRequestQueue();
    }
    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
       ArrayList<Movie> listMovies;
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
    }
}
