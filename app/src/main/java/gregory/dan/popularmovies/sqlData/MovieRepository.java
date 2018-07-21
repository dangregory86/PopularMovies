package gregory.dan.popularmovies.sqlData;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Daniel Gregory on 21/07/2018.
 */
public class MovieRepository {

    private MovieDAO movieDAO;
    private LiveData<List<MovieFavourites>> mAllMovies;

    MovieRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getDatabase(application);
        movieDAO = movieDatabase.movieDAO();
        mAllMovies = movieDAO.getAllMovies();
    }

    LiveData<List<MovieFavourites>> getmAllMovies() {
        return mAllMovies;
    }

    public void insertMovie(MovieFavourites movie) {
        new InsertAsyncTask(movieDAO).execute(movie);
    }

    public void deleteMovie(String movie) {
        new DeleteAsyncTask(movieDAO).execute(movie);
    }

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {
        //get a dao for the async task
        private MovieDAO aSyncMovieDAO;

        DeleteAsyncTask(MovieDAO dao) {
            aSyncMovieDAO = dao;
        }

        @Override
        protected Void doInBackground(String... params) {
            aSyncMovieDAO.deleteMovie(params[0]);
            return null;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<MovieFavourites, Void, Void> {
        //get a dao for the async task
        private MovieDAO aSyncMovieDAO;

        InsertAsyncTask(MovieDAO dao) {
            aSyncMovieDAO = dao;
        }

        @Override
        protected Void doInBackground(MovieFavourites... params) {
            aSyncMovieDAO.insert(params[0]);
            return null;
        }
    }

}
