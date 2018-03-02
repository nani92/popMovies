package eu.napcode.popmovies.repository;

import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import eu.napcode.popmovies.utils.persistance.FavoriteMoviesHelper;
import eu.napcode.popmovies.utils.ApiUtils;
import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.api.responsemodel.ResponseMoviePage;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.model.MoviesMapper;
import eu.napcode.popmovies.movies.SortMovies;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

@Singleton
public class MoviesRepositoryImpl implements MoviesRepository {

    private final FavoriteMoviesHelper favoriteMoviesHelper;
    private MoviesService moviesService;

    private int downloadedMoviesPage = 0;
    private boolean isThereNextMoviesPageToDownload = true;

    @Inject
    public MoviesRepositoryImpl(MoviesService moviesService, FavoriteMoviesHelper favoriteMoviesReader) {
        this.moviesService = moviesService;
        this.favoriteMoviesHelper = favoriteMoviesReader;
    }

    @Override
    public Observable<List<Movie>> getMovies(SortMovies sortMovies) {
        return this.moviesService
                .getMovies(SortMovies.getUrlPathForSort(sortMovies), ApiUtils.TMDB_API_KEY)
                .map(this.mapFunction);
    }

    @Override
    public Observable<List<Movie>> getMoreMovies(SortMovies sortMovies) {
        return this.moviesService
                .getNextMovies(SortMovies.getUrlPathForSort(sortMovies), this.downloadedMoviesPage + 1, ApiUtils.TMDB_API_KEY)
                .map(this.mapFunction);
    }

    private Function<ResponseMoviePage, List<Movie>> mapFunction = responseMoviePage -> {
        setMoviesPageVars(responseMoviePage);

        return Lists.transform(responseMoviePage.getResponseMovies(), MoviesMapper.responseToMovie);
    };

    private void setMoviesPageVars(ResponseMoviePage moviePage) {
        this.downloadedMoviesPage = moviePage.getPage();
        this.isThereNextMoviesPageToDownload =
                this.downloadedMoviesPage < moviePage.getTotalPages();
    }

    @Override
    public boolean isMoreMoviesToDownload() {
        return this.isThereNextMoviesPageToDownload;
    }

    @Override
    public boolean isMovieFavorite(int id) {
        Movie movie = favoriteMoviesHelper.getFavoriteMovie(id);

        return movie != null;
    }

    @Override
    public void favoriteChange(Movie movie) {

        if (isMovieFavorite(movie.getId())) {
            this.favoriteMoviesHelper.removeMovieById(movie.getId());
        } else {
            this.favoriteMoviesHelper.saveMovie(movie);
        }
    }

    @Override
    public List<Movie> getFavorites() {
        return this.favoriteMoviesHelper.getAll();
    }
}
