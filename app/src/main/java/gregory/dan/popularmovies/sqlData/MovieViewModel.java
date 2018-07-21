package gregory.dan.popularmovies.sqlData;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Daniel Gregory on 21/07/2018.
 */
public class MovieViewModel extends AndroidViewModel {

    //get a repository
    private MovieRepository mMovieRepository;
    private LiveData<List<MovieFavourites>> mMovies;

    public MovieViewModel(Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);
        mMovies = mMovieRepository.getmAllMovies();
    }

    public LiveData<List<MovieFavourites>> getAllMovies() {
        return mMovies;
    }

    public void insertMovie(MovieFavourites movie) {
        mMovieRepository.insertMovie(movie);
    }

    public void deleteMovie(String movie) {
        mMovieRepository.deleteMovie(movie);
    }
}
