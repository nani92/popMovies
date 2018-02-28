package eu.napcode.popmovies.favorites;


import javax.inject.Inject;

import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.utils.archbase.BasePresenter;

public class FavoritesPresenter implements BasePresenter<FavoritesView> {

    private FavoritesView favoritesView;
    private MoviesRepository moviesRepository;

    @Inject
    public FavoritesPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void attachView(FavoritesView view) {
        this.favoritesView = view;
    }

    @Override
    public void dropView() {
        this.favoritesView = null;
    }

    public void loadFavorites() {
        this.favoritesView.displayFavorites(this.moviesRepository.getFavorites());
    }
}
