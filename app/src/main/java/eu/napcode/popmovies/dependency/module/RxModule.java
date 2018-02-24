package eu.napcode.popmovies.dependency.module;

import dagger.Module;
import dagger.Provides;
import eu.napcode.popmovies.utils.rx.AppSchedulers;
import eu.napcode.popmovies.utils.rx.RxSchedulers;

@Module
public class RxModule {

    @Provides
    RxSchedulers provideRxSchedulers() {
        return new AppSchedulers();
    }
}
