package gregory.dan.popularmovies;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Daniel Gregory on 09/06/2018.
 */
public class MyMovieFetcher {

    private final static String movieBaseURL = "https://api.themoviedb.org/3/movie";
    private final static String apiKeyCode = "";

    private final static String popular = "popular";
    private final static String topRated = "top_rated";
    private final static String apiKey = "?api_key=" + apiKeyCode;

    /*url builder to build the api url
    * @param int to decide weather to show popular or top rated movies*/
    public static URL buildUrl(int selection){

        String listType;
        if(selection == 0){
            listType = popular;
        }else{
            listType = topRated;
        }

//        Building the uri
        Uri builtUri = Uri.parse(movieBaseURL).buildUpon()
                .appendPath(listType)
                .appendEncodedPath(apiKey)
                .build();

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
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
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
