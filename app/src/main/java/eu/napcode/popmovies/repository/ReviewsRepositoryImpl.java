package eu.napcode.popmovies.repository;

import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import eu.napcode.popmovies.api.MoviesService;
import eu.napcode.popmovies.api.responsemodel.reviews.ResponseReviewsPage;

import eu.napcode.popmovies.model.Review;
import eu.napcode.popmovies.model.ReviewMapper;
import eu.napcode.popmovies.utils.ApiUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

@Singleton
public class ReviewsRepositoryImpl implements ReviewsRepository {

    private MoviesService moviesService;
    private int downloadedReviewsPage = 0;
    private boolean hasNextPageToDownload = true;

    @Inject
    public ReviewsRepositoryImpl(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @Override
    public Observable<List<Review>> getReviews(int movieId) {
        return this.moviesService
                .getReviews(movieId, ApiUtils.TMDB_API_KEY)
                .map(mapFunction);
    }

    private Function<ResponseReviewsPage, List<Review>> mapFunction = responseReviewsPage -> {
        this.downloadedReviewsPage = responseReviewsPage.getPage();
        this.hasNextPageToDownload = this.downloadedReviewsPage < responseReviewsPage.getTotalPages();

        return Lists.transform(responseReviewsPage.getReviews(), ReviewMapper.responseToReview);
    };

    @Override
    public Observable<List<Review>> getMoreReviews(int movieId) {
        return this.moviesService
                .getNextReviews(movieId, downloadedReviewsPage + 1, ApiUtils.TMDB_API_KEY)
                .map(mapFunction);
    }

    @Override
    public boolean hasMoreMoviesToDownload() {
        return this.hasNextPageToDownload;
    }
}
