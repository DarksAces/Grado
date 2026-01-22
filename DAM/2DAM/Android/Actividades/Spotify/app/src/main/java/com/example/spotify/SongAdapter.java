package com.example.spotify;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private Context context;
    private List<Song> songList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SongAdapter(Context context, List<Song> songList, OnItemClickListener listener) {
        this.context = context;
        this.songList = songList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.txtTitle.setText(song.getTitle());
        holder.txtArtist.setText(song.getArtist());
        holder.txtAlbum.setText(song.getAlbum());

        // Load album art using Glide
        // If path is available, can load from file or metadata.
        // For simplicity, we can load from the file path directly if it has an embedded
        // image,
        // or just use a placeholder. Glide handles audio files by extracting album art
        // on some versions/configs,
        // but explicit Uri parsing is safer.
        // Ideally we should have an album art URI, but we can pass the file path or a
        // default image.

        Glide.with(context)
                .load(song.getPath()) // Glide can often extract album art from audio files
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgAlbumArt);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAlbumArt;
        TextView txtTitle, txtArtist, txtAlbum;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbumArt = itemView.findViewById(R.id.imgAlbumArt);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtArtist = itemView.findViewById(R.id.txtArtist);
            txtAlbum = itemView.findViewById(R.id.txtAlbum);
        }
    }
}
