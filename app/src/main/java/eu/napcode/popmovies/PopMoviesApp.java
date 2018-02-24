package eu.napcode.popmovies;


import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import eu.napcode.popmovies.dependency.component.DaggerAppComponent;


public class PopMoviesApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        setupDagger();
    }

    private void setupDagger() {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return this.dispatchingAndroidInjector;
    }
}
