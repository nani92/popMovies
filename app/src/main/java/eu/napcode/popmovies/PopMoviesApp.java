package eu.napcode.popmovies;


import android.app.Application;

import eu.napcode.popmovies.dependency.component.DaggerNetworkingComponent;
import eu.napcode.popmovies.dependency.component.NetworkingComponent;
import eu.napcode.popmovies.dependency.module.AppModule;
import eu.napcode.popmovies.dependency.module.NetworkingModule;

import static eu.napcode.popmovies.api.ApiConstants.TMDB_URL_BASE;

public class PopMoviesApp extends Application {

    private NetworkingComponent networkingComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        
        setupNetworkingComponent();
    }

    private void setupNetworkingComponent() {
        this.networkingComponent = DaggerNetworkingComponent.builder()
                .appModule(new AppModule(this))
                .networkingModule(new NetworkingModule(TMDB_URL_BASE))
                .build();
    }
}
