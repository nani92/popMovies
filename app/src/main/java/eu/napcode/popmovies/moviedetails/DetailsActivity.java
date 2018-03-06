package eu.napcode.popmovies.moviedetails;

import android.content.ActivityNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.model.Video;
import eu.napcode.popmovies.utils.ApiUtils;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.utils.YoutubeUtils;
import eu.napcode.popmovies.utils.animation.SharedElementMovieAnimationHelper;
import eu.napcode.popmovies.utils.animation.TransitionAnimations;

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    //TODO network helper
    //TODO remove toolbar
    //TODO create collapsible toolbar
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

    @BindView(R.id.detailsConstraintLayout)
    ConstraintLayout detailsConstraintLayout;

    @BindView(R.id.videosRecyclerView)
    RecyclerView videoRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        this.posterImageView.setTransitionName(
                SharedElementMovieAnimationHelper.getTransitionName(
                        this.posterImageView.getTransitionName(),
                        ((Movie) getIntent().getParcelableExtra(KEY_MOVIE)).getId()));

        setupPresenter();
        TransitionAnimations.setDetailsTransitionAnimations(getWindow(), getResources().getInteger(R.integer.anim_duration));

        if (savedInstanceState != null) {
            this.favouriteFab.show();
        }
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
    protected void onDestroy() {
        this.detailsPresenter.dropView();

        super.onDestroy();
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
    public void hideBackdrop() {
        this.backdropImageView.setVisibility(View.GONE);
        changeConstraintsForViewsRelatedWithBackdrop();
    }

    private void changeConstraintsForViewsRelatedWithBackdrop() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((Guideline) findViewById(R.id.topCardviewGuideline)).setGuidelineBegin((int) getResources().getDimension(R.dimen.base_margin));

            return;
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(detailsConstraintLayout);

        constraintSet.clear(R.id.posterImageView, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.posterImageView, ConstraintSet.TOP, R.id.topGuideline, ConstraintSet.BOTTOM);

        constraintSet.clear(R.id.favoriteFab, ConstraintSet.TOP);
        constraintSet.connect(R.id.favoriteFab, ConstraintSet.BOTTOM, R.id.detailsTopDividerView, ConstraintSet.TOP);

        constraintSet.applyTo(detailsConstraintLayout);
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
    public void displayVoteAverage(String voteAverage) {
        this.averageVoteTextView.setText(voteAverage);
    }

    @Override
    public void displayPlot(String plot) {
        this.plotTextView.setText(plot);
    }

    @Override
    public void displayFavoriteMovie() {
        this.favouriteFab.setImageResource(R.drawable.fav_empty_to_full);
        ((Animatable)this.favouriteFab.getDrawable()).start();
    }

    @Override
    public void displayNotFavoriteMovie() {
        this.favouriteFab.setImageResource(R.drawable.fav_full_to_empty);
        ((Animatable)this.favouriteFab.getDrawable()).start();
    }

    //TODO display videos inside app
    @Override
    public void displayVideos(List<Video> videos) {
        this.videoRecyclerView.setVisibility(View.VISIBLE);
        this.videoRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_rv));
        this.videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.videoRecyclerView.setAdapter(new VideosAdapter(videos, videoKey -> {
            displayYoutubeVideo(videoKey);
        }));
        this.videoRecyclerView.scheduleLayoutAnimation();
    }

    private void displayYoutubeVideo(String key) {

        try {
            startActivity(YoutubeUtils.getYoutubeAppIntent(key));
        } catch (ActivityNotFoundException ex) {
            startActivity(YoutubeUtils.getYoutubeWebIntent(key));
        }
    }

    @Override
    public void displayPoster(Bitmap bitmap) {
        this.posterImageView.setImageBitmap(bitmap);
    }
}
