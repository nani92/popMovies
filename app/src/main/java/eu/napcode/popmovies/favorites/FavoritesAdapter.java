package eu.napcode.popmovies.favorites;

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
import eu.napcode.popmovies.model.Movie;
import eu.napcode.popmovies.utils.ApiUtils;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private OnMovieClickedListener onMovieClickedListener;

    public FavoritesAdapter(OnMovieClickedListener onMovieClickedListener) {
        this.onMovieClickedListener = onMovieClickedListener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_favorite_movie, parent, false);

        return new FavoritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, final int position) {
        holder.titleTextView.setText(movies.get(position).getTitle());

        Glide.with(holder.itemView)
                .load(ApiUtils.getPosterUrl(movies.get(position).getPosterPath()))
                .apply(new RequestOptions()
                    .placeholder(R.drawable.popcorn))
                .into(holder.posterImageView);

        holder.itemView.setOnClickListener(v ->
                onMovieClickedListener.movieClicked(movies.get(position), v));
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

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnMovieClickedListener {
        void movieClicked(Movie movie, View view);
    }
}
