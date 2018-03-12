package eu.napcode.popmovies.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewLoadDataUtils {

    public static RecyclerView.OnScrollListener getOnScrollListener(LoadDataRecyclerViewListener loadDataRecyclerViewListener) {
        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = getFirstVisibleItemPosition(layoutManager);

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    loadDataRecyclerViewListener.shouldLoadNewData();
                }
            }
        };
    }

    private static int getFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {

        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }

        return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
    }

    public interface LoadDataRecyclerViewListener {
        void shouldLoadNewData();
    }
}


