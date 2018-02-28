package eu.napcode.popmovies.utils.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import eu.napcode.popmovies.R;

public class SharedElementMovieAnimationHelper {

    public static Bundle getBundle(Activity activity, View view) {
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair<View, String>(view.findViewById(R.id.posterImageView), activity.getString(R.string.poster_transition)))
                .toBundle();

        return bundle;
    }
}
