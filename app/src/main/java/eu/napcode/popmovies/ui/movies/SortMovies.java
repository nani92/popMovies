package eu.napcode.popmovies.ui.movies;

import eu.napcode.popmovies.utils.ApiUtils;

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
