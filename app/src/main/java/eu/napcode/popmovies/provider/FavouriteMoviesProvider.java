package eu.napcode.popmovies.provider;


import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;

public class FavouriteMoviesProvider extends SQLiteContentProviderImpl {

    public static final String AUTHORITY = "content://eu.napcode.popmovies/";

    public static final String TABLE_FAVORITE_MOVIES = "favorite";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_POSTER = "poster";
    public static final String COLUMN_ORIGINAL_TITLE= "original_title";
    public static final String COLUMN_AVERAGE_VOTE = "vote";
    public static final String COLUMN_PLOT = "plot";
}
