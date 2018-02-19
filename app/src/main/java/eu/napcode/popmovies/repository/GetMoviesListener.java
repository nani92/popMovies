package eu.napcode.popmovies.repository;

import java.util.List;

import eu.napcode.popmovies.model.Movie;

public interface GetMoviesListener {

    void moviesReceived(List<Movie> movies);

    void moviesFailure();

}
