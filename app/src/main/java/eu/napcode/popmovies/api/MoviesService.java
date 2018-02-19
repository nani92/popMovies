package eu.napcode.popmovies.api;

import eu.napcode.popmovies.api.model.MoviePage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static eu.napcode.popmovies.api.ApiConstants.URL_PARAM_API_KEY;
import static eu.napcode.popmovies.api.ApiConstants.URL_POPULAR;

public interface MoviesService {

    @GET(URL_POPULAR)
    Call<MoviePage> getMoviesByPopularity(@Query(URL_PARAM_API_KEY) String apiKey);
}
