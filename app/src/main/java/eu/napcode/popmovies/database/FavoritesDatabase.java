package eu.napcode.popmovies.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesDatabase extends SQLiteOpenHelper {
    private static String DB_NAME = "favorites";
    private static int DB_V = 1;

    public static final String TABLE_FAVORITE_MOVIES = "favorites";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_POSTER = "poster";
    public static final String COLUMN_ORIGINAL_TITLE = "original_title";
    public static final String COLUMN_AVERAGE_VOTE = "vote";
    public static final String COLUMN_PLOT = "plot";

    private static final String CREATE_TABLE_FAVORITES = "create table " + TABLE_FAVORITE_MOVIES
            + " (" + COLUMN_ID + " integer primary key, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_RELEASE_DATE + " text not null, "
            + COLUMN_POSTER + " BLOB, "
            + COLUMN_ORIGINAL_TITLE + " text, "
            + COLUMN_AVERAGE_VOTE + " text, "
            + COLUMN_PLOT + " text);";

    private static final String DB_SCHEMA = CREATE_TABLE_FAVORITES;

    public FavoritesDatabase(Context context) {
        super(context, DB_NAME, null, DB_V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_MOVIES);
        onCreate(db);
    }
}
