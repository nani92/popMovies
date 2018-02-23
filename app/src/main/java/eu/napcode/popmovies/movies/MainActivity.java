package eu.napcode.popmovies.movies;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.moviedetails.DetailsActivity;

public class MainActivity extends AppCompatActivity implements MoviesView, MoviesAdapter.OnMovieClickedListener {

    private static final int COLUMNS_LANDSCAPE = 4;
    private static final int COLUMNS_PORTRAIT = 2;

    @Inject MoviesPresenter moviesPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.emptyLayout)
    SwipeRefreshLayout emptyLayout;

    MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AndroidInjection.inject(this);
        this.moviesPresenter.attachView(this);

        setupRecyclerView();
        setupSwipeRefreshLayout();

        this.moviesPresenter.getMovies();
    }

    private void setupRecyclerView() {
        this.recyclerView.setLayoutManager(getLayoutManager());
        this.moviesAdapter = new MoviesAdapter(this);
        this.recyclerView.setAdapter(this.moviesAdapter);

        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (MainActivity.this.moviesPresenter.shouldDownloadMoreMovies()) {

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        MainActivity.this.moviesPresenter.getMovies();
                    }
                }
            }
        });
    }

    private GridLayoutManager getLayoutManager() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return new GridLayoutManager(this, COLUMNS_PORTRAIT);
        } else {
            return new GridLayoutManager(this, COLUMNS_LANDSCAPE);
        }
    }

    private void setupSwipeRefreshLayout() {
        this.emptyLayout.setOnRefreshListener(() -> {
            this.moviesPresenter.getMovies();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.popular) {
            this.moviesPresenter.setSort(SortMovies.POPULAR);
        }

        if (item.getItemId() == R.id.topRated) {
            this.moviesPresenter.setSort(SortMovies.TOP_RATED);
        }

        this.moviesPresenter.getMovies();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayMovies(List<Movie> movies) {
        this.moviesAdapter.addMovies(movies);
    }

    @Override
    public void clearRecyclerView() {
        this.moviesAdapter.clearMovies();
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
    public void displayErrorWithDownloading() {
        Snackbar.make(this.recyclerView, R.string.download_error_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideEmptyLayout() {
        this.emptyLayout.setVisibility(View.GONE);
        this.recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayEmptyLayout() {
        this.emptyLayout.setVisibility(View.VISIBLE);
        this.recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void movieClicked(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.KEY_MOVIE, movie);

        startActivity(intent);
    }
}
