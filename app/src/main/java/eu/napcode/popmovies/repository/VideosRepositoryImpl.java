package eu.napcode.popmovies.repository;

import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.model.Video;
import eu.napcode.popmovies.model.VideosMapper;
import eu.napcode.popmovies.utils.ApiUtils;
import io.reactivex.Observable;

public class VideosRepositoryImpl implements VideosRepository {

    private final MoviesService moviesService;

    @Inject
    public VideosRepositoryImpl(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public Observable<List<Video>> getVideos(int movieId) {
        return this.moviesService
                .getVideos(movieId, ApiUtils.TMDB_API_KEY)
                .map(responseMoviePage ->
                        Lists.transform(responseMoviePage.getResponseVideos(), VideosMapper.responseToVideo));
    }
}
