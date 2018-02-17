package eu.napcode.popmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import eu.napcode.popmovies.api.UrlUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Natalia", "api key " + BuildConfig.TMDB_API_KEY);
        UrlUtils.getPopularMoviesUrl();
    }
}
