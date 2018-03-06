package eu.napcode.popmovies.utils.archbase;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import eu.napcode.popmovies.model.Movie;

public class PresenterBundle {

    private Bundle bundle;

    public PresenterBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public PresenterBundle() {
        this.bundle = new Bundle();
    }

    public void putParcelableArrayList(String key, ArrayList<Parcelable> value) {
        bundle.putParcelableArrayList(key, value);
    }

    public List<Movie> getParcelableArrayList(String key) {
        return bundle.getParcelableArrayList(key);
    }

    public Bundle getBundle() {
        return bundle;
    }
}
