package eu.napcode.popmovies.movies;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MoviesPresenterTest {

    @Test
    public void init() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void shouldDisplayBooks() {
        //given
        MoviesView view = new MockMoviesView();
        MoviesRepository repository = new MockMoviesRepository();

        //when
        MoviesPresenter moviesPresenter = new MoviesPresenter(repository, new MockRxSchedulers());
        moviesPresenter.attachView(view);
        moviesPresenter.loadMovies();

        //then
        Assert.assertEquals(true, ((MockMoviesView) view).moviesDisplayed);
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

        public boolean moviesDisplayed;

        @Override
        public void displayMovies(List<Movie> movies) {
            moviesDisplayed = true;
        }

        @Override
        public void clearRecyclerView() {

        }

        @Override
        public void displayProgressBar() {

        }

        @Override
        public void hideProgressBar() {

        }

        @Override
        public void displayErrorWithDownloading() {

        }

        @Override
        public void hideEmptyLayout() {

        }

        @Override
        public void displayEmptyLayout() {

        }
    }

    public class MockMoviesRepository implements MoviesRepository {

        @Override
        public Observable<List<Movie>> getMovies(SortMovies sortMovies) {
            return Observable.fromArray(new ArrayList<>());
        }

        @Override
        public boolean isMoreMoviesToDownload() {
            return false;
        }
    }
}