package eu.napcode.popmovies.utils.archbase;

public interface BasePresenter<T> {

    void attachView(T view);

    void dropView();

}
