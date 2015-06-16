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

import me.weppler.android.spotifystreamer.app.R;
import me.weppler.android.spotifystreamer.app.TopTracksActivity;
import me.weppler.android.spotifystreamer.model.Artist;

/**
 * Created by mattweppler on 6/15/15.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private final String TAG = ArtistAdapter.class.getSimpleName();
    private final ArrayList<Artist> mArtists;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mArtistName;
        public ImageView mImageUrl;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageUrl = (ImageView)itemView.findViewById(R.id.artist_thumb_image_view);
            mArtistName = (TextView)itemView.findViewById(R.id.artist_name_text_view);
        }
    }

    public ArtistAdapter(ArrayList<Artist> artists) {
        mArtists = artists;
    }

    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mArtistName.setText(mArtists.get(position).getName());
        if (!mArtists.get(position).getImageUrl().equals("")) {
            Picasso.with(holder.mImageUrl.getContext())
                    .load(mArtists.get(position).getImageUrl())
                    .resize(192, 192)
                    .centerCrop()
                    .into(holder.mImageUrl);
        } else {
            Picasso.with(holder.mImageUrl.getContext())
                    .load(R.drawable.placeholder)
                    .resize(192, 192)
                    .centerCrop()
                    .into(holder.mImageUrl);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spotifyId = mArtists.get(holder.getAdapterPosition()).getSpotifyId();
                //Toast.makeText(v.getContext(), artist, Toast.LENGTH_SHORT).show();
                Intent artistTracksIntent = new Intent(v.getContext(), TopTracksActivity.class);
                artistTracksIntent.putExtra(Intent.EXTRA_TEXT, spotifyId);
                holder.itemView.getContext().startActivity(artistTracksIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }
}
