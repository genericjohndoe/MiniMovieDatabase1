package com.gjd.minimoviedatabase;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class fetches data for UI thread on worker thread
 */
public class FetchMovieInfo extends AsyncTask<String, Void, List<Movie>> {

    final MovieUpdateCallback movieUpdateCallback;

    public FetchMovieInfo(MovieUpdateCallback movieUpdateCallback) {
        this.movieUpdateCallback = movieUpdateCallback;
    }
    /**
     *
     * @param JsonString is data from http request
     * @return a list of Movie objects to be presented in UI
     * @throws JSONException error may occur when converting string to JSON object
     */
    private List<Movie> getMovieInfoFromJSON(String JsonString)
            throws JSONException {

        List<Movie> MovieList = new ArrayList<>();
        if (!JsonString.equals(null)) {
            JSONObject Json = new JSONObject(JsonString);
            JSONArray MovieInfo = Json.getJSONArray("results");
            for (int i = 0; i < MovieInfo.length(); i++) {
                Movie movieObject = new Movie();
                JSONObject movie = MovieInfo.getJSONObject(i);
                movieObject.title = movie.getString("title");
                movieObject.plot = movie.getString("overview");
                movieObject.releaseDate = movie.getString("release_date");
                movieObject.posterPath = ("http://image.tmdb.org/t/p/w185/" + movie.getString("poster_path"));
                movieObject.userRating = movie.getDouble("vote_average");
                MovieList.add(movieObject);
            }
        }
        return MovieList;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        final String MOVIE_API_URL;
        OkHttpClient client = new OkHttpClient();

        MOVIE_API_URL = "https://api.themoviedb.org/3/movie/" + params[0] + "?api_key=" +  BuildConfig.MOVIE_API_KEY;

        Request request = new Request.Builder()
                .url(MOVIE_API_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return getMovieInfoFromJSON(response.body().string());
        } catch (IOException | JSONException | IllegalStateException e) {
            Log.e("FetchMovieInfo", e.toString());
        }
        return null;
    }


    @Override
    protected void onPostExecute(List<Movie> Movies) {
        if (Movies != null) {
            movieUpdateCallback.movieDataLoaded(Movies);
        }
    }
}
