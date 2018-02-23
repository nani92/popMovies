package eu.napcode.popmovies.movies;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Scheduler;

public class MoviesPresenter implements BasePresenter<MoviesView> {

    List<Movie> movies = new ArrayList<>();

    private MoviesView moviesView;
    private MoviesRepository moviesRepository;
    private SortMovies sort = SortMovies.POPULAR;
    private boolean isDownloadingMovies;

    private Scheduler ioScheduler, mainScheduler;

    @Inject
    public MoviesPresenter(MoviesRepository moviesRepository, RxSchedulers rxSchedulers) {
        this.moviesRepository = moviesRepository;
        this.ioScheduler = rxSchedulers.io();
        this.mainScheduler = rxSchedulers.androidMainThread();
    }

    @Override
    public void attachView(MoviesView view) {
        this.moviesView = view;
    }

    @Override
    public void dropView() {

    }

    public void loadMovies() {
        this.isDownloadingMovies = true;
        this.moviesView.displayProgressBar();
        this.moviesRepository
                .getMovies(this.sort)
                .subscribeOn(this.ioScheduler)
                .observeOn(this.mainScheduler)
                .subscribe(movies -> moviesDownloaded(movies),
                        error -> errorWithDownloadingMovies(error));
    }

    public void setSort(SortMovies sort) {
        this.sort = sort;
        this.movies.clear();
        this.moviesView.clearRecyclerView();
    }

    public boolean shouldDownloadMoreMovies() {
        return isDownloadingMovies == false &&
                this.moviesRepository.isMoreMoviesToDownload();
    }

    void moviesDownloaded(List<Movie> movies) {
        this.movies.addAll(movies);
        this.isDownloadingMovies = false;

        manageEmptyLayout();

        this.moviesView.hideProgressBar();
    }

    void manageEmptyLayout() {

        if (this.movies.isEmpty()) {
            this.moviesView.displayEmptyLayout();
        } else {
            this.moviesView.hideEmptyLayout();
            this.moviesView.displayMovies(movies);
        }
    }

    void errorWithDownloadingMovies(Throwable error) {
        this.isDownloadingMovies = false;

        manageEmptyLayout();

        this.moviesView.hideProgressBar();
        this.moviesView.displayErrorWithDownloading();
    }
}
