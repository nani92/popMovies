package eu.napcode.popmovies.utils.rx;

import io.reactivex.Scheduler;

public interface RxSchedulers {

    Scheduler io();

    Scheduler androidMainThread();
}
