package testhelpers;

import java.util.ArrayList;
import java.util.List;

import eu.napcode.popmovies.api.responsemodel.movie.ResponseMovie;
import eu.napcode.popmovies.api.responsemodel.movie.ResponseMoviePage;

public class MockMoviesResponseHelper {

    public static ResponseMoviePage getResponseMoviesFirstOfTwoPages() {
        ResponseMoviePage responseMoviePage = new ResponseMoviePage();
        responseMoviePage.setTotalPages(2);
        responseMoviePage.setPage(1);
        responseMoviePage.setResponseMovies(getResponseMovieList());

        return responseMoviePage;
    }

    public static ResponseMoviePage getResponseMoviesSecondOfTwoPages() {
        ResponseMoviePage responseMoviePage = new ResponseMoviePage();
        responseMoviePage.setTotalPages(2);
        responseMoviePage.setPage(2);
        responseMoviePage.setResponseMovies(getResponseMovieList());

        return responseMoviePage;
    }

    public static List<ResponseMovie> getResponseMovieList() {
        List<ResponseMovie> responseMovies = new ArrayList<>();
        responseMovies.add(getResponseMovie());
        responseMovies.add(getResponseMovie());

        return responseMovies;
    }

    public static ResponseMovie getResponseMovie() {
        ResponseMovie responseMovie = new ResponseMovie();

        return responseMovie;
    }

}
