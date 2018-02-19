package eu.napcode.popmovies.dependency.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import eu.napcode.popmovies.PopMoviesApp;
import eu.napcode.popmovies.dependency.module.ActivityModule;
import eu.napcode.popmovies.dependency.module.AppModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(PopMoviesApp popMoviesApp);
}
