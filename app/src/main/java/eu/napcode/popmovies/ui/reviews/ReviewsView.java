package eu.napcode.popmovies.ui.reviews;

import java.util.List;

import eu.napcode.popmovies.model.Review;

public interface ReviewsView {
    void displayReviews(List<Review> reviews);

    void displayEmptyLayout();

    void hideEmptyLayout();

    void displayProgressBar();

    void hideProgressBar();
}
