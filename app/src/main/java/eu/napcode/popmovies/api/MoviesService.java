package eu.napcode.popmovies.api;

import eu.napcode.popmovies.api.responsemodel.ResponseMoviePage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static eu.napcode.popmovies.api.ApiUtils.URL_PARAM_API_KEY;
import static eu.napcode.popmovies.api.ApiUtils.URL_POPULAR;

public interface MoviesService {

    @GET(URL_POPULAR)
    Call<ResponseMoviePage> getMoviesByPopularity(@Query(URL_PARAM_API_KEY) String apiKey);
}
