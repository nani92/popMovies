package testhelpers;

import java.util.ArrayList;

import eu.napcode.popmovies.model.Movie;

public class MockMoviesHelper {

    public static ArrayList<Movie> getPopularMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(createMovieWithTitle("Popular 1"));
        movies.add(createMovieWithTitle("Popular 2"));

        return movies;
    }

    public static Movie createMovieWithTitle(String title) {
        Movie movie = new Movie();
        movie.setTitle(title);

        return movie;
    }

    public static ArrayList<Movie> getTopMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(createMovieWithTitle("Top 1"));
        movies.add(createMovieWithTitle("Top 2"));
        movies.add(createMovieWithTitle("Top 3"));
        movies.add(createMovieWithTitle("Top 4"));

        return movies;
    }

    public static Movie getFullMovie() {
        Movie movie = new Movie();
        movie.setId(0);
        movie.setTitle("Title");
        movie.setPlot("Plot");
        movie.setReleaseDate("20-02-2019");
        movie.setOriginalTitle("Original title");
        movie.setPosterPath("path");
        movie.setBackdropPath("path");

        return movie;
    }
}
