package eu.napcode.popmovies.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.api.responsemodel.videos.ResponseVideos;
import eu.napcode.popmovies.utils.ApiUtils;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class VideosRepositoryImplTest {

    @Mock
    MoviesService moviesService;

    private VideosRepository videosRepository;

    @Before
    public void initial() {
        videosRepository = new VideosRepositoryImpl(moviesService);
    }

    @Test
    public void testDownloadVideos() {
        Mockito.when(moviesService.getVideos(anyInt(), anyString()))
                .thenReturn(Observable.just(new ResponseVideos()));

        videosRepository.getVideos(0);

        Mockito.verify(moviesService).getVideos(0, ApiUtils.TMDB_API_KEY);
    }
}