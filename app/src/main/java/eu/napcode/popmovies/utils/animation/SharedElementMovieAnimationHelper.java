package eu.napcode.popmovies.utils.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import eu.napcode.popmovies.R;

public class SharedElementMovieAnimationHelper {

    public static Bundle getBundle(Activity activity, View view) {
        //TODO animate title
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair<View, String>(view.findViewById(R.id.posterImageView), activity.getString(R.string.poster_transition)),
                new Pair<View, String>(view.findViewById(R.id.titleTextView), activity.getString(R.string.title_transition)))
                .toBundle();

        return bundle;
    }

    public static Bundle getBundle(Activity activity, View view, int id) {
        //TODO animate title
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair<View, String>(view.findViewById(R.id.posterImageView), getTransitionName(
                        activity.getString(R.string.poster_transition),
                        id)),
                new Pair<View, String>(view.findViewById(R.id.titleTextView), activity.getString(R.string.title_transition)))
                .toBundle();

        return bundle;
    }

    public static String getTransitionName(String name, int id) {
        return String.format("%s_%d", name, id);
    }
}
