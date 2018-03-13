package eu.napcode.popmovies.repository;

import java.util.List;

import eu.napcode.popmovies.model.Review;
import io.reactivex.Observable;

public interface ReviewsRepository {

    Observable<List<Review>> getReviews(int movieId);

    Observable<List<Review>> getMoreReviews(int movieId);

    boolean hasMoreReviewsToDownload();
}
