package eu.napcode.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.site == null ? -1 : this.site.ordinal());
        dest.writeString(this.key);
        dest.writeString(this.name);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.id = in.readString();
        int tmpSite = in.readInt();
        this.site = tmpSite == -1 ? null : VideoSite.values()[tmpSite];
        this.key = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
