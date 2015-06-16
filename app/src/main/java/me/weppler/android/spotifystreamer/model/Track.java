/**
 * Created by mattweppler on 6/8/15.
 */

package me.weppler.android.spotifystreamer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
    private String albumImageUrl;
    private String albumName;
    private String artistName;
    private String name;
    private String playbackUrl;
    private String spotifyId;

    public Track() {}

    public Track(String artistName, String albumName, String name, String albumImageUrl,
                 String playbackUrl, String spotifyId) {
        this.albumImageUrl = albumImageUrl;
        this.albumName = albumName;
        this.artistName = artistName;
        this.name = name;
        this.playbackUrl = playbackUrl;
        this.spotifyId = spotifyId;
    }

    private Track(Parcel parcel) {
        albumImageUrl = parcel.readString();
        albumName = parcel.readString();
        artistName = parcel.readString();
        name = parcel.readString();
        playbackUrl = parcel.readString();
        spotifyId = parcel.readString();
    }

    public String getAlbumImageUrl() {
        return albumImageUrl;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getName() {
        return name;
    }

    public String getPlaybackUrl() {
        return playbackUrl;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setAlbumImageUrl(String albumImageUrl) {
        this.albumImageUrl = albumImageUrl;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaybackUrl(String playbackUrl) {
        this.playbackUrl = playbackUrl;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    @Override
    public String toString() {
        return " \"albumImageUrl\":" + albumImageUrl + "\"," +
                " \"albumName\":" + albumName + "\"," +
                "\"artistName\": \"" + artistName + "\"," +
                " \"name\":" + name + "\"," +
                " \"playbackUrl\":" + playbackUrl + "\"" +
                " \"spotifyId\":" + spotifyId + "\"";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(albumImageUrl);
        parcel.writeString(albumName);
        parcel.writeString(artistName);
        parcel.writeString(name);
        parcel.writeString(playbackUrl);
        parcel.writeString(spotifyId);
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel parcel) {
            return new Track(parcel);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
