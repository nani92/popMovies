package eu.napcode.popmovies.repository;


import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.model.Review;
import eu.napcode.popmovies.model.ReviewMapper;
import eu.napcode.popmovies.utils.ApiUtils;
import io.reactivex.Observable;

public class ReviewsRepositoryImpl implements ReviewsRepository {

    private MoviesService moviesService;

    @Inject
    public ReviewsRepositoryImpl(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public Observable<List<Review>> getReviews(int movieId) {
        return this.moviesService
                .getReviews(movieId, ApiUtils.TMDB_API_KEY)
                .map(responseReviewsPage ->
                        Lists.transform(responseReviewsPage.getReviews(), ReviewMapper.responseToReview));
    }
}
