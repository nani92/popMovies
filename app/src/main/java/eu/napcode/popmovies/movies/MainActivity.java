package eu.napcode.popmovies.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.api.ApiConstants;
import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.api.UrlUtils;

public class MainActivity extends AppCompatActivity {

    @Inject MoviesPresenter moviesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);

        Log.d("Natalia", "");
    }
}
