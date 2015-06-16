package me.weppler.android.spotifystreamer.app.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.weppler.android.spotifystreamer.app.MediaPlayerActivity;
import me.weppler.android.spotifystreamer.app.R;
import me.weppler.android.spotifystreamer.model.Track;

/**
 * Created by mattweppler on 6/16/15.
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private final String TAG = TrackAdapter.class.getSimpleName();
    private final ArrayList<Track> mTracks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mAlbumImageUrl;
        public TextView mAlbumName;
        public TextView mTrackName;
        public ViewHolder(View itemView) {
            super(itemView);
            mAlbumImageUrl = (ImageView)itemView.findViewById(R.id.track_thumb_image_view);
            mAlbumName = (TextView)itemView.findViewById(R.id.track_album_text_view);
            mTrackName = (TextView)itemView.findViewById(R.id.track_name_text_view);
        }
    }

    public TrackAdapter(ArrayList<Track> tracks) {
        mTracks = tracks;
    }

    @Override
    public TrackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mAlbumName.setText(mTracks.get(position).getAlbumName());
        holder.mTrackName.setText(mTracks.get(position).getName());
        if (!mTracks.get(position).getAlbumImageUrl().equals("")) {
            Picasso.with(holder.mAlbumImageUrl.getContext())
                    .load(mTracks.get(position).getAlbumImageUrl())
                    .resize(192, 192)
                    .centerCrop()
                    .into(holder.mAlbumImageUrl);
        } else {
            Picasso.with(holder.mAlbumImageUrl.getContext())
                    .load(R.drawable.placeholder)
                    .resize(192, 192)
                    .centerCrop()
                    .into(holder.mAlbumImageUrl);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String track = mTracks.get(holder.getAdapterPosition()).getName();
                //Toast.makeText(v.getContext(), track, Toast.LENGTH_SHORT).show();
                Intent mediaPlayerIntent = new Intent(v.getContext(), MediaPlayerActivity.class);
                mediaPlayerIntent.putExtra(Intent.EXTRA_TEXT, track);
                holder.itemView.getContext().startActivity(mediaPlayerIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }
}
