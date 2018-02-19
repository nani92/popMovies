package eu.napcode.popmovies.movies;


import javax.inject.Inject;

import eu.napcode.popmovies.repository.MoviesRepository;

public class MoviesPresenter {

    private MoviesRepository moviesRepository;

    @Inject
    public MoviesPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }
}
