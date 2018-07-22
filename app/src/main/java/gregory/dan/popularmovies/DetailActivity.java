package gregory.dan.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import gregory.dan.popularmovies.sqlData.MovieFavourites;
import gregory.dan.popularmovies.sqlData.MovieViewModel;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private Button addToFavouritesButton, removeFromFavouritesButton;
    private ImageView favouriteStarImage;
    private MovieViewModel mMoviewViewModel;
    String thisMovie;
    public boolean isFavourited;
    TextView titleTextView;
    ImageView detailImageView;
    TextView detailOverview;
    TextView detailReleaseDate;
    TextView detailRating;
    RatingBar ratingBar;
    ListView trailerListView;
    int searchInt, movieId;
    mySimpleFilmTrailerAdapter mMySimpleFilmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        instantiateViews();
        searchInt = 4;
        final Movie movie;
        Intent intent = getIntent();

        movie = intent.getParcelableExtra(Intent.EXTRA_TEXT);

        float ratingOutOf5 = (float) (movie.getVote_average() / 2);
        String rating = movie.getVote_average() + "/10";
        String releaseDate = "Released\n" + movie.getRelease_date();

        titleTextView.setText(movie.getTitle());
        Picasso.get().load("http://image.tmdb.org/t/p/w185" + movie.getPoster_path()).into(detailImageView);
        detailOverview.setText(movie.getOverview());
        detailReleaseDate.setText(releaseDate);
        detailRating.setText(rating);

        ratingBar.setRating(ratingOutOf5);
        thisMovie = movie.getTitle();
        //setting the add favourite button
        addToFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavourites(thisMovie);
            }
        });

        //setting the delete favourite button
        removeFromFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFromFavourites(thisMovie);
            }
        });
        movieId = Integer.parseInt(movie.getMfilmId());

        new MyDetailNetworkTasker(searchInt, movieId).execute(searchInt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //watch the movie favoiurites database to see if any are added/removed
        mMoviewViewModel.getAllMovies().observe(this, new Observer<List<MovieFavourites>>() {
            @Override
            public void onChanged(@Nullable List<MovieFavourites> movieFavourites) {
                //a boolean to see if the database has teh film as a favourite
                isFavourited = false;
                for (int i = 0; i < movieFavourites.size(); i++) {
                    if (movieFavourites.get(i).mMovieTitle.equalsIgnoreCase(thisMovie)) {
                        isFavourited = true;
                    }
                }
                updateFavouriteView(isFavourited);
            }
        });
    }

    /* Adds or removes the film from favourites list
     *  @params boolean for whether or not the movie is already favourited
     * */
    private void addToFavourites(String movie) {
        MovieFavourites newMovie = new MovieFavourites(movie);
        mMoviewViewModel.insertMovie(newMovie);
        addToFavouritesButton.setVisibility(View.GONE);
        removeFromFavouritesButton.setVisibility(View.VISIBLE);
    }

    private void deleteFromFavourites(String movie) {
        mMoviewViewModel.deleteMovie(movie);
        removeFromFavouritesButton.setVisibility(View.GONE);
        addToFavouritesButton.setVisibility(View.VISIBLE);
    }

    private void updateFavouriteView(boolean fave) {
        if (fave) {
            favouriteStarImage.setVisibility(View.VISIBLE);
            addToFavouritesButton.setVisibility(View.GONE);
            removeFromFavouritesButton.setVisibility(View.VISIBLE);
        } else {
            favouriteStarImage.setVisibility(View.INVISIBLE);
            removeFromFavouritesButton.setVisibility(View.GONE);
            addToFavouritesButton.setVisibility(View.VISIBLE);
        }
    }

    private void instantiateViews() {
        //instantiate the view model
        mMoviewViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        //instantiate all the views
        titleTextView = findViewById(R.id.detail_title_text_view);
        detailImageView = findViewById(R.id.detail_poster_image_view);
        detailOverview = findViewById(R.id.detail_film_description_text_view);
        detailReleaseDate = findViewById(R.id.detail_year_of_release_text_view);
        detailRating = findViewById(R.id.detail_rating_text_view);
        ratingBar = findViewById(R.id.detail_film_rating_bar);
        addToFavouritesButton = findViewById(R.id.favourite_button);
        favouriteStarImage = findViewById(R.id.detail_favourite_star);
        removeFromFavouritesButton = findViewById(R.id.delete_button);
        trailerListView = findViewById(R.id.trailer_list_view);
    }

    public void attachAdapter(MovieTrailer[] movies) {
        mMySimpleFilmAdapter = new mySimpleFilmTrailerAdapter(this, movies);
        trailerListView.setAdapter(mMySimpleFilmAdapter);
        trailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieTrailer movie = (MovieTrailer) view.getTag();
                Toast.makeText(getApplicationContext(), movie.getTrailerCode(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DetailActivity.this, PopUpVideoPlayer.class);
                intent.putExtra("code", movie.getTrailerCode());
                startActivity(intent);
            }
        });
    }

    public class MyDetailNetworkTasker extends AsyncTask<Integer, Void, MovieTrailer[]> {

        int mSelection;
        int mMovieId;

        private MyDetailNetworkTasker(int selection, @Nullable int movieId) {
            mSelection = selection;
            mMovieId = movieId;
        }

        @Override
        protected MovieTrailer[] doInBackground(Integer... params) {
            if (params.length == 0) {
                return null;
            }

            URL searchURL = MyMovieFetcher.buildUrl(mSelection, mMovieId);
            String results = null;
            try {
                results = MyMovieFetcher.getResponseFromHttpUrl(searchURL);
                return MyJSONParser.getTrailerDetailsFromJson(DetailActivity.this, results);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MovieTrailer[] s) {
            attachAdapter(s);
        }
    }


}

