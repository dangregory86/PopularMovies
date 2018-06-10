package gregory.dan.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Daniel Gregory on 09/06/2018.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MovieViewHolder> {

    private String[] mData;
    private ListItemClickListener mClickListener;

    public interface ListItemClickListener{
        void onClick(int item);
    }

    public MyRecyclerViewAdapter(ListItemClickListener listItemClickListener){
        mClickListener = listItemClickListener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView posterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.grid_item_image_view);
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
        String data = mData[position];
        Picasso.with(holder.posterImageView.getContext()).load("http://image.tmdb.org/t/p/w342" + data).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.length;
        }else{
            return 0;
        }
    }

    public void setMovieData(String[] s){
        mData = s;
        notifyDataSetChanged();
    }
}
