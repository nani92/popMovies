package eu.napcode.popmovies.model;

import com.google.common.base.Function;

import eu.napcode.popmovies.api.responsemodel.videos.ResponseVideo;

public class VideosMapper {

    public static Function<ResponseVideo, Video> responseToVideo =
            responseMovie -> {
                Video video = new Video();
                video.setId(responseMovie.getId());
                video.setKey(responseMovie.getKey());
                video.setSite(Video.VideoSite.getSiteFromString(responseMovie.getSite()));
                video.setName(responseMovie.getName());

                return video;
            };
}

