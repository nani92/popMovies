package eu.napcode.popmovies.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import eu.napcode.popmovies.api.ApiConstants;
import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.api.responsemodel.ResponseMoviePage;
import eu.napcode.popmovies.model.MoviesMapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.common.collect.*;

@Singleton
public class MoviesRepository {

    private MoviesService moviesService;

    @Inject
    public MoviesRepository(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    public void getMovies(final GetMoviesListener getMoviesListener) {
        this.moviesService
                .getMoviesByPopularity(ApiConstants.TMDB_API_KEY)
                .enqueue(new Callback<ResponseMoviePage>() {

                    @Override
                    public void onResponse(Call<ResponseMoviePage> call, Response<ResponseMoviePage> response) {
                        getMoviesListener.moviesReceived(
                                Lists.transform(response.body().getResponseMovies(), MoviesMapper.responseToMovie));
                    }

                    @Override
                    public void onFailure(Call<ResponseMoviePage> call, Throwable t) {
                        getMoviesListener.moviesFailure();
                    }
                });
    }
}
