package eu.napcode.popmovies.movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.utils.ApiUtils;
import eu.napcode.popmovies.model.Movie;

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
                .inflate(R.layout.list_item_movie, parent, false);

        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, final int position) {
        holder.titleTextView.setText(movies.get(position).getTitle());

        Glide.with(holder.itemView)
                .load(ApiUtils.getPosterUrl(movies.get(position).getPosterPath()))
                .into(holder.posterImageView);

        holder.itemView.setOnClickListener(v ->
                onMovieClickedListener.movieClicked(movies.get(position), v));
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
