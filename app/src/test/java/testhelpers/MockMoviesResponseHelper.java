package testhelpers;

import java.util.ArrayList;
import java.util.List;

import eu.napcode.popmovies.api.responsemodel.ResponseReview;
import eu.napcode.popmovies.api.responsemodel.ResponseMoviePage;

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

    public static List<ResponseReview> getResponseMovieList() {
        List<ResponseReview> responseMovies = new ArrayList<>();
        responseMovies.add(getResponseMovie());
        responseMovies.add(getResponseMovie());

        return responseMovies;
    }

    public static ResponseReview getResponseMovie() {
        ResponseReview responseMovie = new ResponseReview();

        return responseMovie;
    }

}
