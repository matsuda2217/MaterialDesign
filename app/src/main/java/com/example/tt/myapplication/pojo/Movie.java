package com.example.tt.myapplication.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tt.myapplication.logging.L;

import java.util.Date;

/**
 * Created by TT
 */
public class Movie implements Parcelable{
    private long id;
    private String title;
    private Date releaseDateTheater;
    private int audienceScore;
    private String synopsis;
    private String urlThumbnail;
    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;

    public Movie() {

    }

    public Movie(long id, String title, Date releaseDateTheater, int audienceScore, String synopsis
            , String urlThumbnail, String urlSelf, String urlCast, String urlReviews, String urlSimilar) {
        this.id = id;
        this.title = title;
        this.releaseDateTheater = releaseDateTheater;
        this.audienceScore = audienceScore;
        this.synopsis = synopsis;
        this.urlThumbnail = urlThumbnail;
        this.urlSelf = urlSelf;
        this.urlCast = urlCast;
        this.urlReviews = urlReviews;
        this.urlSimilar = urlSimilar;
    }

    public Movie(Parcel source) {
        id = source.readLong();
        title = source.readString();
        releaseDateTheater = new Date(source.readLong());
        audienceScore = source.readInt();
        synopsis = source.readString();
        urlThumbnail = source.readString();
        urlSelf = source.readString();
        urlCast = source.readString();
        urlReviews = source.readString();
        urlSimilar = source.readString();

    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public void setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }

    public String getUrlReviews() {
        return urlReviews;
    }

    public void setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public String getUrlSimilar() {
        return urlSimilar;
    }

    public void setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String toString() {
        return "ID: " + id +
                "Title: " + title +
                "Date: " + releaseDateTheater +
                "Synopsis: " + synopsis +
                "Score: " + audienceScore +
                "urlThumbnail:" + urlThumbnail;
    }

    @Override
    public int describeContents() {
        L.m("describe contents movie");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        L.m("write to Parcel movie");
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(releaseDateTheater.getTime());
        dest.writeInt(audienceScore);
        dest.writeString(synopsis);
        dest.writeString(urlThumbnail);
        dest.writeString(urlSelf);
        dest.writeString(urlCast);
        dest.writeString(urlReviews);
        dest.writeString(urlSimilar);//same order is required

    }
    public static final Parcelable.Creator<Movie> CREATOR =
            new Parcelable.Creator<Movie>(){

                @Override
                public Movie createFromParcel(Parcel source) {
                    L.m("Create form Parcel: Movie");
                    return new Movie(source);
                }

                @Override
                public Movie[] newArray(int size) {
                    return new Movie[size];
                }
            };


}
