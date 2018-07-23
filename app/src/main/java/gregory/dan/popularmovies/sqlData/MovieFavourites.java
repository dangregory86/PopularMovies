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

    @NonNull
    @ColumnInfo(name = "film_id")
    public String mMovieId;

    @NonNull
    @ColumnInfo(name = "poster_path")
    public String poster_path;

    @NonNull
    @ColumnInfo(name = "vote_average")
    public double vote_average;

    @NonNull
    @ColumnInfo(name = "overview")
    public String overview;

    @NonNull
    @ColumnInfo(name = "release_date")
    public String release_date;

    @NonNull
    @ColumnInfo(name = "favourited")
    public boolean mFavourited;

    public MovieFavourites(@NonNull String mMovieTitle, String mMovieId, String poster_path, double vote_average, String overview, String release_date, boolean mFavourited) {
        this.mMovieTitle = mMovieTitle;
        this.mMovieId = mMovieId;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        this.mFavourited = mFavourited;
    }
}
