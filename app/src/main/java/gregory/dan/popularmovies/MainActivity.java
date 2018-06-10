package gregory.dan.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ListItemClickListener{

//    TODO fill movie poster data
//    TODO async task to collect movie data

    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    TextView testText;

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
//        recyclerView.setAdapter(adapter);

        //test text view to be deleted
        testText = (TextView) findViewById(R.id.test_text_view);

        new MyNetworkTasker().execute("test");

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

    public void mSetText(String result){
        testText.setText(result);
    }

    public class MyNetworkTasker extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if(params.length == 0){
                return null;
            }

            int listType = 0;
            URL searchURL = MyMovieFetcher.buildUrl(listType);
            String results = null;
            try{
                results = MyMovieFetcher.getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("test", "onPostExecute: finished");
//            mSetText(s);

        }
    }
}
