package gregory.dan.popularmovies;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel Gregory on 10/06/2018.
 */
public class MyJSONParser {

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param movieJSON JSON response from server
     *
     * @return Array of Strings describing weather data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] getMovieInfoFromJson(Context context, String movieJSON)
            throws JSONException {

        /* the results come in a json array */
        final String RESULTS_ARRAY = "results";

        /* inside the array is the path to the poster image */
        final String MOVIE_TITLE = "title";
        final String VOTE_AVERAGE = "vote_average";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";

        Movie[] movieDetails = null;

        JSONObject movieJSONs = new JSONObject(movieJSON);

        JSONArray resultsArray = movieJSONs.getJSONArray(RESULTS_ARRAY);

        movieDetails = new Movie[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {
            String title;
            String poster_path;
            double vote_average;
            String overview;
            String release_date;

            /* Get the JSON object representing the day */
            JSONObject movie = resultsArray.getJSONObject(i);
            //get the movie title
            title = movie.getString(MOVIE_TITLE);
            vote_average = movie.getDouble(VOTE_AVERAGE);
            overview = movie.getString(OVERVIEW);
            release_date = movie.getString(RELEASE_DATE);
            poster_path = movie.getString(POSTER_PATH);

            movieDetails[i] = new Movie(title, poster_path, vote_average, overview, release_date);
        }

        return movieDetails;
    }


}
