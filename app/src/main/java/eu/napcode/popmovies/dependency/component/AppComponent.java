package eu.napcode.popmovies.dependency.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import eu.napcode.popmovies.PopMoviesApp;
import eu.napcode.popmovies.dependency.module.ActivityModule;
import eu.napcode.popmovies.dependency.module.AppModule;
import eu.napcode.popmovies.dependency.module.RepositoryModule;
import eu.napcode.popmovies.dependency.module.RxModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityModule.class,
        RepositoryModule.class,
        RxModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(PopMoviesApp application);

        AppComponent build();
    }

    void inject(PopMoviesApp application);
}
