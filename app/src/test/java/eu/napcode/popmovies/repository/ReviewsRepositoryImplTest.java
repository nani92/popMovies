package eu.napcode.popmovies.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.utils.ApiUtils;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static testhelpers.MockReviewsResponseHelper.getResponseReviewsFirstOfTwoPages;
import static testhelpers.MockReviewsResponseHelper.getResponseReviewsSecondOfTwoPages;

@RunWith(MockitoJUnitRunner.class)
public class ReviewsRepositoryImplTest {

    @Mock
    MoviesService moviesService;

    private ReviewsRepository reviewsRepository;

    @Before
    public void initial() {
        reviewsRepository = new ReviewsRepositoryImpl(moviesService);

        Mockito.when(moviesService.getReviews(anyInt(), anyString()))
                .thenReturn(Observable.just(getResponseReviewsFirstOfTwoPages()));
        Mockito.when(moviesService.getNextReviews(anyInt(), anyInt(), anyString()))
                .thenReturn(Observable.just(getResponseReviewsSecondOfTwoPages()));
    }

    @Test
    public void testGetReviews() {
        reviewsRepository.getReviews(0);

        Mockito.verify(moviesService).getReviews(0, ApiUtils.TMDB_API_KEY);
    }

    @Test
    public void testGetMoreReviews() {
        reviewsRepository.getReviews(0)
                .test()
                .awaitTerminalEvent();
        reviewsRepository.getMoreReviews(0)
                .test()
                .awaitTerminalEvent();

        Mockito.verify(moviesService).getReviews(0, ApiUtils.TMDB_API_KEY);
        Mockito.verify(moviesService).getNextReviews(0, 2, ApiUtils.TMDB_API_KEY);
    }

    @Test
    public void testShouldDownloadMoreReviews() {
        reviewsRepository.getReviews(0)
                .test()
                .awaitTerminalEvent();

        Mockito.verify(moviesService).getReviews(0, ApiUtils.TMDB_API_KEY);
        Assert.assertEquals(true, reviewsRepository.hasMoreReviewsToDownload());
    }

    @Test
    public void testShouldNotDownloadMoreReviews() {
        reviewsRepository.getReviews(0)
                .test()
                .awaitTerminalEvent();
        reviewsRepository.getMoreReviews(0)
                .test()
                .awaitTerminalEvent();

        Mockito.verify(moviesService).getReviews(0, ApiUtils.TMDB_API_KEY);
        Mockito.verify(moviesService).getNextReviews(0, 2, ApiUtils.TMDB_API_KEY);
        Assert.assertEquals(false, reviewsRepository.hasMoreReviewsToDownload());
    }
}