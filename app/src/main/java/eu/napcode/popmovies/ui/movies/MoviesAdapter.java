package eu.napcode.popmovies.ui.movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.utils.ApiUtils;
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.utils.animation.SharedElementMovieAnimationHelper;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private OnMovieClickedListener onMovieClickedListener;

    public MoviesAdapter(OnMovieClickedListener onMovieClickedListener) {
        this.onMovieClickedListener = onMovieClickedListener;
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyItemRangeInserted(this.movies.size() - movies.size(), movies.size());
    }

    public void clearMovies() {
        int oldCount = this.movies.size();
        this.movies.clear();
        notifyItemRangeRemoved(0, oldCount);
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_movie, parent, false);

        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, final int position) {
        Movie movie = this.movies.get(position);
        holder.titleTextView.setText(movie.getTitle());

        Glide.with(holder.itemView)
                .load(ApiUtils.getPosterUrl(movie.getPosterPath()))
                .apply(new RequestOptions().placeholder(R.drawable.popcorn))
                .into(holder.posterImageView);

        holder.posterImageView.setTransitionName(
                SharedElementMovieAnimationHelper.getTransitionName(
                        holder.posterImageView.getTransitionName(),
                        movie.getId()
                ));

        holder.itemView.setOnClickListener(v ->
                onMovieClickedListener.movieClicked(movie, v));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTextView)
        TextView titleTextView;

        @BindView(R.id.posterImageView)
        ImageView posterImageView;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnMovieClickedListener {
        void movieClicked(Movie movie, View view);
    }
}
