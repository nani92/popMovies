package eu.napcode.popmovies.moviedetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.napcode.popmovies.R;
import eu.napcode.popmovies.model.Video;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {

    private List<Video> videos;
    private OnVideoClickedListener onVideoClickedListener;

    public VideosAdapter(List<Video> videos, OnVideoClickedListener videoClickedListener) {
        this.videos = videos;
        this.onVideoClickedListener = videoClickedListener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_video, parent, false);

        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, final int position) {
        holder.nameTextView.setText(videos.get(position).getName());

        if (videos.get(position).getSite() == Video.VideoSite.YOUTUBE){
            Glide.with(holder.itemView)
                    .load(R.drawable.youtube)
                    .into(holder.videoImageView);
        }

        if (videos.get(position).getSite() == Video.VideoSite.OTHER){
            Glide.with(holder.itemView)
                    .load(R.drawable.video_other)
                    .into(holder.videoImageView);
        }

        holder.itemView.setOnClickListener(v ->
                onVideoClickedListener.videoClicked(videos.get(position).getKey()));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameTextView)
        TextView nameTextView;

        @BindView(R.id.videoImageView)
        ImageView videoImageView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnVideoClickedListener {
        void videoClicked(String videoKey);
    }
}
