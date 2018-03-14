package eu.napcode.popmovies.ui.favorites;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import eu.napcode.popmovies.repository.MoviesRepository;


@RunWith(MockitoJUnitRunner.class)
public class FavoritesPresenterTest {

    @Mock
    MoviesRepository moviesRepository;

    @Mock
    FavoritesView favoritesView;

    private FavoritesPresenter favoritesPresenter;

    @Before
    public void initial() {
        favoritesPresenter = new FavoritesPresenter(moviesRepository);
        favoritesPresenter.attachView(favoritesView);

       // Mockito.when(moviesRepository.getFavorites())
    }
}