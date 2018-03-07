package eu.napcode.popmovies.ui.movies;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.utils.archbase.PresenterBundle;
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class MoviesPresenter implements BasePresenter<MoviesView> {

    private static String SAVE_LIST_STATE = "list state";

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
        this.moviesView = null;
    }

    @Override
    public PresenterBundle saveState() {
        PresenterBundle presenterBundle = new PresenterBundle();
        presenterBundle.putParcelableArrayList(SAVE_LIST_STATE, new ArrayList<>(this.movies));

        return presenterBundle;
    }

    @Override
    public void restoreState(PresenterBundle presenterBundle) {
        this.movies = presenterBundle.getParcelableArrayList(SAVE_LIST_STATE);

        if (this.movies == null || this.movies.isEmpty()) {
            loadMovies();
        } else {
            displayMovies(this.movies);
        }
    }

    public void loadMovies() {
        this.isDownloadingMovies = true;
        this.moviesView.displayProgressBar();
        Observable<List<Movie>> moviesObservable;

        if (this.movies.isEmpty()) {
            moviesObservable = this.moviesRepository.getMovies(this.sort);
        } else {
            moviesObservable = this.moviesRepository.getMoreMovies(this.sort);
        }

        moviesObservable
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
        displayMovies(movies);
    }

    private void displayMovies(List<Movie> movies) {

        if (this.moviesView == null) {
            return;
        }

        this.moviesView.displayMovies(movies);
        manageEmptyLayout();

        this.moviesView.hideProgressBar();
    }

    void manageEmptyLayout() {

        if (this.movies.isEmpty()) {
            this.moviesView.displayEmptyLayout();
        } else {
            this.moviesView.hideEmptyLayout();
        }
    }

    void errorWithDownloadingMovies(Throwable error) {
        this.isDownloadingMovies = false;

        if (this.moviesView == null) {
            return;
        }

        manageEmptyLayout();

        this.moviesView.hideProgressBar();
        this.moviesView.displayErrorWithDownloading();
    }
}
