package eu.napcode.popmovies.movies;

import java.util.List;

import eu.napcode.popmovies.archbase.BaseView;
import eu.napcode.popmovies.model.Movie;

public interface MoviesView extends BaseView {

    void displayMovies(List<Movie> movies);

    void clearRecyclerView();

    void showProgressBar();

    void hideProgressBar();

    void displayErrorWithDownloading();

    void hideEmptyLayout();
}
