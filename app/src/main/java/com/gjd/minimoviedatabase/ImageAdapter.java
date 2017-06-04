package com.gjd.minimoviedatabase;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Class is responsible for generating the poster images shown in RecyclerView
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MovieViewHolder> {
    private final Context mContext;
    private List<Movie> movieList;
    //private Activity activity;



    ImageAdapter(Context context) {
        mContext = context;
    }

    /**
     *
     * @param movies is a list to be shown in UI
     * UI updated by notifyDataSetChanged()
     */
    void setMovieList(List<Movie> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView poster;

        public MovieViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.poster);
            view.setOnClickListener(this);
            view.setTag(this);
        }

        @Override
        public void onClick(View view) {

            Movie movie = movieList.get(getAdapterPosition());
            Intent intent = new Intent(mContext, DetailActivity.class)
                    .putExtra(mContext.getString(R.string.movie_object), movie);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                poster.setTransitionName(mContext.getString(R.string.transition_image));
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                        new Pair(poster, poster.getTransitionName())
                ).toBundle());
            }else {
                mContext.startActivity(intent);
            }
        }
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.poster_image_view, null);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        Movie movie = movieList.get(position);
        Picasso.with(mContext)
                .load(movie.posterPath)
                .into(viewHolder.poster);
        viewHolder.poster.setContentDescription(movie.title);
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        } else {
            return 0;
        }
    }
}



