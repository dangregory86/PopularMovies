package gregory.dan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ListItemClickListener{

//    TODO fill movie poster data
//    TODO async task to collect movie data

    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*a test to check if the recycler view is working*/
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", };
        recyclerView = (RecyclerView) findViewById(R.id.poster_grid);
        int numCols = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numCols));
        adapter = new MyRecyclerViewAdapter(this, data, this);
        recyclerView.setAdapter(adapter);

    }

    public void goToActivity() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onListItemClick(String item) {
        String toastMessage = "Item #" + item + " clicked.";
        Toast mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }
}
