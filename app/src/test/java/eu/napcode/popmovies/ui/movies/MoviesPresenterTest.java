package eu.napcode.popmovies.ui.movies;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import io.reactivex.Observable;
import testhelpers.MockRxSchedulers;

import static org.mockito.Mockito.times;
import static testhelpers.MockMoviesHelper.getPopularMovies;
import static testhelpers.MockMoviesHelper.getTopMovies;

@RunWith(MockitoJUnitRunner.class)
public class MoviesPresenterTest {

    @Mock
    MoviesRepository moviesRepository;

    @Mock
    MoviesView view;

    MoviesPresenter moviesPresenter;
    ArrayList<Movie> popularMovies = getPopularMovies();
    ArrayList<Movie> morePopularMovies = getPopularMovies();
    ArrayList<Movie> topMovies = getTopMovies();

    @Before
    public void initial() {
        Mockito.when(moviesRepository.hasMoreMoviesToDownload())
                .thenReturn(true);
        Mockito.when(moviesRepository.getMovies(SortMovies.POPULAR))
                .thenReturn(Observable.fromArray(popularMovies));
        Mockito.when(moviesRepository.getMoreMovies(SortMovies.POPULAR))
                .thenReturn(Observable.fromArray(morePopularMovies));
        Mockito.when(moviesRepository.getMovies(SortMovies.TOP_RATED))
                .thenReturn(Observable.fromArray(topMovies));

        moviesPresenter = new MoviesPresenter(moviesRepository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
    }

    @Test
    public void testDisplayBooks() {
        moviesPresenter.loadMovies();

        Mockito.verify(view).displayProgressBar();
        Mockito.verify(view).hideProgressBar();
        Mockito.verify(view).hideEmptyLayout();
        Mockito.verify(view).displayMovies(popularMovies);
    }

    @Test
    public void testDisplayError() {
        Mockito.when(moviesRepository.getMovies(SortMovies.POPULAR))
                .thenReturn(Observable.error(new Throwable()));

        moviesPresenter.loadMovies();

        Mockito.verify(view).displayProgressBar();
        Mockito.verify(view).hideProgressBar();
        Mockito.verify(view).displayErrorWithDownloading();
    }

    @Test
    public void testDisplayEmptyLayout() {
        Mockito.when(moviesRepository.getMovies(SortMovies.POPULAR))
                .thenReturn(Observable.fromArray(new ArrayList<>()));

        moviesPresenter.loadMovies();

        Mockito.verify(view).displayProgressBar();
        Mockito.verify(view).hideProgressBar();
        Mockito.verify(view).displayEmptyLayout();
    }

    @Test
    public void testDownloadWithSortChange() {
        moviesPresenter.loadMovies();
        moviesPresenter.setSort(SortMovies.TOP_RATED);
        moviesPresenter.loadMovies();

        Mockito.verify(view).displayMovies(popularMovies);
        Mockito.verify(view, times(2)).displayProgressBar();
        Mockito.verify(view, times(2)).hideProgressBar();
        Mockito.verify(view).displayMovies(topMovies);
        Mockito.verify(view).clearRecyclerView();
    }

    @Test
    public void testDownloadTwiceSameSort() {
        moviesPresenter.loadMovies();
        moviesPresenter.loadMovies();

        Mockito.verify(view).displayMovies(popularMovies);
        Mockito.verify(view).displayMovies(morePopularMovies);
    }

    @Test
    public void testNoCrashWithoutView() {
        moviesPresenter.dropView();
        moviesPresenter.loadMovies();
        moviesPresenter.loadMovies();
    }
}