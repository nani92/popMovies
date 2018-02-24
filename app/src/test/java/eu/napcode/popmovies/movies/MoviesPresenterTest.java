package eu.napcode.popmovies.movies;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MoviesPresenterTest {

    @Test
    public void testDisplayBooks() {
        //given
        MoviesView view = new MockMoviesView();
        MoviesRepository repository = new MockMoviesRepository(MockMoviesRepository.Action.LIST_WITH_MOVIES);

        //when
        MoviesPresenter moviesPresenter = new MoviesPresenter(repository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        //then
        Assert.assertEquals(true, ((MockMoviesView) view).moviesDisplayed);
        Assert.assertEquals(true, ((MockMoviesView) view).emptyHidden);
        Assert.assertEquals(false, ((MockMoviesView) view).isEmptyDisplaying);
    }

    @Test
    public void testDisplayError() {
        //given
        MoviesView view = new MockMoviesView();
        MoviesRepository repository = new MockMoviesRepository(MockMoviesRepository.Action.ERROR);

        //when
        MoviesPresenter moviesPresenter = new MoviesPresenter(repository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        //then
        Assert.assertEquals(true, ((MockMoviesView) view).errorDisplayed);
    }

    @Test
    public void testDisplayEmptyLayout() {
        //given
        MoviesView view = new MockMoviesView();
        MoviesRepository repository = new MockMoviesRepository(MockMoviesRepository.Action.LIST_EMPTY);

        //when
        MoviesPresenter moviesPresenter = new MoviesPresenter(repository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        //then
        Assert.assertEquals(true, ((MockMoviesView) view).emptyDisplayed);
        Assert.assertEquals(true, ((MockMoviesView) view).isEmptyDisplaying);
    }

    @Test
    public void testManagingProgressBarForListReturned() {
        MoviesView view = new MockMoviesView();
        MoviesRepository repository = new MockMoviesRepository(MockMoviesRepository.Action.LIST_WITH_MOVIES);

        MoviesPresenter moviesPresenter = new MoviesPresenter(repository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        Assert.assertEquals(true, ((MockMoviesView) view).progressBarDisplayed);
        Assert.assertEquals(true, ((MockMoviesView) view).progressBarHidden);
        Assert.assertEquals(false, ((MockMoviesView) view).isProgressBarDisplaying);
    }

    @Test
    public void testManagingProgressBarForError() {
        MoviesView view = new MockMoviesView();
        MoviesRepository repository = new MockMoviesRepository(MockMoviesRepository.Action.LIST_WITH_MOVIES);

        MoviesPresenter moviesPresenter = new MoviesPresenter(repository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        Assert.assertEquals(true, ((MockMoviesView) view).progressBarDisplayed);
        Assert.assertEquals(true, ((MockMoviesView) view).progressBarHidden);
        Assert.assertEquals(false, ((MockMoviesView) view).isProgressBarDisplaying);
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

        @Override
        public void displayMovies(List<Movie> movies) {
            moviesDisplayed = true;
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

    public static class MockMoviesRepository implements MoviesRepository {

        public enum Action {
            LIST_WITH_MOVIES,
            LIST_EMPTY,
            ERROR
        }

        private Action action;

        public MockMoviesRepository(Action action) {
            this.action = action;
        }

        @Override
        public Observable<List<Movie>> getMovies(SortMovies sortMovies) {

            switch (this.action) {
                case LIST_WITH_MOVIES:
                    return Observable.fromArray(new ArrayList<>(Arrays.asList(new Movie())));
                case LIST_EMPTY:
                    return Observable.fromArray(new ArrayList<>());
                case ERROR:
                    return Observable.error(new Throwable());
                default:
                    return Observable.fromArray(new ArrayList<>());
            }
        }

        @Override
        public boolean isMoreMoviesToDownload() {
            return false;
        }
    }
}