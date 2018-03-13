package eu.napcode.popmovies.ui.reviews;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import eu.napcode.popmovies.model.Review;
import eu.napcode.popmovies.repository.ReviewsRepository;
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class ReviewsPresenterTest {

    @Mock
    ReviewsRepository reviewsRepository;

    @Mock
    ReviewsView reviewsView;

    private ReviewsPresenter reviewsPresenter;
    private int movieId;
    private List<Review> reviews = getReviews();
    private List<Review> moreReviews = getMoreReviews();

    @Before
    public void initial() {
        reviewsPresenter = new ReviewsPresenter(reviewsRepository, new MockRxSchedulers());
        reviewsPresenter.attachView(reviewsView);

        Mockito.when(reviewsRepository.getReviews(movieId))
                .thenReturn(Observable.fromArray(reviews));
        Mockito.when(reviewsRepository.getMoreReviews(movieId))
                .thenReturn(Observable.fromArray(moreReviews));
        Mockito.when(reviewsRepository.hasMoreMoviesToDownload())
                .thenReturn(true);
    }

    private static ArrayList<Review> getReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(getReview("1"));
        reviews.add(getReview("2"));

        return reviews;
    }

    private static Review getReview(String reviewId) {
        Review review = new Review();
        review.setAuthor("a" + reviewId);
        review.setContent("c" + reviewId);
        review.setUrl("u" + reviewId);

        return review;
    }

    private static ArrayList<Review> getMoreReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(getReview("3"));
        reviews.add(getReview("4"));

        return reviews;
    }

    @Test
    public void testDisplayReviewsList() {
        reviewsPresenter.loadReviews(movieId);

        Mockito.verify(reviewsView).displayReviews(reviews);
    }

    @Test
    public void testProgressBarBehaviourForList() {
        reviewsPresenter.loadReviews(movieId);

        Mockito.verify(reviewsView).displayProgressBar();
        Mockito.verify(reviewsView).hideProgressBar();
    }

    @Test
    public void testDisplayEmpty() {
        Mockito.when(reviewsRepository.getReviews(movieId))
                .thenReturn(Observable.fromArray(new ArrayList<>()));

        reviewsPresenter.loadReviews(movieId);

        Mockito.verify(reviewsView, Mockito.times(2)).displayEmptyLayout();
        Mockito.verify(reviewsView, Mockito.times(0)).displayReviews(null);
    }

    @Test
    public void testProgressBarBehaviourForEmpty() {
        Mockito.when(reviewsRepository.getReviews(movieId))
                .thenReturn(Observable.fromArray(new ArrayList<>()));

        reviewsPresenter.loadReviews(movieId);

        Mockito.verify(reviewsView).displayProgressBar();
        Mockito.verify(reviewsView).hideProgressBar();
    }

    @Test
    public void testLoadMoreReviews() {
        reviewsPresenter.loadReviews(movieId);
        reviewsPresenter.loadReviews(movieId);

        Mockito.verify(reviewsView).displayReviews(reviews);
        Mockito.verify(reviewsView).displayReviews(moreReviews);
        Mockito.verify(reviewsView, Mockito.times(2)).displayProgressBar();
        Mockito.verify(reviewsView, Mockito.times(2)).hideProgressBar();
        Mockito.verify(reviewsView).displayEmptyLayout();
    }

    @Test
    public void testNoMoreReviews() {
        Mockito.when(reviewsRepository.hasMoreMoviesToDownload())
                .thenReturn(false);

        reviewsPresenter.loadReviews(movieId);
        reviewsPresenter.loadReviews(movieId);

        Mockito.verify(reviewsView).displayReviews(reviews);
        Mockito.verify(reviewsView).displayProgressBar();
        Mockito.verify(reviewsView).hideProgressBar();
        Mockito.verify(reviewsView).displayEmptyLayout();
    }

    public static class MockRxSchedulers implements RxSchedulers {

        @Override
        public Scheduler io() {
            return Schedulers.trampoline();
        }

        @Override
        public Scheduler androidMainThread() {
            return Schedulers.trampoline();
        }
    }
}