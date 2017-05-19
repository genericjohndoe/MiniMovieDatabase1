package com.gjd.minimoviedatabase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * A placeholder fragment containing info for a number of movies shown in RecyclerView
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener, MovieUpdateCallback {
    private ImageAdapter URLAdapter;
    private RecyclerView mRecyclerView;
    private Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //RecyclerView Setup
        URLAdapter = new ImageAdapter(getContext());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecyclerView.setAdapter(URLAdapter);

        //Spinner Setup
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.pref_sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return rootView;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id){
        // changing URLAdapter from static to private while passing the fragment in
        // as a parameter for the AsyncTask constructor removes memory leak problem
        FetchMovieInfo movieTask = new FetchMovieInfo(this);
        if (parent.getItemAtPosition(pos).equals(getString(R.string.pref_popularity))) {
            movieTask.execute(getString(R.string.sort_popular));
        } else {
            movieTask.execute(getString(R.string.sort_top_rated));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void movieDataLoaded(List<Movie> movies) {
        URLAdapter.setMovieList(movies);
    }
}
