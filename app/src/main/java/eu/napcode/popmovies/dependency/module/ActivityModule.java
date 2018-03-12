package eu.napcode.popmovies.dependency.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import eu.napcode.popmovies.ui.favorites.FavoritesActivity;
import eu.napcode.popmovies.ui.moviedetails.DetailsActivity;
import eu.napcode.popmovies.ui.movies.MainActivity;
import eu.napcode.popmovies.ui.reviews.ReviewsActivity;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector
    MainActivity bindMainActivity();

    @ContributesAndroidInjector
    DetailsActivity bindDetailsActivity();

    @ContributesAndroidInjector
    FavoritesActivity bindFavoritesActivity();

    @ContributesAndroidInjector
    ReviewsActivity bindReviewsActivity();
}
