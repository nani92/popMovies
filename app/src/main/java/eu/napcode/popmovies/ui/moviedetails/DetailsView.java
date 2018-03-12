package eu.napcode.popmovies.ui.moviedetails;

import android.graphics.Bitmap;

import java.util.List;

import eu.napcode.popmovies.model.Video;
import eu.napcode.popmovies.utils.archbase.BaseView;

public interface DetailsView extends BaseView {

    void displayMovieTitle(String title);

    void displayBackdrop(String path);

    void hideBackdrop();

    void displayPoster(String path);

    void displayPoster(Bitmap bitmap);

    void displayOriginalTitle(String originalTitle);

    void displayReleaseDate(String releaseDate);

    void displayVoteAverage(String voteAverage);

    void displayPlot(String plot);

    void displayFavoriteMovie();

    void displayNotFavoriteMovie();

    void displayVideos(List<Video> videos);
}
