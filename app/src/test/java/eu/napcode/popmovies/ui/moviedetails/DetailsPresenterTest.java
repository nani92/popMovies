package eu.napcode.popmovies.ui.moviedetails;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.model.Video;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.repository.VideosRepository;
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class DetailsPresenterTest {

    @Mock
    MoviesRepository moviesRepository;
    @Mock
    VideosRepository videosRepository;
    @Mock
    DetailsView detailsView;

    @Mock
    Bitmap bitmap;

    private DetailsPresenter detailsPresenter;
    private Movie movie = getFullMovie();
    private List<Video> videos = getVideos();

    @Before
    public void init() {
        detailsPresenter = new DetailsPresenter(moviesRepository, videosRepository, new MockRxSchedulers());
        detailsPresenter.attachView(detailsView);

        Mockito.when(videosRepository.getVideos(movie.getId()))
                .thenReturn(Observable.fromArray(videos));
    }

    public static Movie getFullMovie() {
        Movie movie = new Movie();
        movie.setId(0);
        movie.setTitle("Title");
        movie.setPlot("Plot");
        movie.setReleaseDate("20-02-2019");
        movie.setOriginalTitle("Original title");
        movie.setPosterPath("path");
        movie.setBackdropPath("path");

        return movie;
    }

    private static ArrayList<Video> getVideos() {
        ArrayList<Video> videos = new ArrayList<>();
        videos.add(new Video());

        return videos;
    }

    @Test
    public void testDisplayTitle() {
        this.detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayMovieTitle(movie.getTitle());
    }

    @Test
    public void testDisplayOriginalTitle() {
        this.detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayOriginalTitle(movie.getOriginalTitle());
    }

    @Test
    public void testDisplayReleaseDate() {
        this.detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayReleaseDate(movie.getReleaseDate());
    }

    @Test
    public void testDisplayVote() {
        this.detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayVoteAverage(movie.getVoteAverage());
    }

    @Test
    public void testDisplayPlot() {
        this.detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayPlot(movie.getPlot());
    }

    @Test
    public void testDisplayPoster() {
        this.detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayPoster(movie.getPosterPath());
    }

    @Test
    public void testDisplayBackdrop() {
        this.detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayBackdrop(movie.getBackdropPath());
    }

    @Test
    public void testDisplayPosterFromBitmap() {
        movie.setPosterPath("");
        movie.setPosterBitmap(bitmap);
        this.detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayPoster(bitmap);
    }

    @Test
    public void testDisplayFav() {
        Mockito.when(moviesRepository.isMovieFavorite(movie.getId()))
                .thenReturn(true);

        detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayFavoriteMovie();
    }

    @Test
    public void testDisplayNotFav() {
        Mockito.when(moviesRepository.isMovieFavorite(movie.getId()))
                .thenReturn(false);

        detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayNotFavoriteMovie();
    }

    @Test
    public void testDisplayVideos() {
        detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView).displayVideos(videos);
    }

    @Test
    public void testDisplayChangeToFav() {
        Mockito.when(moviesRepository.isMovieFavorite(movie.getId()))
                .thenReturn(false)
                .thenReturn(true);

        detailsPresenter.setMovie(movie);
        detailsPresenter.favoriteClicked();

        Mockito.verify(detailsView).displayNotFavoriteMovie();
        Mockito.verify(detailsView).displayFavoriteMovie();
    }

    @Test
    public void testErrorWithDownloadVideos() {
        Mockito.when(videosRepository.getVideos(movie.getId()))
                .thenReturn(Observable.error(new Throwable()));

        detailsPresenter.setMovie(movie);

        Mockito.verify(detailsView, Mockito.times(0)).displayVideos(null);
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
}