package gregory.dan.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Daniel Gregory on 09/06/2018.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MovieViewHolder> {

    private Movie[] mData;
    private ListItemClickListener mClickListener;
    private final String posterPathStart = "http://image.tmdb.org/t/p/w780";

    public interface ListItemClickListener{
        void onClick(int item);
    }

    MyRecyclerViewAdapter(ListItemClickListener listItemClickListener) {
        mClickListener = listItemClickListener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView posterImageView;
        TextView favouriteTextView;

        MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.grid_item_image_view);
            favouriteTextView = itemView.findViewById(R.id.favourite_text_view_main_activity);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onClick(getAdapterPosition());
        }
    }

//    inflates the layout when required.
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.poster_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutID, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        String data = mData[position].getPoster_path();
        holder.favouriteTextView.setVisibility(View.GONE);
        if (mData[position].isFavourited()) {
            holder.favouriteTextView.setVisibility(View.VISIBLE);
        }
        holder.favouriteTextView.setTag(mData[position]);
        Picasso.get().load(posterPathStart + data).noFade().resizeDimen(R.dimen.poster_width, R.dimen.poster_height).centerInside().into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.length;
        }else{
            return 0;
        }
    }

    public void setMovieData(Movie[] s) {
        mData = s;
        notifyDataSetChanged();
    }
}
