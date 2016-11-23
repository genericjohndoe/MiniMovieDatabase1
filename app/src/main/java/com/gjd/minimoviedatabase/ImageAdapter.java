package com.gjd.minimoviedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Class is responsible for generating the poster images shown in GridView
 */
public class ImageAdapter extends ArrayAdapter<Movie> {
    Context context;
    List<Movie> Movie;

    public ImageAdapter(Context context, List<Movie> Movie){
        super(context, 0,Movie);
        this.context = context;
        this.Movie = Movie;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Places the Movie object from the Adapter at the appropriate position
        // Movie poster is shown
        Movie movie = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.poster_image_view, parent, false);
        }

        ImageView posterImg = (ImageView) convertView.findViewById(R.id.poster);


        Picasso.with(context).load(movie.posterpath).into(posterImg);

        return convertView;
    }
}
