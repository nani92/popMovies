package eu.napcode.popmovies.movies;

import eu.napcode.popmovies.api.ApiUtils;

public enum SortMovies {
    NONE,
    POPULAR,
    TOP_RATED;

    public static String getUrlPathForSort(SortMovies sortMovies) {

        switch (sortMovies) {
            case POPULAR:
                return ApiUtils.URL_POPULAR;
            case TOP_RATED:
                return ApiUtils.URL_TOP_RATED;
            default:
                return ApiUtils.URL_POPULAR;
        }
    }
}
