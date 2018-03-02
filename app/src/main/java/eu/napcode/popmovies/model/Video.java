package eu.napcode.popmovies.model;

import eu.napcode.popmovies.R;

public class Video {

    private String id;
    private VideoSite site;
    private String key;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VideoSite getSite() {
        return site;
    }

    public void setSite(VideoSite site) {
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum VideoSite {
        YOUTUBE,
        OTHER;

        public static VideoSite getSiteFromString(String site){

            if (site.equals("YouTube")) {
                return YOUTUBE;
            }

            return OTHER;
        }
    }
}
