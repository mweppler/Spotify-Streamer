package me.weppler.android.spotifystreamer.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.weppler.android.spotifystreamer.app.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistSearchActivityFragment extends Fragment {
    private final String TAG = ArtistSearchActivityFragment.class.getSimpleName();
    private ArrayAdapter<String> mArtistAdapter;

    public ArtistSearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_search, container, false);
        String[] artistArray = {
                "Coldplay",
                "The Doors",
                "In Truth",
                "Geto Boys",
                "504 Boyz",
                "Porcelain Raft"
        };
        List<String> artistArrayList = new ArrayList<String>(Arrays.asList(artistArray));
        mArtistAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.artist_list_item,
                R.id.artist_name_text_view,
                artistArrayList
//                new ArrayList<String>()
        );
        final ListView listView = (ListView) rootView.findViewById(R.id.artist_list_view);
        listView.setAdapter(mArtistAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String artist = mArtistAdapter.getItem(position);
                //Toast.makeText(getActivity(), artist, Toast.LENGTH_SHORT).show();
                Intent artistTracksIntent = new Intent(getActivity(), TopTracksActivity.class);
                artistTracksIntent.putExtra(Intent.EXTRA_TEXT, artist);
                startActivity(artistTracksIntent);
            }
        });
        return rootView;
    }
}
