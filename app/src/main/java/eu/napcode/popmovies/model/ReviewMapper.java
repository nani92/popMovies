package eu.napcode.popmovies.model;

import com.google.common.base.Function;
import eu.napcode.popmovies.api.responsemodel.reviews.ResponseReview;

public class ReviewMapper {

    public static Function<ResponseReview, Review> responseToReview =
            responseReview -> {
                Review review = new Review();
                review.setAuthor(responseReview.getAuthor());
                review.setContent(responseReview.getContent());
                review.setUrl(responseReview.getUrl());

                return review;
            };
}

