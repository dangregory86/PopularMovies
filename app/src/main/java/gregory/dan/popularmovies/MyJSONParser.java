package gregory.dan.popularmovies;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel Gregory on 10/06/2018.
 */
public class MyJSONParser {


    /* the results come in a json array */
    final private static String RESULTS_ARRAY = "results";

    /**
     * @param movieJSON JSON response from server
     * @return Array of Movies describing movie data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] getMovieInfoFromJson(Context context, String movieJSON)
            throws JSONException {


        /* inside the array is the path to the poster image */
        final String MOVIE_TITLE = "title";
        final String VOTE_AVERAGE = "vote_average";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        final String FILM_ID = "id";

        Movie[] movieDetails;

        JSONObject movieJSONs = new JSONObject(movieJSON);

        JSONArray resultsArray = movieJSONs.getJSONArray(RESULTS_ARRAY);

        movieDetails = new Movie[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {
            String title;
            String poster_path;
            double vote_average;
            String overview;
            String release_date;
            String filmId;

            /* Get the JSON object representing the movie */
            JSONObject movie = resultsArray.getJSONObject(i);
            //get the movie title
            filmId = movie.getString(FILM_ID);
            title = movie.getString(MOVIE_TITLE);
            vote_average = movie.getDouble(VOTE_AVERAGE);
            overview = movie.getString(OVERVIEW);
            release_date = movie.getString(RELEASE_DATE);
            poster_path = movie.getString(POSTER_PATH);

            movieDetails[i] = new Movie(title, poster_path, vote_average, overview, release_date, filmId);
        }

        return movieDetails;
    }

    /*
     * This method does a similar thing but for the movie trailer details
     * @param movieJSON JSON response from server
     * @return Array of MovieTrailers describing movie data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static MovieTrailer[] getTrailerDetailsFromJson(Context context, String movieTrailerJson)
            throws JSONException {

        MovieTrailer[] trailerIds;

        final String TRAILER_KEY = "key";
        final String TRAILER_NAME = "name";

        JSONObject movieTrailers = new JSONObject(movieTrailerJson);

        JSONArray trailerDetailArray = movieTrailers.getJSONArray(RESULTS_ARRAY);
        trailerIds = new MovieTrailer[trailerDetailArray.length()];

        //loop through the array of trailers
        for (int i = 0; i < trailerDetailArray.length(); i++) {
            String trailerKey;
            String trailerName;

            JSONObject trailer = trailerDetailArray.getJSONObject(i);

            trailerKey = trailer.getString(TRAILER_KEY);
            trailerName = trailer.getString(TRAILER_NAME);

            trailerIds[i] = new MovieTrailer(trailerKey, trailerName);
        }
        return trailerIds;
    }

}
