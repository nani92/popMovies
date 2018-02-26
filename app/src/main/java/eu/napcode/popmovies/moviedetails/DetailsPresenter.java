package eu.napcode.popmovies.moviedetails;

import javax.inject.Inject;

import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;

public class DetailsPresenter implements BasePresenter<DetailsView> {

    private MoviesRepository moviesRepository;

    private DetailsView detailsView;
    private Movie movie;

    private boolean isFavorite;

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
    }

    private void displayMovie(){
        this.detailsView.displayMovieTitle(this.movie.getTitle());
        this.detailsView.displayBackdropImageView(this.movie.getBackdropPath());
        this.detailsView.displayPosterImageView(this.movie.getPosterPath());
        this.detailsView.displayOriginalTitle(this.movie.getOriginalTitle());
        this.detailsView.displayReleaseDate(this.movie.getReleaseDate());
        this.detailsView.displayVoteAverage(this.movie.getVoteAverage());
        this.detailsView.displayPlot(this.movie.getPlot());

        displayFavorite();
    }

    private void displayFavorite() {

        if (isFavorite) {
            this.detailsView.displayFavoriteMovie();
        } else {
            this.detailsView.displayNotFavoriteMovie();
        }
    }

    public void favoriteClicked() {
        isFavorite = !isFavorite;
        displayFavorite();
    }
}
