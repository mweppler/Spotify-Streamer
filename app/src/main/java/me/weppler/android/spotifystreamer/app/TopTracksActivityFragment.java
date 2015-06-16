package me.weppler.android.spotifystreamer.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import me.weppler.android.spotifystreamer.model.Track;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTracksActivityFragment extends Fragment {
    private final String TAG = TopTracksActivityFragment.class.getSimpleName();
    private String mArtistName;
    private SimpleAdapter mTrackAdapter;
    private ArrayList<HashMap<String, String>> mTrackListing;

    public TopTracksActivityFragment() {
        //getActivity().setTitle(getString(R.string.top_tracks_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);
        Intent incomingIntent = getActivity().getIntent();
        if (incomingIntent != null && incomingIntent.hasExtra(Intent.EXTRA_TEXT)) {
            mArtistName = incomingIntent.getStringExtra(Intent.EXTRA_TEXT);
        }
        mTrackListing = new ArrayList<HashMap<String, String>>();
        Track[] tracks = {
                new Track("Light My Fire", "The Doors"),
                new Track("Riders on the Storm", "L.A. Woman"),
                new Track("Break On Through (to the Other Side)", "The Doors"),
                new Track("Hello, I Love You", "Waiting for the Sun"),
                new Track("Love Me Two Times", "Strange Days"),
                new Track("Touch Me", "The Soft Parade"),
                new Track("L.A. Woman", "L.A. Woman"),
                new Track("Crystal Ship", "The Doors"),
                new Track("Roadhouse Blues", "Morrison Hotel"),
                new Track("Love Her Madly", "L.A. Woman")
        };
        int listLength = tracks.length;
        for (int i = 0; i < listLength; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(getString(R.string.track_name_hash_key), tracks[i].getName());
            map.put(getString(R.string.track_album_hash_key), tracks[i].getAlbum());
            mTrackListing.add(map);
        }
        mTrackAdapter = new SimpleAdapter(
                getActivity(),
                mTrackListing,
                R.layout.track_list_item,
                new String[] {
                        getString(R.string.track_name_hash_key),
                        getString(R.string.track_album_hash_key)
                },
                new int[] {
                        R.id.track_name_text_view,
                        R.id.track_album_text_view
                }
        );
        final ListView listView = (ListView) rootView.findViewById(R.id.track_list_view);
        listView.setAdapter(mTrackAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = mTrackListing.get(position);
                String trackName = map.get(getString(R.string.track_name_hash_key));
                String trackAlbum = map.get(getString(R.string.track_album_hash_key));
                String trackInfo = trackName + " - " + trackAlbum;
                Toast.makeText(getActivity(), trackInfo, Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
