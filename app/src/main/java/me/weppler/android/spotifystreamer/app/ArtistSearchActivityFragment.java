package me.weppler.android.spotifystreamer.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import me.weppler.android.spotifystreamer.app.adapter.ArtistAdapter;
import me.weppler.android.spotifystreamer.model.Artist;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistSearchActivityFragment extends Fragment {
    private final String TAG = ArtistSearchActivityFragment.class.getSimpleName();

    private EditText mSearchQueryText;
    private String mSearchQuery = "";

    private ArrayList<Artist> mArtists;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mArtistAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ArtistSearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_search, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.artist_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if ((savedInstanceState != null)
                && (savedInstanceState.getSerializable("SEARCH_RESULTS") != null)) {
            mArtists = savedInstanceState.getParcelableArrayList("SEARCH_RESULTS");
            mSearchQuery = savedInstanceState.getString("SEARCH_QUERY");
        } else {
            mArtists = new ArrayList<>();
        }
        mArtistAdapter = new ArtistAdapter(mArtists);
        mRecyclerView.setAdapter(mArtistAdapter);
        mSearchQueryText = (EditText) rootView.findViewById(R.id.artist_search_edit_text);
        if (!mSearchQuery.equals("")) {
            mSearchQueryText.setText(mSearchQuery);
        }
        mSearchQueryText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    mSearchQuery = v.getText().toString().replace(" ", "+");
                    searchArtist(mSearchQuery);
                    InputMethodManager inputManager = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    handled = true;
                }
                return handled;
            }
        });
        return rootView;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("SEARCH_RESULTS", mArtists);
        savedInstanceState.putString("SEARCH_QUERY", mSearchQuery);
    }

    private void searchArtist(String query) {
        if (query.equals("")) {
            String msg = "Your search query is empty, please refine your search and try again.";
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        } else {
            SearchArtistDataTask artistTask = new SearchArtistDataTask();
            artistTask.execute(query);
        }
    }

    public class SearchArtistDataTask extends AsyncTask<String, Void, Void> {
        private final String TAG = SearchArtistDataTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(params[0]);
            if (results.artists.items == null) {
                return null;
            } else {
                Log.d(TAG, "results: " + results);
                updateArtistsFromResults(results.artists.items);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if (mArtists.size() == 0) {
                String msg = "Couldn't find any artists, please refine your search and try again.";
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                return;
            }
            mArtistAdapter.notifyDataSetChanged();
        }

        private Void updateArtistsFromResults(
                List<kaaes.spotify.webapi.android.models.Artist> artistData) {
            mArtists.clear();
            for (kaaes.spotify.webapi.android.models.Artist artist : artistData) {
                String artistName = artist.name;
                String imageUrl;
                if (artist.images.size() > 0) {
                    imageUrl = artist.images.get(0).url;
                } else {
                    imageUrl = "";
                }
                mArtists.add(new Artist(artistName, imageUrl));
            }
            return null;
        }
    }
}
