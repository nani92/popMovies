package eu.napcode.popmovies.ui.favorites;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;

import static testhelpers.MockMoviesHelper.getPopularMovies;

@RunWith(MockitoJUnitRunner.class)
public class FavoritesPresenterTest {

    @Mock
    MoviesRepository moviesRepository;

    @Mock
    FavoritesView favoritesView;

    private FavoritesPresenter favoritesPresenter;
    private List<Movie> favMovies = getPopularMovies();

    @Before
    public void initial() {
        favoritesPresenter = new FavoritesPresenter(moviesRepository);
        favoritesPresenter.attachView(favoritesView);

        Mockito.when(moviesRepository.getFavorites())
                .thenReturn(favMovies);
    }

    @Test
    public void testLoadFavorites() {
        favoritesPresenter.loadFavorites();

        Mockito.verify(favoritesView).displayFavorites(favMovies);
        Mockito.verify(favoritesView).hideEmptyLayout();
    }

    @Test
    public void testLoadNoFavorites() {
        Mockito.when(moviesRepository.getFavorites())
                .thenReturn(new ArrayList<>());

        favoritesPresenter.loadFavorites();

        Mockito.verify(favoritesView).displayEmptyLayout();
        Mockito.verify(favoritesView, Mockito.times(0)).hideEmptyLayout();
    }
}