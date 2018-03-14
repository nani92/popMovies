package eu.napcode.popmovies.ui.moviedetails;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.model.Video;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.repository.VideosRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import testhelpers.MockRxSchedulers;

import static testhelpers.MockMoviesHelper.getFullMovie;
import static testhelpers.MockVideoHelper.getVideos;

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
        Mockito.when(moviesRepository.favoriteChange(movie))
                .thenReturn(Completable.complete());
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
}