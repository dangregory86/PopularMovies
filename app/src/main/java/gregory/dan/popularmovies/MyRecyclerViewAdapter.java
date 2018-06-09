package gregory.dan.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Daniel Gregory on 09/06/2018.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private String[] mData = new String[0];
    private LayoutInflater mInflater;
    private ListItemClickListener mClickListener;

    public interface ListItemClickListener{
        void onListItemClick(String item);
    }

    //constructor where the data is passed in
    public MyRecyclerViewAdapter(Context context, String[] data, ListItemClickListener listener){
        mInflater = LayoutInflater.from(context);
        mData = data;
        mClickListener = listener;
    }

//    inflates the layout when required.
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.poster_grid_item;
        View view = mInflater.inflate(layoutID, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
        String data = mData[position];
        holder.myTextView.setText(data);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView myTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.grid_item_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null){
                mClickListener.onListItemClick(mData[getAdapterPosition()]);
            }

        }
    }
}
