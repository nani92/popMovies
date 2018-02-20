package eu.napcode.popmovies.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import eu.napcode.popmovies.api.ApiUtils;
import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.api.responsemodel.ResponseMoviePage;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.model.MoviesMapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.common.collect.*;

import java.util.List;

@Singleton
public class MoviesRepository {

    private MoviesService moviesService;

    private List<Movie> movies;

    @Inject
    public MoviesRepository(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    public void getMovies(final GetMoviesListener getMoviesListener) {

        if (this.movies == null || this.movies.isEmpty()) {
            downloadMovies(getMoviesListener);
        } else {
            getMoviesListener.moviesReceived(this.movies);
        }
    }

    private void downloadMovies(final GetMoviesListener listener) {
        this.moviesService
                .getMovies(ApiUtils.URL_TOP_RATED, ApiUtils.TMDB_API_KEY)
                .enqueue(new Callback<ResponseMoviePage>() {

                    @Override
                    public void onResponse(Call<ResponseMoviePage> call, Response<ResponseMoviePage> response) {
                        MoviesRepository.this.movies = Lists.transform(response.body().getResponseMovies(), MoviesMapper.responseToMovie);
                        listener.moviesReceived(MoviesRepository.this.movies);
                    }

                    @Override
                    public void onFailure(Call<ResponseMoviePage> call, Throwable t) {
                        listener.moviesFailure();
                    }
                });
    }

    public Movie getMovieById(int movieId) {

        for (Movie movie : this.movies) {

            if (movie.getId() == movieId) {
                return movie;
            }
        }

        return null;
    }
}
