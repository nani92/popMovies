package eu.napcode.popmovies.utils.persistance;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import javax.inject.Inject;

import static eu.napcode.popmovies.database.FavoritesDatabase.COLUMN_ID;
import static eu.napcode.popmovies.database.FavoritesDatabase.TABLE_FAVORITE_MOVIES;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.AUTHORITY;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.CONTENT_URI;

public class DatabaseHelper {

    private final ContentResolver contentResolver;
    private Uri uri = CONTENT_URI;

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
        Uri uri = Uri.parse(this.uri + "/" + id);

        return contentResolver.query(uri, null, null, null, null);
    }
}
