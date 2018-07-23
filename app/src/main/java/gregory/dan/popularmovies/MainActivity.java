package gregory.dan.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import gregory.dan.popularmovies.sqlData.MovieFavourites;
import gregory.dan.popularmovies.sqlData.MovieViewModel;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ListItemClickListener{

    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    Movie[] movies;
    MovieViewModel mMoviewViewModel;

    int selection;
    int numCols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instantiate the view model
        mMoviewViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        recyclerView = findViewById(R.id.poster_grid);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            numCols = 2;
        } else {
            numCols = 3;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, numCols));
        adapter = new MyRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        if(movies == null){
        loadMovieData(selection, -1);}

    }

    public void loadMovieData(int selected, int movieID) {
        new MyNetworkTasker(selected, movieID).execute(selected);
    }

    public void setNewData(Movie[] s) {
        if(s != null) adapter.setMovieData(s);
    }

    public void goToActivity(Movie m) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, m);
        startActivity(intent);
    }

    @Override
    public void onClick(int item) {
        goToActivity(movies[item]);
    }

    public class MyNetworkTasker extends AsyncTask<Integer, Void, Movie[]> {

        int mSelection;
        int mMovieId;

        private MyNetworkTasker(int selection, @Nullable int movieId) {
            mSelection = selection;
            mMovieId = movieId;
        }

        @Override
        protected Movie[] doInBackground(Integer... params) {
            if(params.length == 0){
                return null;
            }

            URL searchURL = MyMovieFetcher.buildUrl(mSelection, mMovieId);
            String results;
            try{
                results = MyMovieFetcher.getResponseFromHttpUrl(searchURL);

                return MyJSONParser.getMovieInfoFromJson(MainActivity.this, results);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] s) {
            movies = s;
            checkForFavourite(s, 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.selection, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.popular_menu:
                selection = 0;
                loadMovieData(selection, -1);
                break;
            case R.id.top_rated_menu:
                selection = 1;
                loadMovieData(selection, -1);
                break;
            case R.id.favourited_menu:
                selection = 2;
                checkForFavourite(movies, 2);
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    * @Params Movie[] the movie array to check through.
    * @Params int favourites: checking if it should show only the favourites.
    * */
    public void checkForFavourite(final Movie[] movie, final int favourites) {
        //watch the movie favourites database to see if any are added/removed
        mMoviewViewModel.getAllMovies().observe(this, new Observer<List<MovieFavourites>>() {
            @Override
            public void onChanged(@Nullable List<MovieFavourites> movieFavourites) {
                Movie[] favouriteArray;
                switch(favourites){
                    case 1:
                        for (int j = 0; j < movie.length; j++) {
                            //a boolean to see if the database has teh film as a favourite
                            for (int i = 0; i < movieFavourites.size(); i++) {
                                if (movieFavourites.get(i).mMovieTitle.equalsIgnoreCase(movie[j].getTitle()))
                                    movie[j].setFavourited(true);
                            }
                        }
                        setNewData(movies);
                        break;
                    case 2:
                        favouriteArray = new Movie[movieFavourites.size()];
                        for(int i = 0; i < movieFavourites.size(); i++){
                            String title = movieFavourites.get(i).mMovieTitle;
                            String poster_path = movieFavourites.get(i).poster_path;
                            double vote_average = movieFavourites.get(i).vote_average;
                            String overview = movieFavourites.get(i).overview;
                            String release_date = movieFavourites.get(i).release_date;
                            String mfilmId = movieFavourites.get(i).mMovieId;

                            Movie movie = new Movie(title,  poster_path, vote_average,  overview,  release_date,  mfilmId);
                            movie.setFavourited(true);

                            favouriteArray[i] = movie;
                        }
                        movies = favouriteArray;
                        setNewData(favouriteArray);
                        break;
                }
            }
        });

    }
}
