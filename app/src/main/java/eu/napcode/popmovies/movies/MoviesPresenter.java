package eu.napcode.popmovies.movies;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoviesPresenter implements BasePresenter<MoviesView> {

    private MoviesView moviesView;
    private MoviesRepository moviesRepository;
    private SortMovies sort = SortMovies.POPULAR;
    private boolean isDownloadingMovies;

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
        this.isDownloadingMovies = true;
        this.moviesView.showProgressBar();
        this.moviesRepository
                .getMovies(this.sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> moviesDownloaded(movies),
                        error -> displayError(error));
    }

    public void setSort(SortMovies sort) {
        this.sort = sort;
        this.moviesView.clearRecyclerView();
    }

    public boolean shouldDownloadMoreMovies() {
        return isDownloadingMovies == false &&
                this.moviesRepository.isMoreMoviesToDownload();
    }

    void moviesDownloaded(List<Movie> movies) {
        this.isDownloadingMovies = false;
        this.moviesView.hideProgressBar();
        this.moviesView.displayMovies(movies);
    }

    void displayError(Throwable error) {
        Log.d("Natalia", "error" + error);
        this.isDownloadingMovies = false;
        this.moviesView.hideProgressBar();
        this.moviesView.displayErrorWithDownloading();
    }
}
