package eu.napcode.popmovies.provider;

import android.net.Uri;

import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;

public class FavouriteMoviesProvider extends SQLiteContentProviderImpl {

    private static final String AUTHORITY = "content://eu.napcode.popmovies/";

    private static final String TABLE_FAVOURITE_MOVIES = "favourite movies";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";

    public static final Uri FAVOURITE_MOVIES = Uri.parse(AUTHORITY)
            .buildUpon()
            .appendPath(TABLE_FAVOURITE_MOVIES)
            .build();
}
