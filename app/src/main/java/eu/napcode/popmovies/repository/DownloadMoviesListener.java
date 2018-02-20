package eu.napcode.popmovies.repository;

import java.util.List;

import eu.napcode.popmovies.model.Movie;

public interface DownloadMoviesListener {

    void moviesReceived(List<Movie> movies);

    void moviesFailure();

}
