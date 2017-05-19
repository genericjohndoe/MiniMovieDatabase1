package com.gjd.minimoviedatabase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for Movie object creation
 */
public class Movie implements Parcelable {
    String title;
    String plot;
    String posterPath;
    String releaseDate;
    double userRating;

    public Movie(){}

    private Movie(Parcel in){
        title = in.readString();
        plot = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        userRating = in.readDouble();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() { return title + " -- " + plot + " -- " + posterPath + " -- " + releaseDate + " -- " +userRating; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(plot);
        parcel.writeString(posterPath);
        parcel.writeString(releaseDate);
        parcel.writeDouble(userRating);
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
