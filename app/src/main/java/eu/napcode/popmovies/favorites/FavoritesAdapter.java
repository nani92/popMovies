package eu.napcode.popmovies.favorites;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.utils.ApiUtils;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private OnMovieClickedListener onMovieClickedListener;
    private int displayedPosition = -1;

    public FavoritesAdapter(OnMovieClickedListener onMovieClickedListener) {
        this.onMovieClickedListener = onMovieClickedListener;
    }

    public void setMovies(List<Movie> movies) {

        if (movies.size() < this.movies.size() && this.displayedPosition >= 0) {
            this.movies.remove(this.displayedPosition);
            notifyItemRemoved(this.displayedPosition);
        } else {
            this.movies = movies;
            notifyDataSetChanged();
        }
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_favorite_movie, parent, false);

        return new FavoritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, final int position) {
        Movie movie = this.movies.get(position);

        holder.titleTextView.setText(movie.getTitle());
        holder.releaseTextView.setText(movie.getReleaseDate());
        displayPoster(holder, movie);

        holder.itemView.setOnClickListener(v -> {
                onMovieClickedListener.movieClicked(movies.get(position), v);
                this.displayedPosition = position;
        });
    }

    private void displayPoster(FavoritesViewHolder holder, Movie movie) {

        if (TextUtils.isEmpty(movie.getPosterPath())) {
            holder.posterImageView.setImageBitmap(movie.getPosterBitmap());
        } else {
            Glide.with(holder.itemView)
                    .load(ApiUtils.getPosterUrl(movie.getPosterPath()))
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.popcorn))
                    .into(holder.posterImageView);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTextView)
        TextView titleTextView;

        @BindView(R.id.posterImageView)
        ImageView posterImageView;

        @BindView(R.id.releaseDateTextView)
        TextView releaseTextView;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnMovieClickedListener {
        void movieClicked(Movie movie, View view);
    }
}
