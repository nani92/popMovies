package eu.napcode.popmovies.moviedetails;

import eu.napcode.popmovies.archbase.BaseView;

public interface DetailsView extends BaseView {

    void displayMovieTitle(String title);

    void displayBackdropImageView(String path);

    void displayPosterImageView(String path);

    void displayOriginalTitle(String originalTitle);

    void displayReleaseDate(String releaseDate);

    void displayVoteAverage(double voteAverage);

    void displayPlot(String plot);
}
