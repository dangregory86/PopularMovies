package gregory.dan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private TextView titleTextView;
    private ImageView detailImageView;
    private TextView detailOverview;
    private TextView detailReleaseDate;
    private TextView detailRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = (TextView) findViewById(R.id.detail_title_text_view);
        detailImageView = (ImageView) findViewById(R.id.detail_image_view);
        detailOverview = (TextView) findViewById(R.id.detail_description);
        detailReleaseDate = (TextView) findViewById(R.id.detail_release_date);
        detailRating = (TextView) findViewById(R.id.detail_rating);

        Movie movie;
        Intent intent = getIntent();

        movie = intent.getParcelableExtra(Intent.EXTRA_TEXT);

        titleTextView.setText(movie.getTitle());
        Picasso.with(this).load("http://image.tmdb.org/t/p/w780" + movie.getPoster_path()).into(detailImageView);
        detailOverview.setText(movie.getOverview());
        detailReleaseDate.setText("Released\n" + movie.getRelease_date());
        detailRating.setText(movie.getVote_average() + "/10");


    }
}
