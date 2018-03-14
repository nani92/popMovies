package eu.napcode.popmovies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import eu.napcode.popmovies.database.FavoritesDatabase;

import static eu.napcode.popmovies.database.FavoritesDatabase.COLUMN_ID;
import static eu.napcode.popmovies.database.FavoritesDatabase.TABLE_FAVORITE_MOVIES;

public class FavouriteMoviesProvider extends ContentProvider {

    private FavoritesDatabase db;

    public static final String AUTHORITY = "eu.napcode.popmovies";
    public static final int FAVORITES = 100;
    public static final int FAVORITE_MOVIE = 101;

    private static final String FAVORITES_BASE_PATH = "favorites";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + FAVORITES_BASE_PATH);

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, FAVORITES_BASE_PATH, FAVORITES);
        uriMatcher.addURI(AUTHORITY, FAVORITES_BASE_PATH + "/#", FAVORITE_MOVIE);
    }

    @Override
    public boolean onCreate() {
        this.db = new FavoritesDatabase(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TABLE_FAVORITE_MOVIES);

        int uriType = uriMatcher.match(uri);

        switch (uriType) {
            case FAVORITES:
                break;
            case FAVORITE_MOVIE:
                builder.appendWhere(COLUMN_ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Query unknown URI " + uri);

        }

        Cursor cursor = builder.query(db.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = uriMatcher.match(uri);

        if (uriType != FAVORITES) {
            throw new IllegalArgumentException("Insert unknown URI " + uri);
        }

        SQLiteDatabase database = db.getWritableDatabase();
        long id = database.insert(TABLE_FAVORITE_MOVIES, null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(FAVORITES_BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase database = db.getWritableDatabase();
        int rowsAffected = 0;

        switch (uriType) {
            case FAVORITE_MOVIE:
                rowsAffected = database.delete(TABLE_FAVORITE_MOVIES, COLUMN_ID + "=" + uri.getLastPathSegment(), null);
                break;
            case FAVORITES:
                rowsAffected = database.delete(TABLE_FAVORITE_MOVIES, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Delete unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
