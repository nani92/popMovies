package testhelpers;

import java.util.ArrayList;

import eu.napcode.popmovies.model.Review;

public class MockReviewHelper {

    public static ArrayList<Review> getReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(getReview("1"));
        reviews.add(getReview("2"));

        return reviews;
    }

    public static Review getReview(String reviewId) {
        Review review = new Review();
        review.setAuthor("a" + reviewId);
        review.setContent("c" + reviewId);
        review.setUrl("u" + reviewId);

        return review;
    }

    public static ArrayList<Review> getMoreReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(getReview("3"));
        reviews.add(getReview("4"));

        return reviews;
    }
}
