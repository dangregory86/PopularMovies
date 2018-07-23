package gregory.dan.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel Gregory on 10/06/2018.
 */
public class Movie implements Parcelable{

    private String title;
    private String poster_path;
    private double vote_average;
    private String overview;
    private String release_date;
    private boolean mFavourited;
    private String mfilmId;

    public boolean isFavourited() {
        return mFavourited;
    }

    public void setFavourited(boolean favourited) {
        this.mFavourited = favourited;
    }

    protected Movie(String title_, String posterPath, double rating, String overview_, String releaseDate, String filmId) {
        title = title_;
        poster_path = posterPath;
        vote_average = rating;
        overview = overview_;
        release_date = releaseDate;
        mfilmId = filmId;
    }

    public boolean ismFavourited() {
        return mFavourited;
    }

    public void setmFavourited(boolean mFavourited) {
        this.mFavourited = mFavourited;
    }

    public String getMfilmId() {
        return mfilmId;
    }

    public void setMfilmId(String mfilmId) {
        this.mfilmId = mfilmId;
    }

    private Movie(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        release_date = in.readString();
        mfilmId = in.readString();

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeDouble(vote_average);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(mfilmId);
    }
}
