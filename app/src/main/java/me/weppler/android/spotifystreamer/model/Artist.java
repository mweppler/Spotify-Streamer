package me.weppler.android.spotifystreamer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mattweppler on 6/15/15.
 */
public class Artist implements Parcelable {
    private String imageUrl;
    private String name;

    public Artist() {}

    public Artist(String name, String imageUrl) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    private Artist(Parcel parcel) {
        imageUrl = parcel.readString();
        name = parcel.readString();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\"name\": \"" + name + "\", \"imageUrl\":" + imageUrl + "\"";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(imageUrl);
        parcel.writeString(name);
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
