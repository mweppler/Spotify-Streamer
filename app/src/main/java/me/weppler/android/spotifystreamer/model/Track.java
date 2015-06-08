/**
 * Created by mattweppler on 6/8/15.
 */

package me.weppler.android.spotifystreamer.model;

public class Track {
    private String album;
    private String name;

    public Track() {

    }

    public Track(String name, String album) {
        this.album = album;
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public String getName() {
        return name;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " - " + album;
    }
}
