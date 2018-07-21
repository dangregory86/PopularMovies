package gregory.dan.popularmovies.sqlData;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Daniel Gregory on 21/07/2018.
 */
@Dao
public abstract class MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(MovieFavourites movie);

    @Query("DELETE FROM movies")
    abstract void deleteAll();

    @Query("DELETE FROM movies WHERE movie_title = :movie_name")
    abstract void deleteMovie(String movie_name);


    @Query("SELECT * from movies ORDER BY movie_title ASC")
    abstract LiveData<List<MovieFavourites>> getAllMovies();


}
