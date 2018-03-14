package eu.napcode.popmovies.repository;

import java.util.List;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.ui.movies.SortMovies;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface MoviesRepository {

    Observable<List<Movie>> getMovies(SortMovies sortMovies);

    Observable<List<Movie>> getMoreMovies(SortMovies sortMovies);

    boolean hasMoreMoviesToDownload();

    boolean isMovieFavorite(int id);

    Completable favoriteChange(Movie movie);

    List<Movie> getFavorites();
}
