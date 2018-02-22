package eu.napcode.popmovies.movies;

import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.DownloadMoviesListener;
import eu.napcode.popmovies.repository.MoviesRepository;

public class MoviesPresenter implements DownloadMoviesListener, BasePresenter<MoviesView> {

    private MoviesView moviesView;
    private MoviesRepository moviesRepository;
    private SortMovies sort = SortMovies.POPULAR;

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
        this.moviesRepository.getMovies(this, this.sort);
    }

    public void setSort(SortMovies sort) {
        this.sort = sort;
        this.moviesView.clearRecyclerView();
    }

    public boolean shouldDownloadMoreMovies() {
        return this.moviesRepository.shouldDownloadMoreMovies();
    }

    @Override
    public void moviesReceived(List<Movie> movies) {
        this.moviesView.setMovies(movies);
    }

    @Override
    public void moviesFailure() {

    }

}
