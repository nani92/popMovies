package eu.napcode.popmovies.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.moviedetails.DetailsActivity;
import eu.napcode.popmovies.utils.animation.SharedElementMovieAnimationHelper;

public class FavoritesActivity extends AppCompatActivity implements FavoritesView, FavoritesAdapter.OnMovieClickedListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.emptyLayout)
    ConstraintLayout emptyLayout;

    private FavoritesAdapter favoritesAdapter;

    @Inject
    FavoritesPresenter favoritesPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AndroidInjection.inject(this);
        favoritesPresenter.attachView(this);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.favoritesAdapter = new FavoritesAdapter(this);
        this.recyclerView.setAdapter(this.favoritesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.favoritesPresenter.loadFavorites();
    }

    @Override
    protected void onDestroy() {
        this.favoritesPresenter.dropView();

        super.onDestroy();
    }

    @Override
    public void movieClicked(Movie movie, View view) {
        //TODO start for result in order to animate unfavorited movie
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.KEY_MOVIE, movie);

        startActivity(intent, SharedElementMovieAnimationHelper.getBundle(this, view));
    }

    @Override
    public void displayFavorites(List<Movie> favorites) {
        this.favoritesAdapter.setMovies(favorites);
    }

    @Override
    public void displayEmptyLayout() {
        this.recyclerView.setVisibility(View.GONE);
        this.emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyLayout() {
        this.recyclerView.setVisibility(View.VISIBLE);
        this.emptyLayout.setVisibility(View.GONE);
    }
}
