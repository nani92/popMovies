package eu.napcode.popmovies.persistance;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import javax.inject.Inject;

import eu.napcode.popmovies.provider.FavouriteMoviesProvider;

import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.AUTHORITY;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.TABLE_FAVORITE_MOVIES;

public class DatabaseHelper {

    private final ContentResolver contentResolver;
    private Uri uri = Uri.parse(AUTHORITY + TABLE_FAVORITE_MOVIES);

    @Inject
    public DatabaseHelper(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    protected void saveMovieData(ContentValues contentValues) {
        Uri uri = this.uri;
        this.contentResolver.insert(uri, contentValues);
    }

    protected Cursor getAll() {
        return contentResolver.query(this.uri, null, null, null, null);
    }

    protected Cursor getById(int id) {
        Uri uri = Uri.parse(AUTHORITY + TABLE_FAVORITE_MOVIES + "/" + id);
        Log.d("Natalia", uri.toString());

        return contentResolver.query(uri, null, null, null, null);
    }
}
