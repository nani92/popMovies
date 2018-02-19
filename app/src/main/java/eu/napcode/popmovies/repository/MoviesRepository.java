package eu.napcode.popmovies.repository;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import eu.napcode.popmovies.api.MoviesService;

@Singleton
public class MoviesRepository {

    private MoviesService moviesService;

    @Inject
    public MoviesRepository(MoviesService moviesService) {
        this.moviesService = moviesService;
    }
}
