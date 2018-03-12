package eu.napcode.popmovies.ui.movies;

import org.junit.Assert;
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
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class MoviesPresenterTest {

    @Mock
    MoviesRepository moviesRepository;

    @Before
    public void initial() {
        Mockito.when(moviesRepository.hasMoreMoviesToDownload())
                .thenReturn(true);
        Mockito.when(moviesRepository.getMovies(SortMovies.POPULAR))
                .thenReturn(Observable.fromArray(getPopularMovies()));
        Mockito.when(moviesRepository.getMoreMovies(SortMovies.POPULAR))
                .thenReturn(Observable.fromArray(getPopularMovies()));
        Mockito.when(moviesRepository.getMovies(SortMovies.TOP_RATED))
                .thenReturn(Observable.fromArray(getTopMovies()));
    }

    private static ArrayList<Movie> getPopularMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(createMovieWithTitle("Popular 1"));
        movies.add(createMovieWithTitle("Popular 2"));

        return movies;
    }

    private static Movie createMovieWithTitle(String title) {
        Movie movie = new Movie();
        movie.setTitle(title);

        return movie;
    }

    private static ArrayList<Movie> getTopMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(createMovieWithTitle("Top 1"));
        movies.add(createMovieWithTitle("Top 2"));
        movies.add(createMovieWithTitle("Top 3"));
        movies.add(createMovieWithTitle("Top 4"));

        return movies;
    }

    @Test
    public void testDisplayBooks() {
        MoviesView view = new MockMoviesView();

        MoviesPresenter moviesPresenter = new MoviesPresenter(moviesRepository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        Assert.assertEquals(true, ((MockMoviesView) view).moviesDisplayed);
        Assert.assertEquals(true, ((MockMoviesView) view).emptyHidden);
        Assert.assertEquals(false, ((MockMoviesView) view).isEmptyDisplaying);
    }

    @Test
    public void testDisplayError() {
        MoviesView view = new MockMoviesView();
        Mockito.when(moviesRepository.getMovies(SortMovies.POPULAR))
                .thenReturn(Observable.error(new Throwable()));

        MoviesPresenter moviesPresenter = new MoviesPresenter(moviesRepository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        Assert.assertEquals(true, ((MockMoviesView) view).errorDisplayed);
    }

    @Test
    public void testDisplayEmptyLayout() {
        MoviesView view = new MockMoviesView();
        Mockito.when(moviesRepository.getMovies(SortMovies.POPULAR))
                .thenReturn(Observable.fromArray(new ArrayList<>()));

        MoviesPresenter moviesPresenter = new MoviesPresenter(moviesRepository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        Assert.assertEquals(true, ((MockMoviesView) view).emptyDisplayed);
        Assert.assertEquals(true, ((MockMoviesView) view).isEmptyDisplaying);
    }

    @Test
    public void testManagingProgressBarForListReturned() {
        MoviesView view = new MockMoviesView();

        MoviesPresenter moviesPresenter = new MoviesPresenter(moviesRepository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        Assert.assertEquals(true, ((MockMoviesView) view).progressBarDisplayed);
        Assert.assertEquals(true, ((MockMoviesView) view).progressBarHidden);
        Assert.assertEquals(false, ((MockMoviesView) view).isProgressBarDisplaying);
    }

    @Test
    public void testManagingProgressBarForError() {
        MoviesView view = new MockMoviesView();

        MoviesPresenter moviesPresenter = new MoviesPresenter(moviesRepository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        Assert.assertEquals(true, ((MockMoviesView) view).progressBarDisplayed);
        Assert.assertEquals(true, ((MockMoviesView) view).progressBarHidden);
        Assert.assertEquals(false, ((MockMoviesView) view).isProgressBarDisplaying);
    }

    @Test
    public void testDownloadWithSortChange() {
        MoviesView view = new MockMoviesView();

        MoviesPresenter moviesPresenter = new MoviesPresenter(moviesRepository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();
        moviesPresenter.setSort(SortMovies.TOP_RATED);
        moviesPresenter.loadMovies();

        Assert.assertEquals(true, ((MockMoviesView) view).recyclerviewCleared);
    }

    @Test
    public void testDownloadTwiceSameSort() {
        MoviesView view = new MockMoviesView();

        MoviesPresenter moviesPresenter = new MoviesPresenter(moviesRepository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();
        moviesPresenter.loadMovies();

        Assert.assertEquals(false, ((MockMoviesView) view).recyclerviewCleared);
        Assert.assertEquals( getPopularMovies().size() * 2, ((MockMoviesView) view).movies.size());
    }

    public static class MockRxSchedulers implements RxSchedulers {

        @Override
        public Scheduler io() {
            return Schedulers.trampoline();
        }

        @Override
        public Scheduler androidMainThread() {
            return Schedulers.trampoline();
        }
    }

    public static class MockMoviesView implements MoviesView {

        boolean moviesDisplayed;
        boolean errorDisplayed;
        boolean recyclerviewCleared;

        boolean emptyDisplayed;
        boolean emptyHidden;
        boolean isEmptyDisplaying;

        boolean progressBarDisplayed;
        boolean progressBarHidden;
        boolean isProgressBarDisplaying;

        List<Movie> movies = new ArrayList<>();

        @Override
        public void displayMovies(List<Movie> movies) {
            moviesDisplayed = true;

            this.movies.addAll(movies);
        }

        @Override
        public void clearRecyclerView() {
            this.recyclerviewCleared = true;
        }

        @Override
        public void displayProgressBar() {
            this.isProgressBarDisplaying = true;
            this.progressBarDisplayed = true;
        }

        @Override
        public void hideProgressBar() {
            this.isProgressBarDisplaying = false;
            this.progressBarHidden = true;
        }

        @Override
        public void displayErrorWithDownloading() {
            this.errorDisplayed = true;
        }

        @Override
        public void hideEmptyLayout() {
            this.isEmptyDisplaying = false;
            this.emptyHidden = true;
        }

        @Override
        public void displayEmptyLayout() {
            this.isEmptyDisplaying = true;
            this.emptyDisplayed = true;
        }
    }
}