package eu.napcode.popmovies.dependency.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import eu.napcode.popmovies.favorites.FavoritesActivity;
import eu.napcode.popmovies.moviedetails.DetailsActivity;
import eu.napcode.popmovies.movies.MainActivity;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector
    MainActivity bindMainActivity();

    @ContributesAndroidInjector
    DetailsActivity bindDetailsActivity();

    @ContributesAndroidInjector
    FavoritesActivity bindFavoritesActivity();
}
