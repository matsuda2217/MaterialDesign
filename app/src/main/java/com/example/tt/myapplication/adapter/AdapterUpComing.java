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
import com.example.tt.myapplication.network.VolleySingleton;
import com.example.tt.myapplication.pojo.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by TT
 */
public class AdapterUpComing extends RecyclerView.Adapter<AdapterUpComing.MyUpComingHolder> {
    private ArrayList<Movie> movies = new ArrayList<>();
   private LayoutInflater layoutInflater;
    ImageLoader imageLoader;
    VolleySingleton volleySingleton;

    public AdapterUpComing(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsIntance();
        imageLoader  = volleySingleton.getImageLoader();

    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyItemRangeChanged(0,movies.size());
    }

    @Override
    public MyUpComingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.custom_upcoming, parent, false);
        MyUpComingHolder holder = new MyUpComingHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyUpComingHolder holder, int position) {
       Movie curMv = movies.get(position);
        holder.title.setText(curMv.getTitle());
        holder.ratingBar.setRating(curMv.getAudienceScore() / 20.0f);
        holder.date.setText(new SimpleDateFormat("yyyy-MM-dd").format(curMv.getReleaseDateTheater()));
        imageLoader.get(curMv.getUrlThumbnail(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.thumbnail.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MyUpComingHolder extends RecyclerView.ViewHolder {
        private TextView title, date;
        private ImageView thumbnail;
        private RatingBar ratingBar;

        public MyUpComingHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.textView2);
            date = (TextView) itemView.findViewById(R.id.textView3);
            thumbnail = (ImageView) itemView.findViewById(R.id.imageView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating);

        }

    }
}
