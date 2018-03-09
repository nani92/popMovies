package eu.napcode.popmovies.ui.reviews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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

    //TODO add markdown (or something whatever should be used)
    private List<Review> reviews = new ArrayList<>();
    private Context context;

    public ReviewsAdapter(Context context) {
        this.context = context;
    }

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
        holder.authorTextView.setText(R.string.review_written_by);
        holder.authorTextView.append(getAuthorSpannable(review.getAuthor()));
        holder.reviewTextView.setText(review.getContent());

        holder.reviewCardView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
            this.context.startActivity(intent);
        });
    }

    private SpannableString getAuthorSpannable(String author) {
        SpannableString authorSpannable = new SpannableString(author);
        authorSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, author.length(), 0);
        authorSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.context, R.color.colorPrimary)), 0, author.length(), 0);

        return authorSpannable;
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reviewCardView)
        CardView reviewCardView;

        @BindView(R.id.authorTextView)
        TextView authorTextView;

        @BindView(R.id.reviewTextView)
        TextView reviewTextView;

        @BindView(R.id.readMoreTextView)
        TextView readMoreTextView;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
