package eu.napcode.popmovies.dependency.component;

import javax.inject.Singleton;

import dagger.Component;
import eu.napcode.popmovies.MainActivity;
import eu.napcode.popmovies.dependency.module.AppModule;
import eu.napcode.popmovies.dependency.module.NetworkingModule;

@Singleton
@Component(modules = {AppModule.class, NetworkingModule.class})
public interface NetworkingComponent {
    void inject(MainActivity activity);
}
