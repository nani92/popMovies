package eu.napcode.popmovies.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class FavoritesActivity extends AppCompatActivity implements FavoritesView, FavoritesAdapter.OnMovieClickedListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private FavoritesAdapter favoritesAdapter;

    @Inject FavoritesPresenter favoritesPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AndroidInjection.inject(this);
        favoritesPresenter.attachView(this);

        setupRecyclerView();
        this.favoritesPresenter.loadFavorites();
    }

    private void setupRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.favoritesAdapter = new FavoritesAdapter(this);
        this.recyclerView.setAdapter(this.favoritesAdapter);
    }

    @Override
    public void movieClicked(Movie movie, View view) {

    }

    @Override
    public void displayFavorites(List<Movie> favorites) {
        this.favoritesAdapter.setMovies(favorites);
    }
}
