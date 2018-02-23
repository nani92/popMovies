package eu.napcode.popmovies.repository;

import java.util.List;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.movies.SortMovies;
import io.reactivex.Observable;

public interface MoviesRepository {

    Observable<List<Movie>> getMovies(SortMovies sortMovies);

    boolean isMoreMoviesToDownload();
}
