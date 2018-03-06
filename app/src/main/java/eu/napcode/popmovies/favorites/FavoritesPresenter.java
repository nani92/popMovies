package eu.napcode.popmovies.favorites;

import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.utils.archbase.PresenterBundle;

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

    @Override
    public PresenterBundle saveState() {
        return null;
    }

    @Override
    public void restoreState(PresenterBundle presenterBundle) {

    }

    void loadFavorites() {
        List<Movie> movies = this.moviesRepository.getFavorites();

        if (movies.isEmpty()) {
            this.favoritesView.displayEmptyLayout();
        } else {
            this.favoritesView.hideEmptyLayout();
            this.favoritesView.displayFavorites(movies);
        }
    }
}
