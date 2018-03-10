package eu.napcode.popmovies.ui.reviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.model.Review;
import eu.napcode.popmovies.utils.RecyclerViewLoadDataUtils;

public class ReviewsActivity extends AppCompatActivity implements ReviewsView, RecyclerViewLoadDataUtils.LoadDataRecyclerViewListener {

    public static String MOVIE_ID_KEY = "movie id";

    @Inject
    ReviewsPresenter reviewsPresenter;

    @BindView(R.id.reviewRecyclerView)
    RecyclerView recyclerView;

    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        setupRecyclerView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.reviewsPresenter.attachView(this);
        this.reviewsPresenter.loadReviews(getIntent().getExtras().getInt(MOVIE_ID_KEY));
    }

    private void setupRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.reviewsAdapter = new ReviewsAdapter(this);
        this.recyclerView.setAdapter(this.reviewsAdapter);
        this.recyclerView.addOnScrollListener(RecyclerViewLoadDataUtils.getOnScrollListener(this));
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
    protected void onDestroy() {
        this.reviewsPresenter.dropView();

        super.onDestroy();
    }

    @Override
    public void displayReviews(List<Review> reviews) {
        this.reviewsAdapter.addReviews(reviews);
    }

    @Override
    public void shouldLoadNewData() {
        this.reviewsPresenter.loadReviews(getIntent().getExtras().getInt(MOVIE_ID_KEY));
    }
}
