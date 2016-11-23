package com.gjd.minimoviedatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing info for a number of movies
 */
public class MainActivityFragment extends Fragment {
    //ImageAdapter object was declared static to facilitate easy data transfer from AsyncTask (FetchMovieInfo)
    static ImageAdapter URLAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URLAdapter = new ImageAdapter(getContext(), new ArrayList<Movie>());

        //Inflate ViewGroup
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Get a reference to the GridView, as defined in the ViewGroup, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        //when info in the adapter changes,info on screen changes
        gridView.setAdapter(URLAdapter);
        //onClickListener responds to touch on specific item in GridView
        //send relevant movie info to DetailActivity
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = URLAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("movie object", movie);
                startActivity(intent);
            }
        });
        return rootView;
    }
    private void updateSort() {
        //this method is used to retrieve movie data to show on UI
        FetchMovieInfo movieTask = new FetchMovieInfo();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String prefString = prefs.getString(getString(R.string.Sort_pref),
                getString(R.string.pref_popularity));

        movieTask.execute((prefString.equals(R.string.pref_popularity)) ?
                getString(R.string.sort_popular) : getString(R.string.sort_top_rated));
    }

    @Override
    public void onStart() {
        super.onStart();
        updateSort();
    }


}
