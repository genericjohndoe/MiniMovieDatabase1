package com.gjd.minimoviedatabase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for Movie ojec creation
 */
public class Movie implements Parcelable {
    String title;
    String plot;
    String posterpath;
    String releaseDate;
    double user_rating;

    public Movie(){}

    public Movie(String title, String plot, String posterpath, String releaseDate, double user_rating){
        this.title = title;
        this.plot = plot;
        this.posterpath = posterpath;
        this.releaseDate = releaseDate;
        this.user_rating = user_rating;
    }

    private Movie(Parcel in){
        title = in.readString();
        plot = in.readString();
        posterpath = in.readString();
        releaseDate = in.readString();
        user_rating = in.readDouble();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() { return title + " -- " + plot + " -- " + posterpath + " -- " + releaseDate + " -- " +user_rating; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(plot);
        parcel.writeString(posterpath);
        parcel.writeString(releaseDate);
        parcel.writeDouble(user_rating);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };
}
