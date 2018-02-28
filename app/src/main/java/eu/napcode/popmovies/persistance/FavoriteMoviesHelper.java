package eu.napcode.popmovies.persistance;


import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.model.Movie;

import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_ID;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_TITLE;

public class FavoriteMoviesHelper {

    private final DatabaseHelper databaseHelper;

    @Inject
    public FavoriteMoviesHelper(DatabaseHelper databaseReader) {
        this.databaseHelper = databaseReader;
    }

    public Movie getFavoriteMovie(int id) {
        Cursor cursor = this.databaseHelper.getById(id);

        if (cursor.moveToFirst()) {
            Movie movie = getMovie(cursor);
            cursor.close();

            return movie;
        }

        return null;
    }

    private Movie getMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));

        return movie;
    }

    public void saveMovie(Movie movie) {
        this.databaseHelper.saveMovieData(createContentValuesFromMovie(movie));
    }

    private ContentValues createContentValuesFromMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, movie.getId());
        values.put(COLUMN_TITLE, movie.getTitle());

        return values;
    }

    public void removeMovieById(int id) {
        this.databaseHelper.removeById(id);
    }

    public List<Movie> getAll() {
        Cursor cursor = this.databaseHelper.getAll();
        List<Movie> favoriteMovies = new ArrayList<>();

        while (cursor.moveToNext()) {
            favoriteMovies.add(getMovie(cursor));
        }

        return favoriteMovies;
    }
}
