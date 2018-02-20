package eu.napcode.popmovies.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.api.ApiUtils;

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    public static final String KEY_MOVIE_ID = "movie id";

    @Inject DetailsPresenter detailsPresenter;

    @BindView(R.id.titleTextView)
    TextView titleTextView;

    @BindView(R.id.backdropImageView)
    ImageView backdropImageView;

    @BindView(R.id.posterImageView)
    ImageView posterImageView;

    @BindView(R.id.releaseDateValueTextView)
    TextView releaseDateTextView;

    @BindView(R.id.originalTitleValueTextView)
    TextView originalTitleTextView;

    @BindView(R.id.averageVoteValueTextView)
    TextView averageVoteTextView;

    @BindView(R.id.plotTextView)
    TextView plotTextView;

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
    public void displayMovieTitle(String title) {
        this.titleTextView.setText(title);
    }

    @Override
    public void displayBackdropImageView(String path) {
        Glide.with(this)
                .load(ApiUtils.getBackdropUrl(path))
                .into(this.backdropImageView);
    }

    @Override
    public void displayPosterImageView(String path) {
        Glide.with(this)
                .load(ApiUtils.getPosterUrl(path))
                .into(this.posterImageView);
    }

    @Override
    public void displayOriginalTitle(String originalTitle) {
        this.originalTitleTextView.setText(originalTitle);
    }

    @Override
    public void displayReleaseDate(String releaseDate) {
        this.releaseDateTextView.setText(releaseDate);
    }

    @Override
    public void displayVoteAverage(double voteAverage) {
        this.averageVoteTextView.setText(String.valueOf(voteAverage));
    }

    @Override
    public void displayPlot(String plot) {
        this.plotTextView.setText(plot);
    }
}
