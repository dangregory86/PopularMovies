package gregory.dan.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Daniel Gregory on 23/07/2018.
 */
public class MySimpleFilmReviewAdapter extends ArrayAdapter<MovieReview> {
    private final MovieReview[] mMovies;
    private final Context mContext;

    public MySimpleFilmReviewAdapter(@NonNull Context context, MovieReview[] movies) {
        super(context, -1, movies);
        this.mMovies = movies;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.review_list_item_view, parent, false);
        TextView reviewAuthor = rowView.findViewById(R.id.review_list_item_text_view_author);
        reviewAuthor.setText(mMovies[position].getmAuthor());
        TextView reviewText = rowView.findViewById(R.id.review_list_item_text_view_review_text);
        reviewText.setText(mMovies[position].getmReview());

        return rowView;
    }
}
