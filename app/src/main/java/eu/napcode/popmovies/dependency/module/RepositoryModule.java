package eu.napcode.popmovies.dependency.module;

import dagger.Binds;
import dagger.Module;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.repository.MoviesRepositoryImpl;

@Module
public interface RepositoryModule {

    @Binds
    MoviesRepository moviesRepository(MoviesRepositoryImpl moviesRepository);
}
