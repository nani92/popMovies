package eu.napcode.popmovies.utils;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;

import eu.napcode.popmovies.R;

public class ConstraintSetChangeUtils {

    public static void detailsNoBackdropChange(ConstraintLayout constraintLayout) {

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.clear(R.id.posterImageView, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.posterImageView, ConstraintSet.TOP, R.id.topGuideline, ConstraintSet.BOTTOM);

        constraintSet.clear(R.id.favoriteFab, ConstraintSet.TOP);
        constraintSet.connect(R.id.favoriteFab, ConstraintSet.BOTTOM, R.id.detailsTopDividerView, ConstraintSet.TOP);

        constraintSet.applyTo(constraintLayout);
    }
}
