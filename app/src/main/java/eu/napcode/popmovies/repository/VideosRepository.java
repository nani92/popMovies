package eu.napcode.popmovies.repository;

import java.util.List;

import eu.napcode.popmovies.model.Video;
import io.reactivex.Observable;

public interface VideosRepository {

    Observable<List<Video>>  getVideos(int movieId);
}
