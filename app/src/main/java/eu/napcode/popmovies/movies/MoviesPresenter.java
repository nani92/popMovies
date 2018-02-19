package eu.napcode.popmovies.movies;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.GetMoviesListener;
import eu.napcode.popmovies.repository.MoviesRepository;

public class MoviesPresenter implements GetMoviesListener, BasePresenter<MoviesView> {

    private MoviesView moviesView;
    private MoviesRepository moviesRepository;

    @Inject
    public MoviesPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void attachView(MoviesView view) {
        this.moviesView = view;
    }

    @Override
    public void dropView() {

    }

    public void getMovies() {
        this.moviesRepository.getMovies(this);
    }

    @Override
    public void moviesReceived(List<Movie> movies) {
        Log.d("Natalia", "" + movies.size());
        this.moviesView.setMovies(movies);
    }

    @Override
    public void moviesFailure() {

    }
}
