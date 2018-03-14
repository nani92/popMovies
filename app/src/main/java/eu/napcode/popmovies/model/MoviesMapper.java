package eu.napcode.popmovies.model;

import com.google.common.base.Function;

import eu.napcode.popmovies.api.responsemodel.ResponseReview;

public class MoviesMapper {

    public static Function<ResponseReview, Movie> responseToMovie =
            new Function<ResponseReview, Movie>() {

                @Override
                public Movie apply(ResponseReview responseMovie) {
                    Movie movie = new Movie();
                    movie.setTitle(responseMovie.getTitle());
                    movie.setId(responseMovie.getId());
                    movie.setPosterPath(responseMovie.getPosterPath());
                    movie.setBackdropPath(responseMovie.getBackdropPath());
                    movie.setOriginalTitle(responseMovie.getOriginalTitle());
                    movie.setReleaseDate(responseMovie.getReleaseDate());
                    movie.setVoteAverage(String.valueOf(responseMovie.getVoteAverage()));
                    movie.setPlot(responseMovie.getOverview());

                    return movie;
                }
            };
}

