package eu.napcode.popmovies.dependency.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.napcode.popmovies.api.ApiConstants;
import eu.napcode.popmovies.api.MoviesService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    MoviesService provideMoviesService() {
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.TMDB_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build()
                .create(MoviesService.class);
    }

    private OkHttpClient getHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }
}