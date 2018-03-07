package eu.napcode.popmovies.ui.reviews;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.napcode.popmovies.model.Review;
import eu.napcode.popmovies.repository.ReviewsRepository;
import eu.napcode.popmovies.utils.archbase.BasePresenter;
import eu.napcode.popmovies.utils.archbase.PresenterBundle;
import eu.napcode.popmovies.utils.rx.RxSchedulers;

public class ReviewsPresenter implements BasePresenter<ReviewsView> {

    private ReviewsRepository reviewsRepository;
    private RxSchedulers rxSchedulers;

    private ReviewsView reviewsView;

    private List<Review> reviews = new ArrayList<>();

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
        this.reviewsRepository.getReviews(movieId)
                .subscribeOn(this.rxSchedulers.io())
                .observeOn(this.rxSchedulers.androidMainThread())
                .subscribe(reviews -> displayReviews(reviews),
                        throwable -> displayError());
    }

    private void displayReviews(List<Review> reviews) {

        if (reviews == null || reviews.isEmpty()) {
            displayNoReviews();

            return;
        }

        this.reviewsView.displayReviews(reviews);
    }

    private void displayNoReviews() {

    }

    private void displayError() {
        displayNoReviews();
        //toDO display
    }
}
