package eu.napcode.popmovies.dependency.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.napcode.popmovies.api.ApiConstants;
import eu.napcode.popmovies.api.MoviesService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton @Provides
    MoviesService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.TMDB_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesService.class);
    }
}
