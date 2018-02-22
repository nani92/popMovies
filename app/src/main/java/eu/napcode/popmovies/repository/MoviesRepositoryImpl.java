package eu.napcode.popmovies.repository;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import eu.napcode.popmovies.api.ApiUtils;
import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.api.responsemodel.ResponseMoviePage;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.model.MoviesMapper;
import eu.napcode.popmovies.movies.SortMovies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MoviesRepositoryImpl implements MoviesRepository {

    private MoviesService moviesService;

    private List<Movie> movies = new ArrayList<>();
    private SortMovies lastSortMovies = SortMovies.NONE;
    private int downloadedMoviesPage = 0;
    private boolean isThereNextMoviesPageToDownload = true;
    private boolean isDownloading = false;

    @Inject
    public MoviesRepositoryImpl(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    public void getMovies(DownloadMoviesListener downloadMoviesListener, SortMovies sortMovies) {

        if (sortMovies != lastSortMovies) {
            this.lastSortMovies = sortMovies;
            this.movies.clear();

            downloadMovies(downloadMoviesListener, sortMovies);
        } else {
            downloadMoreMovies(downloadMoviesListener, sortMovies);
        }

    }

    private void downloadMovies(final DownloadMoviesListener listener, SortMovies sortMovies) {
        this.moviesService
                .getMovies(SortMovies.getUrlPathForSort(sortMovies), ApiUtils.TMDB_API_KEY)
                .enqueue(getResponseMoviesCallback(listener));
        this.isDownloading = true;
    }

    private Callback<ResponseMoviePage> getResponseMoviesCallback(final DownloadMoviesListener listener) {
        return new Callback<ResponseMoviePage>() {

            @Override
            public void onResponse(Call<ResponseMoviePage> call, Response<ResponseMoviePage> response) {
                MoviesRepositoryImpl.this.isDownloading = false;

                List<Movie> movies = Lists.transform(response.body().getResponseMovies(), MoviesMapper.responseToMovie);
                MoviesRepositoryImpl.this.movies.addAll(movies);
                setMoviesPageVars(response);

                listener.moviesReceived(movies);
            }

            @Override
            public void onFailure(Call<ResponseMoviePage> call, Throwable t) {
                MoviesRepositoryImpl.this.isDownloading = false;

                listener.moviesFailure();
            }
        };
    }

    private void setMoviesPageVars( Response<ResponseMoviePage> response) {
        this.downloadedMoviesPage = response.body().getPage();
        this.isThereNextMoviesPageToDownload =
                this.downloadedMoviesPage < response.body().getTotalPages();
    }

    private void downloadMoreMovies(final DownloadMoviesListener listener, SortMovies sortMovies) {
        this.moviesService
                .getNextMovies(SortMovies.getUrlPathForSort(sortMovies), this.downloadedMoviesPage + 1, ApiUtils.TMDB_API_KEY)
                .enqueue(getResponseMoviesCallback(listener));
        this.isDownloading = true;
    }

    public Movie getMovieById(int movieId) {

        for (Movie movie : this.movies) {

            if (movie.getId() == movieId) {
                return movie;
            }
        }

        return null;
    }

    public boolean shouldDownloadMoreMovies() {
        return this.isThereNextMoviesPageToDownload && !this.isDownloading;
    }
}
