package com.example.tt.myapplication;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import com.example.tt.myapplication.adapter.AdapterBoxOffice;
import com.example.tt.myapplication.extras.Constrants;
import com.example.tt.myapplication.extras.Keys;
import com.example.tt.myapplication.extras.MovieSorter;
import com.example.tt.myapplication.extras.SortListener;
import com.example.tt.myapplication.logging.L;
import com.example.tt.myapplication.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tt.myapplication.extras.Keys.EndpointsBoxOffice.*;
import com.example.tt.myapplication.pojo.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SortListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOIVES = "state_movies" ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String myKey = "54wzfswsa4qmjg8hjwa64d4c";
    private static final String URL_BOX_OFFICE ="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private ArrayList<Movie> listMoives = new ArrayList<>();
    private ArrayList<String> lists = new ArrayList<>();
    private SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMoiveHits;
    private AdapterBoxOffice adapterBoxOffice;
    private TextView textVolley;
    //swiperefreshlayout
    private SwipeRefreshLayout swipeRefreshLayout;
    MovieSorter movieSorter = new MovieSorter();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOIVES,listMoives);
    }

    public static String getRequestUrl(int limit) {
         return URL_BOX_OFFICE + "?apikey=" + MyApplication.myKey + "&limit=" + limit;

    }
    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volleySingleton = VolleySingleton.getsIntance();
        requestQueue = volleySingleton.getmRequestQueue();

       // sendJsonObject();

    }

    private void sendJsonObject() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(30),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //L.t(getActivity(),response.toString());
                        try {
                            textVolley.setVisibility(View.GONE);
                           listMoives = parseJsonRequest(response);
                            adapterBoxOffice.setListmovies(listMoives);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // L.t(getActivity(),error.getMessage());
            handleVolleyError(error);

            }
        });
        requestQueue.add(request);
    }

    public void handleVolleyError(VolleyError error) {
        textVolley.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                textVolley.setText("error Timeout");
        }else if (error instanceof AuthFailureError) {

        }else if (error instanceof ServerError) {

        }else if (error instanceof NetworkError) {
            textVolley.setText("Connect timeout");
        }else if (error instanceof ParseError) {

        }
    }
    private ArrayList<Movie> parseJsonRequest(JSONObject response) throws JSONException {
        ArrayList<Movie> listMoive = new ArrayList<>();
        if (response == null || response.length() == 0) {
            //return;
        }
        long id = -1;
        String title = Constrants.NA;
        String releaseDate = Constrants.NA;
        int audienceScore = -1;
        String synopsis = Constrants.NA;
        String urlThumbnail = Constrants.NA;
        try {
            StringBuilder data = new StringBuilder();
            JSONArray arrayMoives = response.getJSONArray(Keys.EndpointsBoxOffice.KEY_MOVIES);
            for (int i = 0; i < arrayMoives.length(); i++) {

                JSONObject currMovie = arrayMoives.getJSONObject(i);
                //get the id of the current movie

                if (currMovie.has(Keys.EndpointsBoxOffice.KEY_ID) &&
                        !currMovie.isNull(Keys.EndpointsBoxOffice.KEY_ID)) {

                    id = currMovie.getLong(Keys.EndpointsBoxOffice.KEY_ID);
                }
                //get the title of the current movie
                if(currMovie.has(Keys.EndpointsBoxOffice.KEY_TITLE)&&
                        !currMovie.isNull(Keys.EndpointsBoxOffice.KEY_TITLE)) {

                    title = currMovie.getString(Keys.EndpointsBoxOffice.KEY_TITLE);
                }
                //get the date  in threaters for the current moive
                if (currMovie.has(Keys.EndpointsBoxOffice.KEY_RELEASE_DATES)&&
                        !currMovie.isNull(Keys.EndpointsBoxOffice.KEY_RELEASE_DATES)) {

                    JSONObject objReleaseDate = currMovie.getJSONObject(Keys.EndpointsBoxOffice.KEY_RELEASE_DATES);
                    if (objReleaseDate!=null &&
                            objReleaseDate.has(Keys.EndpointsBoxOffice.KEY_THEATER)&&
                            !objReleaseDate.isNull(Keys.EndpointsBoxOffice.KEY_THEATER)) {

                        releaseDate = objReleaseDate.getString(Keys.EndpointsBoxOffice.KEY_THEATER);
                    }
                }
                //get the audience Score for the current moive

                JSONObject objRating = currMovie.getJSONObject(Keys.EndpointsBoxOffice.KEY_RATINGS);

                if (objRating!=null&&
                        objRating.has(Keys.EndpointsBoxOffice.KEY_AUDIENCE_SCORE)&&
                        !objRating.isNull(Keys.EndpointsBoxOffice.KEY_AUDIENCE_SCORE)) {

                    audienceScore = objRating.getInt(Keys.EndpointsBoxOffice.KEY_AUDIENCE_SCORE);
                }
                //get the synopsis of the current movie
                if(currMovie.has(Keys.EndpointsBoxOffice.KEY_SYNOPSIS)&&
                        !currMovie.isNull(Keys.EndpointsBoxOffice.KEY_SYNOPSIS)){

                    synopsis = currMovie.getString(Keys.EndpointsBoxOffice.KEY_SYNOPSIS);
                }


                //get the poster of the current moive
                JSONObject objPoster = currMovie.getJSONObject(Keys.EndpointsBoxOffice.KEY_POSTERS);

                if (objPoster!=null
                        && objPoster.has(Keys.EndpointsBoxOffice.KEY_THUMBNAIL)
                        && !objPoster.isNull(Keys.EndpointsBoxOffice.KEY_THUMBNAIL)) {

                    urlThumbnail = objPoster.getString(Keys.EndpointsBoxOffice.KEY_THUMBNAIL);
                }

                Movie movie = new Movie();
                movie.setId(id);
                movie.setTitle(title);
                movie.setAudienceScore(audienceScore);
                movie.setUrlThumbnail(urlThumbnail);
                Date date = spf.parse(releaseDate);
                movie.setReleaseDateTheater(date);
                movie.setSynopsis(synopsis);
                if (id != -1 && !title.equals(Constrants.NA)) {
                    listMoive.add(movie);
                }


                // data.append("id: "+ id + " title: "+title +" release date: "+ releaseDate+ " AudienceScore: "+audienceScore  +"\n");
            }
           // L.t(getActivity(), listMoive.size()+" were fetched");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listMoive;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        textVolley = (TextView) view.findViewById(R.id.textVolley);
        listMoiveHits = (RecyclerView) view.findViewById(R.id.listMoives);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeMovieHits);
        swipeRefreshLayout.setOnRefreshListener(this);
        listMoiveHits.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMoiveHits.setAdapter(adapterBoxOffice);
        if (savedInstanceState != null) {
            listMoives = savedInstanceState.getParcelableArrayList(STATE_MOIVES);
            adapterBoxOffice.setListmovies(listMoives);
           // L.t(getActivity(),"check");
        }else{

            sendJsonObject();
            L.t(getActivity(),listMoives.size()+ " were fill");
        }

        return view;
    }


    @Override
    public void onSortByName() {
       // L.t(getActivity(),"sort name boxoffice");
        movieSorter.sortMovieByName(listMoives);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByDate() {
        movieSorter.sortMovieByDate(listMoives);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByRating() {
        L.t(getActivity(),"sort rating boxoffice");
        movieSorter.sortMoiveByRating(listMoives);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        L.t(getActivity(),"onRefresh");
    }
}
