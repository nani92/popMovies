package eu.napcode.popmovies.repository;

import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    private MoviesService moviesService;

    private SortMovies lastSortMovies = SortMovies.NONE;
    private int downloadedMoviesPage = 0;
    private boolean isThereNextMoviesPageToDownload = true;

    @Inject
    public MoviesRepositoryImpl(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public Observable<List<Movie>> getMovies(SortMovies sortMovies) {

        if (sortMovies != lastSortMovies) {
            this.lastSortMovies = sortMovies;

            return getMoviesFromService(sortMovies);
        } else {
            return getMoreMoviesFromService(sortMovies);
        }
    }

    private Observable<List<Movie>> getMoviesFromService(SortMovies sortMovies) {
        return moviesService
                .getMovies(SortMovies.getUrlPathForSort(sortMovies), ApiUtils.TMDB_API_KEY)
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

    private Observable<List<Movie>> getMoreMoviesFromService(SortMovies sortMovies) {
        return this.moviesService
                .getNextMovies(SortMovies.getUrlPathForSort(sortMovies), this.downloadedMoviesPage + 1, ApiUtils.TMDB_API_KEY)
                .map(this.mapFunction);
    }

    @Override
    public boolean isMoreMoviesToDownload() {
        return this.isThereNextMoviesPageToDownload;
    }
}
