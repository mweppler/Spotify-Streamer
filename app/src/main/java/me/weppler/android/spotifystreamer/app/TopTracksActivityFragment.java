package me.weppler.android.spotifystreamer.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import me.weppler.android.spotifystreamer.app.adapter.TrackAdapter;
import me.weppler.android.spotifystreamer.model.Track;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTracksActivityFragment extends Fragment {
    private final String TAG = TopTracksActivityFragment.class.getSimpleName();

    private String mSpotifyArtistId;
    private ArrayList<Track> mTracks;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mTrackAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public TopTracksActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);
        Intent incomingIntent = getActivity().getIntent();
        if (incomingIntent != null && incomingIntent.hasExtra(Intent.EXTRA_TEXT)) {
            mSpotifyArtistId = incomingIntent.getStringExtra(Intent.EXTRA_TEXT);
        }
        fetchTopTracksForArtist(mSpotifyArtistId);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.top_tracks_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if ((savedInstanceState != null)
                && (savedInstanceState.getSerializable("TRACK_RESULTS") != null)) {
            mTracks = savedInstanceState.getParcelableArrayList("TRACK_RESULTS");
        } else {
            mTracks = new ArrayList<>();
        }
        mTrackAdapter = new TrackAdapter(mTracks);
        mRecyclerView.setAdapter(mTrackAdapter);
        return rootView;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("TRACK_RESULTS", mTracks);
    }

    private void fetchTopTracksForArtist(String query) {
        TopTrackDataTask trackTask = new TopTrackDataTask();
        trackTask.execute(query);
    }

    public class TopTrackDataTask extends AsyncTask<String, Void, Void> {
        private final String TAG = TopTrackDataTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            HashMap<String, Object> queryString = new HashMap<String, Object>();
            queryString.put(SpotifyService.COUNTRY, Locale.getDefault().getCountry());
            Tracks results = spotify.getArtistTopTrack(params[0], queryString);
            if (results.tracks == null) {
                return null;
            } else {
                Log.d(TAG, "results: " + results);
                updateTracksFromResults(results.tracks);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if (mTracks.size() == 0) {
                String msg = "Couldn't find any tracks for the artist.";
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                return;
            }
            mTrackAdapter.notifyDataSetChanged();
        }

        private Void updateTracksFromResults(
                List<kaaes.spotify.webapi.android.models.Track> trackData) {
            mTracks.clear();
            for (kaaes.spotify.webapi.android.models.Track track : trackData) {
                String albumImageUrl;
                String albumName = track.album.name;
                String artistName = track.artists.get(0).name;
                String trackName = track.name;
                String playbackUrl = track.preview_url;
                String spotifyId = track.id;
                if (track.album.images.size() > 0) {
                    albumImageUrl = track.album.images.get(0).url;
                } else {
                    albumImageUrl = "";
                }
                mTracks.add(new Track(artistName, albumName, trackName, albumImageUrl,
                        playbackUrl, spotifyId));
            }
            return null;
        }
    }
}
