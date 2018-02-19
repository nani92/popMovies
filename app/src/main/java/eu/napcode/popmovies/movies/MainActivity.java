package eu.napcode.popmovies.movies;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.model.Movie;

public class MainActivity extends AppCompatActivity implements MoviesView {

    private static final int COLUMNS_LANDSCAPE = 4;
    private static final int COLUMNS_PORTRAIT = 2;

    @Inject MoviesPresenter moviesPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AndroidInjection.inject(this);
        this.moviesPresenter.attachView(this);

        setupRecyclerView();

        this.moviesPresenter.getMovies();
    }

    private void setupRecyclerView() {
        this.recyclerView.setLayoutManager(getLayoutManager());
        this.moviesAdapter = new MoviesAdapter();
        this.recyclerView.setAdapter(this.moviesAdapter);
    }

    private GridLayoutManager getLayoutManager() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return new GridLayoutManager(this, COLUMNS_PORTRAIT);
        } else {
            return new GridLayoutManager(this, COLUMNS_LANDSCAPE);
        }
    }

    @Override
    public void setMovies(List<Movie> movies) {
        this.moviesAdapter.setMovies(movies);
    }
}
