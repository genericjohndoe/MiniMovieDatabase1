package com.gjd.minimoviedatabase;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class fetches data for UI thread on worker thread
 */
public class FetchMovieInfo extends AsyncTask<String, Void, List<Movie>> {


    private List<Movie> getMovieInfoFromJSON(String JsonString)
            throws JSONException {
        long start_time = System.currentTimeMillis();
        JSONObject Json = new JSONObject(JsonString);
        JSONArray MovieInfo = Json.getJSONArray("results");
        List<Movie> MovieList = new ArrayList<Movie>();
        for (int i = 0; i < MovieInfo.length(); i++) {
            Movie movieObject = new Movie();
            JSONObject movie = MovieInfo.getJSONObject(i);
            movieObject.title = movie.getString("title");
            movieObject.plot = movie.getString("overview");
            movieObject.releaseDate = movie.getString("release_date");
            movieObject.posterpath = ("http://image.tmdb.org/t/p/w185/"+ movie.getString("poster_path"));
            movieObject.user_rating = movie.getDouble("vote_average");
            MovieList.add(movieObject);
        }
        long end_time = System.currentTimeMillis();
        Log.i("MinfoJSONtime", Long.toString(end_time-start_time));
        return MovieList;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        Log.i("MinfoDIB", "started");
        long start_time = System.currentTimeMillis();
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String JsonStr = null;
        List<Movie> random = null;
        final String MOVIE_API_URL;


        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast

            MOVIE_API_URL = "https://api.themoviedb.org/3/movie/"+params[0]+"?api_key=4d2fe91044a113962494d96fba3bdfd6";


            Uri builtUri = Uri.parse(MOVIE_API_URL).buildUpon()
                    .build();

            URL url = new URL(builtUri.toString());
            Log.i("URL", url.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("Error ", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Error ", "Error closing stream", e);
                }
            }
        }
        try {
            List<Movie> movies = getMovieInfoFromJSON(JsonStr);
            long end_time = System.currentTimeMillis();
            Log.i("MinfoDIBtime", Long.toString(end_time-start_time));
            Log.i("MinfoDIB", "finished");
            return movies;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return random;
    }

    @Override
    protected void onPostExecute(List<Movie> Movies) {
        if (Movies != null) {
            MainActivityFragment.URLAdapter.clear();
            for (Movie movie : Movies) {
                MainActivityFragment.URLAdapter.add(movie);
            }
        }
    }
}
