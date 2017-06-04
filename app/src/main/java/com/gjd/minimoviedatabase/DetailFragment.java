package com.gjd.minimoviedatabase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.gjd.minimoviedatabase.R.id.movie_poster;
import static com.gjd.minimoviedatabase.R.string.release_date;

/**
 * Fragment class for showing additional movie details.
 */
public class DetailFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            getActivity().getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getActivity().getWindow().setAllowEnterTransitionOverlap(true);
            getActivity().getWindow().setSharedElementEnterTransition(new ChangeImageTransform());
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        //get movie data from parcel
        Bundle bundle = getActivity().getIntent().getExtras();
        Movie movie = bundle.getParcelable(getString(R.string.movie_object));

        //set values to various views in fragment
        if (movie != null) {
            getActivity().setTitle(movie.title);

            TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
            releaseDate.setText(getString(release_date) + "\n" + movie.releaseDate);

            TextView plot = (TextView) rootView.findViewById(R.id.movie_plot);
            plot.setText(getString(R.string.overview) + "\n\n" + movie.plot);

            TextView user_rating = (TextView) rootView.findViewById(R.id.movie_rating);
            user_rating.setText(getString(R.string.user_rating) + "\n" + Double.toString(movie.userRating));

            //asynchronous loading of image
            ImageView moviePoster = (ImageView) rootView.findViewById(movie_poster);
            Picasso.with(getContext()).load(movie.posterPath).into(moviePoster);
            moviePoster.setContentDescription(movie.title);
        }

        return rootView;
    }
}
