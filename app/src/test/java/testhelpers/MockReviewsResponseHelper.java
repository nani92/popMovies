package testhelpers;

import java.util.ArrayList;
import java.util.List;

import eu.napcode.popmovies.api.responsemodel.reviews.ResponseReview;
import eu.napcode.popmovies.api.responsemodel.reviews.ResponseReviewsPage;

public class MockReviewsResponseHelper {

    public static ResponseReviewsPage getResponseReviewsFirstOfTwoPages() {
        ResponseReviewsPage responseReviewsPage = new ResponseReviewsPage();
        responseReviewsPage.setTotalPages(2);
        responseReviewsPage.setPage(1);
        responseReviewsPage.setReviews(getResponseReviewsList());

        return responseReviewsPage;
    }

    public static ResponseReviewsPage getResponseReviewsSecondOfTwoPages() {
        ResponseReviewsPage responseReviewsPage = new ResponseReviewsPage();
        responseReviewsPage.setTotalPages(2);
        responseReviewsPage.setPage(2);
        responseReviewsPage.setReviews(getResponseReviewsList());

        return responseReviewsPage;
    }

    public static List<ResponseReview> getResponseReviewsList() {
        List<ResponseReview> responseReviews = new ArrayList<>();
        responseReviews.add(getResponseReview());
        responseReviews.add(getResponseReview());

        return responseReviews;
    }

    public static ResponseReview getResponseReview() {
        ResponseReview responseReview = new ResponseReview();

        return responseReview;
    }

}
