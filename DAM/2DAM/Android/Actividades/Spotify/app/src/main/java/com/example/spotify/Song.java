package com.example.spotify;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private String title;
    private String artist;
    private String album;
    private String path;
    private long duration;
    private long albumId;

    public Song(String title, String artist, String album, String path, long duration, long albumId) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.path = path;
        this.duration = duration;
        this.albumId = albumId;
    }

    protected Song(Parcel in) {
        title = in.readString();
        artist = in.readString();
        album = in.readString();
        path = in.readString();
        duration = in.readLong();
        albumId = in.readLong();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(album);
        dest.writeString(path);
        dest.writeLong(duration);
        dest.writeLong(albumId);
    }

    // Getters

    public long getAlbumId() {
        return albumId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getPath() {
        return path;
    }

    public long getDuration() {
        return duration;
    }
}
