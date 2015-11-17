package com.example.tt.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tt.myapplication.adapter.AdapterUpComing;
import com.example.tt.myapplication.extras.Keys;
import com.example.tt.myapplication.extras.SortListener;
import com.example.tt.myapplication.logging.L;
import com.example.tt.myapplication.network.VolleySingleton;
import com.example.tt.myapplication.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUpComing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUpComing extends Fragment implements SortListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView listUpComing;

    ArrayList<Movie> listMoviess ;
    VolleySingleton volleySingleton ;
    RequestQueue requestQueue ;
    AdapterUpComing adap;

    public static final String myKey = "54wzfswsa4qmjg8hjwa64d4c";
    private static final String URL_BOX_OFFICE ="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUpComing.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUpComing newInstance(String param1, String param2) {
        FragmentUpComing fragment = new FragmentUpComing();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentUpComing() {
        // Required empty public constructor
    }

    public String getUrlRequest(int limit) {
        return URL_BOX_OFFICE + "?apikey=" + myKey + "&limit=" + limit;
    }
    public void sendJSonObject() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getUrlRequest(10), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
             listMoviess =   parseJsonObject(response);
               adap.setMovies(listMoviess);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.m(error.toString());

            }
        });
        requestQueue.add(request);

    }

    private ArrayList<Movie> parseJsonObject(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<>();
        try {

            JSONArray listmovie = response.getJSONArray(Keys.EndpointsBoxOffice.KEY_MOVIES);
            for (int i = 0; i < listmovie.length(); i++) {
                JSONObject curMovie = listmovie.getJSONObject(i);
                //get id
                long id = curMovie.getLong(Keys.EndpointsBoxOffice.KEY_ID);
                //get title
                String title = curMovie.getString(Keys.EndpointsBoxOffice.KEY_TITLE);
                //get releaseDate
                JSONObject objReleaseDate = curMovie.getJSONObject(Keys.EndpointsBoxOffice.KEY_RELEASE_DATES);
                String releaseDate = null;
                if (objReleaseDate.has(Keys.EndpointsBoxOffice.KEY_THEATER)) {
                    releaseDate = objReleaseDate.getString(Keys.EndpointsBoxOffice.KEY_THEATER);
                }
                //get rating
                JSONObject objRating = curMovie.getJSONObject(Keys.EndpointsBoxOffice.KEY_RATINGS);
                String rating = "";
                if (objRating.has(Keys.EndpointsBoxOffice.KEY_AUDIENCE_SCORE)) {
                    rating = objRating.getString(Keys.EndpointsBoxOffice.KEY_AUDIENCE_SCORE);
                }
                //get thumbnail
                String thumbnail="";
                JSONObject objThumbnail = curMovie.getJSONObject(Keys.EndpointsBoxOffice.KEY_POSTERS);
                if (objThumbnail.has(Keys.EndpointsBoxOffice.KEY_THUMBNAIL)) {
                    thumbnail = objThumbnail.getString(Keys.EndpointsBoxOffice.KEY_THUMBNAIL);
                }
               Movie mv = new Movie();
                mv.setId(id);
                mv.setTitle(title);
                try {
                    mv.setReleaseDateTheater(new  SimpleDateFormat("yyyy-MM-dd").parse(releaseDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mv.setUrlThumbnail(thumbnail);
                mv.setAudienceScore(Integer.parseInt(rating));
                listMovies.add(mv);

               // L.m(mv.toString());


            }
           // L.t(getActivity(), listmovie.toString());
        } catch (JSONException e) {
           L.m(e.getMessage());
        }
        return listMovies;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        listMoviess = new ArrayList<>();
        volleySingleton = VolleySingleton.getsIntance();
        requestQueue = volleySingleton.getmRequestQueue();
        sendJSonObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        listUpComing = (RecyclerView) view.findViewById(R.id.listUpComing);
        listUpComing.setLayoutManager(new LinearLayoutManager(getActivity()));
        adap = new AdapterUpComing(getActivity());
        listUpComing.setAdapter(adap);
        return view;
    }


    @Override
    public void onSortByName() {
        L.t(getActivity(),"sort name Upcoming");
    }

    @Override
    public void onSortByDate() {

    }

    @Override
    public void onSortByRating() {

    }
}
