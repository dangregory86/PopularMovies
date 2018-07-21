package gregory.dan.popularmovies.sqlData;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Daniel Gregory on 21/07/2018.
 */
@Entity(tableName = "movies")
public class MovieFavourites {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movie_title")
    public String mMovieTitle;

    public MovieFavourites(@NonNull String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }
}
