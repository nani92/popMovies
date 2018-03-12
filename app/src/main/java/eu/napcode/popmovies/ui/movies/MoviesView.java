package eu.napcode.popmovies.ui.movies;

import java.util.List;

import eu.napcode.popmovies.utils.archbase.BaseView;
import eu.napcode.popmovies.model.Movie;

public interface MoviesView extends BaseView {

    void displayMovies(List<Movie> movies);

    void clearRecyclerView();

    void displayProgressBar();

    void hideProgressBar();

    void displayErrorWithDownloading();

    void hideEmptyLayout();

    void displayEmptyLayout();
}
