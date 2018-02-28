package eu.napcode.popmovies.provider;

import android.net.Uri;

import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;

public class FavouriteMoviesProvider extends SQLiteContentProviderImpl {

    public static final String AUTHORITY = "content://eu.napcode.popmovies/";

    public static final String TABLE_FAVORITE_MOVIES = "favorite";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RELEASE_DATE = "releasedate";

    public static final Uri FAVOURITE_MOVIES = Uri.parse(AUTHORITY)
            .buildUpon()
            .appendPath(TABLE_FAVORITE_MOVIES)
            .build();
}
