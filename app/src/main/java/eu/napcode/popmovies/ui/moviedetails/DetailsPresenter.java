package eu.napcode.popmovies.ui.moviedetails;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.model.Video;
import eu.napcode.popmovies.repository.VideosRepository;
import eu.napcode.popmovies.utils.TextUtils;
import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.utils.archbase.PresenterBundle;
import eu.napcode.popmovies.utils.rx.RxSchedulers;

public class DetailsPresenter implements BasePresenter<DetailsView> {

    private static final String SAVE_VIDEOS_LIST = "videos";
    private MoviesRepository moviesRepository;
    private VideosRepository videosRepository;

    private RxSchedulers rxSchedulers;

    private DetailsView detailsView;
    private Movie movie;

    private List<Video> videos;

    @Inject
    public DetailsPresenter(MoviesRepository moviesRepository, VideosRepository videosRepository, RxSchedulers rxSchedulers) {
        this.moviesRepository = moviesRepository;
        this.videosRepository = videosRepository;
        this.rxSchedulers = rxSchedulers;
    }

    @Override
    public void attachView(DetailsView view) {
        this.detailsView = view;
    }

    @Override
    public void dropView() {
        this.detailsView = null;
    }

    @Override
    public PresenterBundle saveState() {
        PresenterBundle presenterBundle = new PresenterBundle();
        presenterBundle.putParcelableArrayList(SAVE_VIDEOS_LIST, new ArrayList<>(this.videos));

        return presenterBundle;
    }

    @Override
    public void restoreState(PresenterBundle presenterBundle) {
        this.videos = presenterBundle.getParcelableArrayList(SAVE_VIDEOS_LIST);
        displayVideos();
    }

    public void setMovie(Movie movie) {
        this.movie = movie;

        if (this.detailsView == null) {
            return;
        }

        displayMovie();
        checkIfFavorite();
        loadVideos();
    }

    private void displayMovie() {
        this.detailsView.displayMovieTitle(this.movie.getTitle());
        this.detailsView.displayOriginalTitle(this.movie.getOriginalTitle());
        this.detailsView.displayReleaseDate(this.movie.getReleaseDate());
        this.detailsView.displayVoteAverage(this.movie.getVoteAverage());
        this.detailsView.displayPlot(this.movie.getPlot());

        displayBackdrop();
        displayPoster();
    }

    private void displayPoster() {

        if (TextUtils.isEmpty(this.movie.getPosterPath())) {
            this.detailsView.displayPoster(this.movie.getPosterBitmap());
        } else {
            this.detailsView.displayPoster(this.movie.getPosterPath());
        }
    }

    private void displayBackdrop() {

        if (TextUtils.isEmpty(this.movie.getBackdropPath())) {
            this.detailsView.hideBackdrop();
        } else {
            this.detailsView.displayBackdrop(this.movie.getBackdropPath());
        }
    }

    private void checkIfFavorite() {
        boolean isFavorite = this.moviesRepository.isMovieFavorite(this.movie.getId());
        displayFavorite(isFavorite);
    }

    private void displayFavorite(boolean isFavorite) {

        if (isFavorite) {
            this.detailsView.displayFavoriteMovie();
        } else {
            this.detailsView.displayNotFavoriteMovie();
        }
    }

    private void loadVideos() {
        this.videosRepository
                .getVideos(this.movie.getId())
                .subscribeOn(this.rxSchedulers.io())
                .observeOn(this.rxSchedulers.androidMainThread())
                .subscribe(videos -> videosDownloaded(videos),
                        throwable -> {});
    }

    private void videosDownloaded(List<Video> videos) {
        this.videos = videos;
        displayVideos();
    }

    private void displayVideos() {

        if (this.detailsView != null) {
            this.detailsView.displayVideos(videos);
        }
    }

    public void favoriteClicked() {
        this.moviesRepository.favoriteChange(this.movie)
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidMainThread())
                .subscribe(()-> checkIfFavorite());
    }

    public void setPosterBitmap(Bitmap posterBitmap) {
        this.movie.setPosterBitmap(posterBitmap);
    }

    public int getMovieId() {
        return this.movie.getId();
    }
}
