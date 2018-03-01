package eu.napcode.popmovies.moviedetails;

import android.graphics.Bitmap;
import android.text.TextUtils;

import javax.inject.Inject;

import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;

public class DetailsPresenter implements BasePresenter<DetailsView> {

    private MoviesRepository moviesRepository;

    private DetailsView detailsView;
    private Movie movie;

    @Inject
    public DetailsPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void attachView(DetailsView view) {
        this.detailsView = view;
    }

    @Override
    public void dropView() {
        this.detailsView = null;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;

        if (this.detailsView == null) {
            return;
        }

        displayMovie();
        checkIfFavorite();
    }

    private void checkIfFavorite() {
        boolean isFavorite = this.moviesRepository.isMovieFavorite(this.movie.getId());
        displayFavorite(isFavorite);
    }

    private void displayMovie(){
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

    private void displayFavorite(boolean isFavorite) {

        if (isFavorite) {
            this.detailsView.displayFavoriteMovie();
        } else {
            this.detailsView.displayNotFavoriteMovie();
        }
    }

    public void favoriteClicked() {
        this.moviesRepository.favoriteChange(this.movie);

        checkIfFavorite();
    }

    public void setPosterBitmap(Bitmap posterBitmap) {
        this.movie.setPosterBitmap(posterBitmap);
    }
}
