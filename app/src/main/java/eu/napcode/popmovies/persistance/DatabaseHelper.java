package eu.napcode.popmovies.persistance;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import javax.inject.Inject;

import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.AUTHORITY;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_ID;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.TABLE_FAVORITE_MOVIES;

public class DatabaseHelper {

    private final ContentResolver contentResolver;
    private Uri uri = Uri.parse(AUTHORITY + TABLE_FAVORITE_MOVIES);

    @Inject
    public DatabaseHelper(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    protected void saveMovieData(ContentValues contentValues) {
        this.contentResolver.insert(this.uri, contentValues);
    }

    protected void removeById(int id) {
        String[] selectionArgs = {String.valueOf(id)};

        this.contentResolver.delete(this.uri, COLUMN_ID + " = ?", selectionArgs);
    }

    protected Cursor getAll() {
        return contentResolver.query(this.uri, null, null, null, null);
    }

    protected Cursor getById(int id) {
        Uri uri = Uri.parse(AUTHORITY + TABLE_FAVORITE_MOVIES + "/" + id);

        return contentResolver.query(uri, null, null, null, null);
    }
}
