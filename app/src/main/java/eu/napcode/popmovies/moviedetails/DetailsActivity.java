package eu.napcode.popmovies.moviedetails;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.utils.ApiUtils;
import eu.napcode.popmovies.model.Movie;

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    //TODO more data saved to sql
    //TODO network helper

    public static final String KEY_MOVIE = "movie";

    @Inject
    DetailsPresenter detailsPresenter;

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

    @BindView(R.id.favoriteFab)
    FloatingActionButton favouriteFab;

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
        this.detailsPresenter.setMovie((Movie) getIntent().getParcelableExtra(KEY_MOVIE));
    }

    @OnClick(R.id.favoriteFab)
    void onFavoriteFabClicked() {
        this.detailsPresenter.favoriteClicked();
    }

    @Override
    public void displayMovieTitle(String title) {
        this.titleTextView.setText(title);
    }

    @Override
    public void displayBackdrop(String path) {
        Glide.with(this)
                .load(ApiUtils.getBackdropUrl(path))
                .apply(new RequestOptions()
                    .placeholder(R.drawable.popcorn))
                .into(this.backdropImageView);
    }

    @Override
    public void displayPoster(String path) {
        Glide.with(this)
                .asBitmap()
                .load(ApiUtils.getPosterUrl(path))
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(@NonNull Bitmap posterBitmap, @Nullable Transition<? super Bitmap> transition) {
                        DetailsActivity.this.posterImageView.setImageBitmap(posterBitmap);
                        DetailsActivity.this.detailsPresenter.setPosterBitmap(posterBitmap);
                    }
                });
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

    @Override
    public void displayFavoriteMovie() {
        this.favouriteFab.setImageResource(R.drawable.ic_favorite);
    }

    @Override
    public void displayNotFavoriteMovie() {
        this.favouriteFab.setImageResource(R.drawable.ic_favorite_border);
    }

    @Override
    public void displayPoster(Bitmap bitmap) {
        this.posterImageView.setImageBitmap(bitmap);
    }
}
