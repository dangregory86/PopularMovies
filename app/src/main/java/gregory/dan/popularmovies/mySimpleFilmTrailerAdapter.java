package gregory.dan.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class mySimpleFilmTrailerAdapter extends ArrayAdapter<MovieTrailer> {
    private final MovieTrailer[] mMovies;
    private final Context mContext;

    public mySimpleFilmTrailerAdapter(@NonNull Context context, MovieTrailer[] movies) {
        super(context, -1, movies);
        this.mMovies = movies;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.trailer_list_view_item, parent, false);
        TextView trailerNameTextView = rowView.findViewById(R.id.trailer_list_item_text_view);
        trailerNameTextView.setText(mMovies[position].getTrailerName());
        rowView.setTag(mMovies[position]);

        return rowView;
    }
}
