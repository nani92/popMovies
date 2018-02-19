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
import eu.napcode.popmovies.model.Movie;

import static eu.napcode.popmovies.api.ApiConstants.POSTER_BASE_URL;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    List<Movie> movies = new ArrayList<>();

    public MoviesAdapter() {}

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);

        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.titleTextView.setText(movies.get(position).getTitle());
        Glide.with(holder.itemView)
                .load(POSTER_BASE_URL + movies.get(position).getPosterPath())
                .into(holder.posterImageView);
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
}
