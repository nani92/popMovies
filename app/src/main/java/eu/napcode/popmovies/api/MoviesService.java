package eu.napcode.popmovies.api;

import eu.napcode.popmovies.api.responsemodel.ResponseMoviePage;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static eu.napcode.popmovies.api.ApiUtils.URL_PARAM_API_KEY;

public interface MoviesService {

    @GET("{sort}")
    Observable<ResponseMoviePage> getMovies(@Path("sort") String sort, @Query(URL_PARAM_API_KEY) String apiKey);

    @GET("{sort}")
    Observable<ResponseMoviePage> getNextMovies(@Path("sort") String sort, @Query("page") int page, @Query(URL_PARAM_API_KEY) String apiKey);
}
