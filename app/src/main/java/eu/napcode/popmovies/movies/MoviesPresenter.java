package eu.napcode.popmovies.movies;

import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Scheduler;

public class MoviesPresenter implements BasePresenter<MoviesView> {

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
        this.moviesView.clearRecyclerView();
        this.moviesView.displayEmptyLayout();
    }

    public boolean shouldDownloadMoreMovies() {
        return isDownloadingMovies == false &&
                this.moviesRepository.isMoreMoviesToDownload();
    }

    void moviesDownloaded(List<Movie> movies) {
        this.isDownloadingMovies = false;
        this.moviesView.hideProgressBar();
        this.moviesView.displayMovies(movies);
        this.moviesView.hideEmptyLayout();
    }

    void errorWithDownloadingMovies(Throwable error) {
        this.isDownloadingMovies = false;
        this.moviesView.hideProgressBar();
        this.moviesView.displayErrorWithDownloading();
    }
}
