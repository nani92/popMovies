package eu.napcode.popmovies.utils.animation;

import android.support.design.widget.FloatingActionButton;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.view.Window;

import eu.napcode.popmovies.R;

public class TransitionAnimations {

    public static void setDetailsTransitionAnimations(Window window, int duration) {
        setEnterTransition(window, duration);
        setReturnTransition(window, duration);
    }

    private static void setEnterTransition(Window window, int duration) {
        Explode explode = new Explode();
        explode.setDuration(duration);

        window.setEnterTransition(explode);
        window.getEnterTransition().addListener(getDetailsEnterTransitionListener(window));
    }

    private static TransitionListener getDetailsEnterTransitionListener(Window window) {
        return new TransitionListener() {

            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                ((FloatingActionButton) window.findViewById(R.id.favoriteFab)).show();
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        };
    }

    private static void setReturnTransition(Window window, int duration) {
        Fade fade = new Fade();
        fade.setDuration(duration);

        window.setReturnTransition(fade);
        window.getReturnTransition().addListener(getDetailsReturnTransitionListener(window));
    }

    private static TransitionListener getDetailsReturnTransitionListener(Window window) {
        return new TransitionListener() {

            @Override
            public void onTransitionStart(Transition transition) {
                ((FloatingActionButton) window.findViewById(R.id.favoriteFab)).hide();
            }

            @Override
            public void onTransitionEnd(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        };
    }
}
