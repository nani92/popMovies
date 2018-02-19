package eu.napcode.popmovies.archbase;

public interface BasePresenter<T> {

    void attachView(T view);

    void dropView();

}
