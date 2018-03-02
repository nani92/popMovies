package eu.napcode.popmovies.utils.persistance;


import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.model.Movie;

import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_AVERAGE_VOTE;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_ID;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_ORIGINAL_TITLE;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_PLOT;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_POSTER;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_RELEASE_DATE;
import static eu.napcode.popmovies.provider.FavouriteMoviesProvider.COLUMN_TITLE;

public class FavoriteMoviesHelper {

    private final DatabaseHelper databaseHelper;

    @Inject
    public FavoriteMoviesHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
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

    //TODO move to separatedMapper
    private Movie getMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE)));
        movie.setPosterBitmap(DatabaseBitmapHelper.getBitmapFromBytes(cursor.getBlob(cursor.getColumnIndex(COLUMN_POSTER))));
        movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_TITLE)));
        movie.setPlot(cursor.getString(cursor.getColumnIndex(COLUMN_PLOT)));
        movie.setVoteAverage(cursor.getString(cursor.getColumnIndex(COLUMN_AVERAGE_VOTE)));

        return movie;
    }

    public void saveMovie(Movie movie) {
        this.databaseHelper.saveMovieData(createContentValuesFromMovie(movie));
    }

    private ContentValues createContentValuesFromMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, movie.getId());
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(COLUMN_POSTER, DatabaseBitmapHelper.getBytesFromBitmap(movie.getPosterBitmap()));
        values.put(COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(COLUMN_PLOT, movie.getPlot());
        values.put(COLUMN_AVERAGE_VOTE, movie.getVoteAverage());

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
