package me.weppler.android.spotifystreamer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mattweppler on 6/15/15.
 */
public class Artist implements Parcelable {
    private String imageUrl;
    private String name;
    private String spotifyId;

    public Artist() {}

    public Artist(String name, String imageUrl, String spotifyId) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.spotifyId = spotifyId;
    }

    private Artist(Parcel parcel) {
        imageUrl = parcel.readString();
        name = parcel.readString();
        spotifyId = parcel.readString();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    @Override
    public String toString() {
        return "\"name\": \"" + name + "\"," +
                "\"imageUrl\":" + imageUrl + "\"," +
                "\"spotifyId\":" + spotifyId + "\"";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(imageUrl);
        parcel.writeString(name);
        parcel.writeString(spotifyId);
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel parcel) {
            return new Artist(parcel);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
