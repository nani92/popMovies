package eu.napcode.popmovies.dependency.module;

import dagger.Binds;
import dagger.Module;
import eu.napcode.popmovies.repository.MoviesRepository;
import eu.napcode.popmovies.repository.MoviesRepositoryImpl;
import eu.napcode.popmovies.repository.VideosRepository;
import eu.napcode.popmovies.repository.VideosRepositoryImpl;

@Module
public interface RepositoryModule {

    @Binds
    MoviesRepository moviesRepository(MoviesRepositoryImpl moviesRepository);

    @Binds
    VideosRepository videosRepository(VideosRepositoryImpl videosRepository);
}
