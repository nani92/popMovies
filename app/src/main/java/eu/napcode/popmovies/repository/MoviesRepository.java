package eu.napcode.popmovies.repository;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.movies.SortMovies;

public interface MoviesRepository {

    void getMovies(DownloadMoviesListener downloadMoviesListener, SortMovies sortMovies);

    Movie getMovieById(int movieId);

    boolean shouldDownloadMoreMovies();
}
