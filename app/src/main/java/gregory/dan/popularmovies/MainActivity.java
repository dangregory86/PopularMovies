package gregory.dan.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ListItemClickListener{

    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    Movie[] movies;

    int selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.poster_grid);
        int numCols = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numCols));
        adapter = new MyRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        selection = 0;

        loadMovieData(selection);

    }

    public void loadMovieData(int selected){
        new MyNetworkTasker().execute(selected);
    }

    public void setNewData(String[] s){
        if(s != null) adapter.setMovieData(s);
    }

    public void goToActivity(Movie m) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, m);
        startActivity(intent);
    }

    @Override
    public void onClick(int item) {
        goToActivity(movies[item]);
    }

    public class MyNetworkTasker extends AsyncTask<Integer, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(Integer... params) {
            if(params.length == 0){
                return null;
            }

            URL searchURL = MyMovieFetcher.buildUrl(params[0]);
            String results = null;
            try{
                results = MyMovieFetcher.getResponseFromHttpUrl(searchURL);
                Movie[] movies = MyJSONParser.getMovieInfoFromJson(MainActivity.this, results);
                return movies;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] s) {
            movies = s;
            String[] posterPath = new String[s.length];
            for(int i =0; i < s.length; i++){
                posterPath[i] = s[i].getPoster_path();
            }
            setNewData(posterPath);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.selection, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.popular_menu:
                selection = 0;
                loadMovieData(selection);
                break;
            case R.id.top_rated_menu:
                selection = 1;
                loadMovieData(selection);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
