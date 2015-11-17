package com.example.tt.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.tt.myapplication.R;
import com.example.tt.myapplication.anim.AnimationUtils;
import com.example.tt.myapplication.extras.Constrants;
import com.example.tt.myapplication.network.VolleySingleton;
import com.example.tt.myapplication.pojo.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by TT
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {
    private ArrayList<Movie> listmovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private int previousPosition = 0;
    public AdapterBoxOffice(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsIntance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setListmovies(ArrayList<Movie> listmovies) {
        this.listmovies = listmovies;
        notifyItemRangeChanged(0,listmovies.size());
    }
    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.custom_movie_box_office, parent, false);
        ViewHolderBoxOffice viewHolderBoxOffice = new ViewHolderBoxOffice(v);
        return viewHolderBoxOffice;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Movie currMoive = listmovies.get(position);
        holder.moiveTitle.setText(currMoive.getTitle());


        if (currMoive.getReleaseDateTheater() != null) {
            holder.movieReleaseDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(currMoive.getReleaseDateTheater()));
        } else {
            holder.movieReleaseDate.setText(Constrants.NA);
        }

        if (currMoive.getAudienceScore() == -1) {
            holder.movieRatings.setRating(0.0f);
            holder.movieRatings.setAlpha(0.5f);
        } else {
            holder.movieRatings.setRating(currMoive.getAudienceScore() / 20.0f);
            holder.movieRatings.setAlpha(1.0f);
        }
        if (position > previousPosition) {
            AnimationUtils.animate(holder,  true);
        } else {
            AnimationUtils.animate(holder, false);
        }
           previousPosition = position;
        String url = currMoive.getUrlThumbnail();
        loadImages(url, holder);

    }

    public void loadImages(String url, final ViewHolderBoxOffice holderBoxOffice) {
        if (!url.equals(Constrants.NA)) {
            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holderBoxOffice.moviveThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return listmovies.size();
    }

     public static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{
         private ImageView moviveThumbnail;
         private TextView moiveTitle;
         private TextView movieReleaseDate;
         private RatingBar movieRatings;
         public ViewHolderBoxOffice(View view) {
             super(view);
             moviveThumbnail = (ImageView) view.findViewById(R.id.movieThumbnail);
             moiveTitle = (TextView) view.findViewById(R.id.movieTitle);
             movieRatings = (RatingBar) view.findViewById(R.id.movieRatings);
             movieReleaseDate = (TextView) view.findViewById(R.id.movieReleaseDate);
         }

    }
}
