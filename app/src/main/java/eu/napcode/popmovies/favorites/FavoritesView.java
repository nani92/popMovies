package eu.napcode.popmovies.favorites;

import java.util.List;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.utils.archbase.BaseView;

public interface FavoritesView extends BaseView {
    void displayFavorites(List<Movie> favorites);
}
