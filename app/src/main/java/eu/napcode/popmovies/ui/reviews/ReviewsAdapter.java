package eu.napcode.popmovies.ui.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.model.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<Review> reviews = new ArrayList<>();

    public void addReviews(List<Review> reviews) {
        this.reviews.addAll(reviews);
        notifyItemRangeInserted(this.reviews.size() - reviews.size(), reviews.size());
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_review, parent, false);

        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, final int position) {
        Review review = this.reviews.get(position);
        holder.authorTextView.setText(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.authorTextView)
        TextView authorTextView;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
