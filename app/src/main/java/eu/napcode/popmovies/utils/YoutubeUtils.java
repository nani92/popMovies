package eu.napcode.popmovies.utils;

import android.content.Intent;
import android.net.Uri;

public class YoutubeUtils {

    public static Intent getYoutubeAppIntent(String key) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
    }

    public static Intent getYoutubeWebIntent(String key) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key));
    }
}
