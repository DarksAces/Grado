package com.example.spotify;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private String title_DGB;
    private String artist_DGB;
    private String album_DGB;
    private String path_DGB;
    private long duration_DGB;
    private long albumId_DGB;

    public Song(String title_DGB, String artist_DGB, String album_DGB, String path_DGB, long duration_DGB, long albumId_DGB) {
        this.title_DGB = title_DGB;
        this.artist_DGB = artist_DGB;
        this.album_DGB = album_DGB;
        this.path_DGB = path_DGB;
        this.duration_DGB = duration_DGB;
        this.albumId_DGB = albumId_DGB;
    }

    protected Song(Parcel in_DGB) {
        title_DGB = in_DGB.readString();
        artist_DGB = in_DGB.readString();
        album_DGB = in_DGB.readString();
        path_DGB = in_DGB.readString();
        duration_DGB = in_DGB.readLong();
        albumId_DGB = in_DGB.readLong();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in_DGB) {
            return new Song(in_DGB);
        }

        @Override
        public Song[] newArray(int size_DGB) {
            return new Song[size_DGB];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest_DGB, int flags_DGB) {
        dest_DGB.writeString(title_DGB);
        dest_DGB.writeString(artist_DGB);
        dest_DGB.writeString(album_DGB);
        dest_DGB.writeString(path_DGB);
        dest_DGB.writeLong(duration_DGB);
        dest_DGB.writeLong(albumId_DGB);
    }

    // Getters

    public long getAlbumId() {
        return albumId_DGB;
    }

    public String getTitle() {
        return title_DGB;
    }

    public String getArtist() {
        return artist_DGB;
    }

    public String getAlbum() {
        return album_DGB;
    }

    public String getPath() {
        return path_DGB;
    }

    public long getDuration() {
        return duration_DGB;
    }
}
