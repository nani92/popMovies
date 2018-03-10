package eu.napcode.popmovies.ui.movies;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.ui.favorites.FavoritesActivity;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.ui.moviedetails.DetailsActivity;
import eu.napcode.popmovies.utils.RecyclerViewLoadDataUtils;
import eu.napcode.popmovies.utils.animation.SharedElementMovieAnimationHelper;
import eu.napcode.popmovies.utils.archbase.PresenterBundle;

public class MainActivity extends AppCompatActivity implements MoviesView, MoviesAdapter.OnMovieClickedListener, RecyclerViewLoadDataUtils.LoadDataRecyclerViewListener {

    private static String SAVE_PRESENTER_STATE = "presenter";

    private static final int COLUMNS_LANDSCAPE = 4;
    private static final int COLUMNS_PORTRAIT = 2;

    @Inject MoviesPresenter moviesPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.emptyLayout)
    ConstraintLayout emptyLayout;

    private MoviesAdapter moviesAdapter;
    private boolean isFirstLoadRecyclerview = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AndroidInjection.inject(this);
        this.moviesPresenter.attachView(this);

        setupRecyclerView();

        if (savedInstanceState == null) {
            this.moviesPresenter.loadMovies();
        }
    }

    private void setupRecyclerView() {
        this.recyclerView.setLayoutManager(getLayoutManager());
        this.moviesAdapter = new MoviesAdapter(this);
        this.recyclerView.setAdapter(this.moviesAdapter);
        this.recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_rv));
        this.recyclerView.addOnScrollListener(RecyclerViewLoadDataUtils.getOnScrollListener(this));
    }

    private GridLayoutManager getLayoutManager() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return new GridLayoutManager(this, COLUMNS_PORTRAIT);
        } else {
            return new GridLayoutManager(this, COLUMNS_LANDSCAPE);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.moviesPresenter.restoreState(new PresenterBundle(savedInstanceState.getBundle(SAVE_PRESENTER_STATE)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(SAVE_PRESENTER_STATE, this.moviesPresenter.saveState().getBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.favorite) {
            startActivity(new Intent(this, FavoritesActivity.class));

            return true;
        }

        if (item.getItemId() == R.id.popular) {
            this.moviesPresenter.setSort(SortMovies.POPULAR);
        }

        if (item.getItemId() == R.id.topRated) {
            this.moviesPresenter.setSort(SortMovies.TOP_RATED);
        }

        this.isFirstLoadRecyclerview = true;
        this.moviesPresenter.loadMovies();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        this.moviesPresenter.dropView();

        super.onDestroy();
    }

    @Override
    public void displayMovies(List<Movie> movies) {
        this.moviesAdapter.addMovies(movies);

        if (this.isFirstLoadRecyclerview) {
            this.isFirstLoadRecyclerview = false;
            this.recyclerView.scheduleLayoutAnimation();
        }
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
    public void movieClicked(Movie movie, View view) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.KEY_MOVIE, movie);

        startActivity(intent, SharedElementMovieAnimationHelper.getBundle(this, view, movie.getId()));
    }

    @Override
    public void shouldLoadNewData() {
        this.moviesPresenter.loadMovies();
    }
}
