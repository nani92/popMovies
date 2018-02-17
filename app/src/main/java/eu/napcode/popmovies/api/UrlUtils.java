package eu.napcode.popmovies.api;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

import eu.napcode.popmovies.BuildConfig;

public class UrlUtils {
    private static final String TMDB_URL_BASE = "http://api.themoviedb.org/3/movie/";
    private static final String URL_POPULAR = "popular";
    private static final String URL_TOP_RATED = "top_rated";
    private static final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;

    private static final String URL_PARAM_API_KEY = "api_key";

    public static URL getPopularMoviesUrl() {
        Uri uri = getBaseUriBuilder()
                .appendPath(URL_POPULAR)
                .appendQueryParameter(URL_PARAM_API_KEY, TMDB_API_KEY)
                .build();

        return getUrlFromUri(uri);
    }

    public static URL getTopRatedMoviesUrl() {
        Uri uri = getBaseUriBuilder()
                .appendPath(URL_TOP_RATED)
                .appendQueryParameter(URL_PARAM_API_KEY, TMDB_API_KEY)
                .build();

        return getUrlFromUri(uri);
    }

    private static Uri.Builder getBaseUriBuilder() {
        return Uri.parse(TMDB_URL_BASE).buildUpon();
    }

    private static URL getUrlFromUri(Uri uri) {
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
