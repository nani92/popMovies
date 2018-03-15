package eu.napcode.popmovies.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.api.responsemodel.movie.ResponseMoviePage;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.ui.movies.SortMovies;
import eu.napcode.popmovies.utils.ApiUtils;
import eu.napcode.popmovies.utils.persistance.FavoriteMoviesHelper;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static testhelpers.MockMoviesHelper.getFullMovie;
import static testhelpers.MockMoviesHelper.getPopularMovies;
import static testhelpers.MockMoviesResponseHelper.getResponseMoviesFirstOfTwoPages;
import static testhelpers.MockMoviesResponseHelper.getResponseMoviesSecondOfTwoPages;

@RunWith(MockitoJUnitRunner.class)
public class MoviesRepositoryImplTest {

    @Mock
    MoviesService moviesService;

    @Mock
    FavoriteMoviesHelper favoriteMoviesHelper;

    private MoviesRepository moviesRepository;
    private ResponseMoviePage responseMoviePage = getResponseMoviesFirstOfTwoPages();

    @Before
    public void initial() {
        moviesRepository = new MoviesRepositoryImpl(moviesService, favoriteMoviesHelper);

        Mockito.when(moviesService.getMovies(anyString(), anyString()))
                .thenReturn(Observable.just(responseMoviePage));
        Mockito.when(moviesService.getNextMovies(anyString(), anyInt(), anyString()))
                .thenReturn(Observable.just(getResponseMoviesSecondOfTwoPages()).delay(1, TimeUnit.MILLISECONDS));
        Mockito.when(favoriteMoviesHelper.getAll())
                .thenReturn(getPopularMovies());
        Mockito.when(favoriteMoviesHelper.getFavoriteMovie(anyInt()))
                .thenReturn(getFullMovie());
    }

    @Test
    public void testGetPopularMovies() {
        moviesRepository.getMovies(SortMovies.POPULAR);

        Mockito.verify(moviesService).getMovies(ApiUtils.URL_POPULAR, ApiUtils.TMDB_API_KEY);
    }

    @Test
    public void testGetTopMovies() {
        moviesRepository.getMovies(SortMovies.TOP_RATED);

        Mockito.verify(moviesService).getMovies(ApiUtils.URL_TOP_RATED, ApiUtils.TMDB_API_KEY);
    }

    @Test
    public void testShouldLetDownloadMoreMovies() {
        moviesRepository.getMovies(SortMovies.POPULAR)
                .test()
                .assertNoErrors()
                .onComplete();

        Assert.assertEquals(true, moviesRepository.hasMoreMoviesToDownload());
    }

    @Test
    public void testShouldNotLetDownloadMoreMovies() {
        moviesRepository.getMovies(SortMovies.POPULAR)
                .test()
                .assertNoErrors()
                .awaitTerminalEvent();
        moviesRepository.getMoreMovies(SortMovies.POPULAR)
                .test()
                .assertNoErrors()
                .awaitTerminalEvent();

        Assert.assertEquals(false, moviesRepository.hasMoreMoviesToDownload());
    }

    @Test
    public void testGetMorePopularMovies() {
        moviesRepository.getMovies(SortMovies.POPULAR)
                .test()
                .assertNoErrors()
                .awaitTerminalEvent();
        moviesRepository.getMoreMovies(SortMovies.POPULAR)
                .test()
                .assertNoErrors()
                .awaitTerminalEvent();

        Mockito.verify(moviesService).getNextMovies(ApiUtils.URL_POPULAR, 2, ApiUtils.TMDB_API_KEY);
    }

    @Test
    public void testGetFavoriteMovies() {
        moviesRepository.getFavorites();

        Mockito.verify(favoriteMoviesHelper).getAll();
    }

    @Test
    public void testMovieFavorite() {
        boolean isFav = moviesRepository.isMovieFavorite(0);

        Mockito.verify(favoriteMoviesHelper).getFavoriteMovie(0);
        Assert.assertEquals(true, isFav);
    }

    @Test
    public void testMovieNotFavorite() {
        Mockito.when(favoriteMoviesHelper.getFavoriteMovie(anyInt()))
                .thenReturn(null);

        boolean isFav = moviesRepository.isMovieFavorite(0);

        Mockito.verify(favoriteMoviesHelper).getFavoriteMovie(0);
        Assert.assertEquals(false, isFav);
    }

    @Test
    public void testChangeToFav() {
        Mockito.when(favoriteMoviesHelper.getFavoriteMovie(anyInt()))
                .thenReturn(null);

        Movie movie = getFullMovie();
        moviesRepository.favoriteChange(movie)
                .test()
                .awaitTerminalEvent();

        Mockito.verify(favoriteMoviesHelper).getFavoriteMovie(0);
        Mockito.verify(favoriteMoviesHelper).saveMovie(movie);
        Mockito.verify(favoriteMoviesHelper, Mockito.times(0)).removeMovieById(0);
    }

    @Test
    public void testChangeToNotFav() {
        Movie movie = getFullMovie();
        moviesRepository.favoriteChange(movie)
                .test()
                .awaitTerminalEvent();

        Mockito.verify(favoriteMoviesHelper).getFavoriteMovie(0);
        Mockito.verify(favoriteMoviesHelper, Mockito.times(0)).saveMovie(movie);
        Mockito.verify(favoriteMoviesHelper).removeMovieById(0);
    }
}