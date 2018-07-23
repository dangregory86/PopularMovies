package gregory.dan.popularmovies;

/**
 * Created by Daniel Gregory on 23/07/2018.
 */
class MovieReview {

    private String mAuthor, mReview;

    public MovieReview(String author, String review){
        mAuthor = author;
        mReview = review;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmReview() {
        return mReview;
    }
}
