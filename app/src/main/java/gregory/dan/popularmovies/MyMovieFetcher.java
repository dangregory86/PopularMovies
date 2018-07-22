package gregory.dan.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.annotation.Nullable;

import static android.content.ContentValues.TAG;

/**
 * Created by Daniel Gregory on 09/06/2018.
 */
public class MyMovieFetcher {
    private final static String movieBaseURL = "http://api.themoviedb.org/3/movie";
    private final static String apiKeyCode = "";

    private final static String popular = "popular";
    private final static String topRated = "top_rated";
    private final static String apiKey = "?api_key=";
    private static final String VIDEOS = "videos";


    /*url builder to build the api url
    * @param int to decide weather to show popular or top rated movies*/
    public static URL buildUrl(int selection, @Nullable int movieId) {

        String searched;
        Uri builtUri = null;

        switch (selection) {
            case 0:
            case 1:
                if (selection == 0) {
                    searched = popular;
                } else {
                    searched = topRated;
                }
                searched += apiKey + apiKeyCode;
                //        Building the uri
                builtUri = Uri.parse(movieBaseURL).buildUpon()
                        .appendEncodedPath(searched)
                        .build();
                break;
            case 4:
                searched = VIDEOS;
                searched += apiKey + apiKeyCode;
                builtUri = Uri.parse(movieBaseURL).buildUpon()
                        .appendPath(Integer.toString(movieId))
                        .appendEncodedPath(searched)
                        .build();
                break;
            default:
                Log.d(TAG, " wrong uri entered");


        }
        Log.d(TAG, "buildUrl: address = " + builtUri.toString());
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        InputStream inputStream;
        try {
            inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }



}
