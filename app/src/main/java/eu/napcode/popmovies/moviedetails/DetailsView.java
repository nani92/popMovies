package eu.napcode.popmovies.moviedetails;

import android.graphics.Bitmap;

import eu.napcode.popmovies.utils.archbase.BaseView;

public interface DetailsView extends BaseView {

    void displayMovieTitle(String title);

    void displayBackdrop(String path);

    void displayPoster(String path);

    void displayPoster(Bitmap bitmap);

    void displayOriginalTitle(String originalTitle);

    void displayReleaseDate(String releaseDate);

    void displayVoteAverage(String voteAverage);

    void displayPlot(String plot);

    void displayFavoriteMovie();

    void displayNotFavoriteMovie();
}
