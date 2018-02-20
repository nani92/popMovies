package eu.napcode.popmovies.api;


import android.util.Log;

import eu.napcode.popmovies.BuildConfig;

public class ApiUtils {
    public static final String TMDB_URL_BASE = "http://api.themoviedb.org/3/movie/";
    public static final String URL_POPULAR = "popular";

    public static final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String URL_PARAM_API_KEY = "api_key";

    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w185//";
    private static final String BACKDROP_SIZE = "w500//";

    public static String getPosterUrl(String imagePath) {
        return String.format("%s%s%s", POSTER_BASE_URL, POSTER_SIZE, imagePath);
    }

    public static String getBackdropUrl(String imagePath) {
        return String.format("%s%s%s", POSTER_BASE_URL, BACKDROP_SIZE, imagePath);
    }
}
