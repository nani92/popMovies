package eu.napcode.popmovies.ui.reviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.model.Review;
import eu.napcode.popmovies.utils.RecyclerViewLoadDataUtils;
import eu.napcode.popmovies.utils.archbase.PresenterBundle;

public class ReviewsActivity extends AppCompatActivity implements ReviewsView, RecyclerViewLoadDataUtils.LoadDataRecyclerViewListener {

    private static final String SAVE_PRESENTER_STATE = "reviews presenter";
    public static String MOVIE_ID_KEY = "movie id";

    @Inject
    ReviewsPresenter reviewsPresenter;

    @BindView(R.id.reviewRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.emptyLayout)
    ConstraintLayout emptyLayout;

    @BindView(R.id.noDataTextView)
    TextView noDataTextView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ReviewsAdapter reviewsAdapter;
    private boolean isFirstLoadRecyclerview = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        setupRecyclerView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.reviewsPresenter.attachView(this);
        this.noDataTextView.setText(R.string.no_reviews_to_display);
    }

    private void setupRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.reviewsAdapter = new ReviewsAdapter(this);
        this.recyclerView.setAdapter(this.reviewsAdapter);
        this.recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_rv_reviews));
        this.recyclerView.addOnScrollListener(RecyclerViewLoadDataUtils.getOnScrollListener(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.reviewsPresenter.loadReviews(getIntent().getExtras().getInt(MOVIE_ID_KEY));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.reviewsPresenter.restoreState(new PresenterBundle(savedInstanceState.getBundle(SAVE_PRESENTER_STATE)));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(SAVE_PRESENTER_STATE, this.reviewsPresenter.saveState().getBundle());
    }

    @Override
    protected void onDestroy() {
        this.reviewsPresenter.dropView();

        super.onDestroy();
    }

    @Override
    public void displayReviews(List<Review> reviews) {
        this.recyclerView.setVisibility(View.VISIBLE);
        this.reviewsAdapter.addReviews(reviews);

        if (this.isFirstLoadRecyclerview) {
            this.isFirstLoadRecyclerview = false;
            this.recyclerView.scheduleLayoutAnimation();
        }
    }

    @Override
    public void displayEmptyLayout() {
        this.emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyLayout() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.item_animation_hide_empty);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                emptyLayout.setVisibility(View.GONE);
            }
        });

        emptyLayout.startAnimation(animation);
    }

    @Override
    public void displayProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void shouldLoadNewData() {
        this.reviewsPresenter.loadReviews(getIntent().getExtras().getInt(MOVIE_ID_KEY));
    }
}
