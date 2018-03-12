package eu.napcode.popmovies.ui.reviews;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.model.Review;
import eu.napcode.popmovies.repository.ReviewsRepository;
import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.utils.archbase.PresenterBundle;
import eu.napcode.popmovies.utils.rx.RxSchedulers;
import io.reactivex.Observable;

public class ReviewsPresenter implements BasePresenter<ReviewsView> {

    private ReviewsRepository reviewsRepository;
    private RxSchedulers rxSchedulers;

    private ReviewsView reviewsView;

    private List<Review> reviews = new ArrayList<>();
    private boolean isDownloading;

    @Inject
    public ReviewsPresenter(ReviewsRepository reviewsRepository, RxSchedulers rxSchedulers) {
        this.reviewsRepository = reviewsRepository;
        this.rxSchedulers = rxSchedulers;
    }

    @Override
    public void attachView(ReviewsView view) {
        this.reviewsView = view;
    }

    @Override
    public void dropView() {
        this.reviewsView = null;
    }

    @Override
    public PresenterBundle saveState() {
        return null;
    }

    @Override
    public void restoreState(PresenterBundle presenterBundle) {

    }

    public void loadReviews(int movieId) {

        if (shouldNotDownload()) {
            return;
        }

        this.reviewsView.displayProgressBar();
        this.isDownloading = true;

        Observable<List<Review>> reviewsObservable;

        if (this.reviews.isEmpty()) {
            reviewsObservable = this.reviewsRepository.getReviews(movieId);
        } else {
            reviewsObservable = this.reviewsRepository.getMoreReviews(movieId);
        }

        reviewsObservable
                .subscribeOn(this.rxSchedulers.io())
                .observeOn(this.rxSchedulers.androidMainThread())
                .subscribe(reviews -> downloadedReviews(reviews),
                        throwable -> displayError());
    }

    private boolean shouldNotDownload() {
        return isDownloading ||
                (!this.reviews.isEmpty() && this.reviewsRepository.hasMoreMoviesToDownload() == false);
    }

    private void downloadedReviews(List<Review> reviews) {
        this.isDownloading = false;

        if (this.reviewsView != null) {
            this.reviewsView.hideProgressBar();
        }

        if (reviews.isEmpty() && this.reviews.isEmpty()) {
            displayNoReviews();

            return;
        }

        this.reviews.addAll(reviews);
        displayReviews(reviews);
    }

    private void displayReviews(List<Review> reviews) {
        this.reviewsView.hideEmptyLayout();
        this.reviewsView.displayReviews(reviews);
    }

    private void displayNoReviews() {
        this.reviewsView.displayEmptyLayout();
    }

    private void displayError() {
        this.isDownloading = false;

        if (this.reviewsView == null) {
            return;
        }

        this.reviewsView.hideProgressBar();

        if (this.reviews.isEmpty()) {
            displayNoReviews();
        }

        //TODO display error
    }
}
