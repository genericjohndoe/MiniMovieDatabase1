package com.gjd.minimoviedatabase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Fragment class for showing additional movie details.
 */
public class DetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate ViewGroup
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        //get movie data from parcel
        Bundle bundle = getActivity().getIntent().getExtras();
        Movie movie = bundle.getParcelable("movie object");

        //set values to various views in fragment
        TextView title = (TextView) rootView.findViewById(R.id.movie_title);
        title.setText(movie.title);
        TextView release_date = (TextView) rootView.findViewById(R.id.movie_release_date);
        release_date.setText("Release Date: " + movie.releaseDate);
        TextView plot = (TextView) rootView.findViewById(R.id.movie_plot);
        plot.setText("Overview: \n" + movie.plot);
        TextView user_rating = (TextView) rootView.findViewById(R.id.movie_rating);
        user_rating.setText("Average User Rating: " + Double.toString(movie.user_rating));
        ImageView movie_poster = (ImageView) rootView.findViewById(R.id.movie_poster);
        //asynchronous loading of image
        Picasso.with(getContext()).load(movie.posterpath).into(movie_poster);

        return rootView;
    }
}
