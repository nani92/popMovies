package eu.napcode.popmovies.api;

import eu.napcode.popmovies.api.responsemodel.ResponseMoviePage;
import eu.napcode.popmovies.api.responsemodel.reviews.ResponseReviewsPage;
import eu.napcode.popmovies.api.responsemodel.videos.ResponseVideos;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static eu.napcode.popmovies.utils.ApiUtils.URL_PARAM_API_KEY;

public interface MoviesService {

    @GET("{sort}")
    Observable<ResponseMoviePage> getMovies(@Path("sort") String sort, @Query(URL_PARAM_API_KEY) String apiKey);

    @GET("{sort}")
    Observable<ResponseMoviePage> getNextMovies(@Path("sort") String sort, @Query("page") int page, @Query(URL_PARAM_API_KEY) String apiKey);

    @GET("{id}/videos")
    Observable<ResponseVideos> getVideos(@Path("id") int id, @Query(URL_PARAM_API_KEY) String apiKey);

    @GET("{id}/reviews")
    Observable<ResponseReviewsPage> getReviews(@Path("id") int id, @Query(URL_PARAM_API_KEY) String apiKey);
}
