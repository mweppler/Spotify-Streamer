package me.weppler.android.spotifystreamer.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.weppler.android.spotifystreamer.app.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MediaPlayerActivityFragment extends Fragment {

    public MediaPlayerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_media_player, container, false);
        return rootView;
    }
}
