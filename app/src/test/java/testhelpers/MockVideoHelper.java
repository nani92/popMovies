package testhelpers;

import java.util.ArrayList;

import eu.napcode.popmovies.model.Video;

public class MockVideoHelper {

    public static ArrayList<Video> getVideos() {
        ArrayList<Video> videos = new ArrayList<>();
        videos.add(new Video());

        return videos;
    }
}
