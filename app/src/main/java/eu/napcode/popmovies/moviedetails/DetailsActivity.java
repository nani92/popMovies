package eu.napcode.popmovies.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    public static final String KEY_MOVIE_ID = "movie id";

    @Inject DetailsPresenter detailsPresenter;

    @BindView(R.id.titleTextView)
    TextView titleTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        setupPresenter();
    }

    private void setupPresenter() {
        this.detailsPresenter.attachView(this);
        this.detailsPresenter.getMovie(getIntent().getIntExtra(KEY_MOVIE_ID, 0));
    }

    @Override
    public void setMovieTitle(String title) {
        titleTextView.setText(title);
    }
}
