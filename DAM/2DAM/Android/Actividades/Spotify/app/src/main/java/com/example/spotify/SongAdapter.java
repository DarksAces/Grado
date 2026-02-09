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

    private Context context_DGB;
    private List<Song> songList_DGB;
    private OnItemClickListener listener_DGB;

    public interface OnItemClickListener {
        void onItemClick(int position_DGB);
    }

    public SongAdapter(Context context_DGB, List<Song> songList_DGB, OnItemClickListener listener_DGB) {
        this.context_DGB = context_DGB;
        this.songList_DGB = songList_DGB;
        this.listener_DGB = listener_DGB;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent_DGB, int viewType_DGB) {
        View view_DGB = LayoutInflater.from(context_DGB).inflate(R.layout.item_song, parent_DGB, false);
        return new SongViewHolder(view_DGB);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder_DGB, int position_DGB) {
        Song song_DGB = songList_DGB.get(position_DGB);
        holder_DGB.txtTitle_DGB.setText(song_DGB.getTitle());
        holder_DGB.txtArtist_DGB.setText(song_DGB.getArtist());
        holder_DGB.txtAlbum_DGB.setText(song_DGB.getAlbum());

        // Load album art using Glide
        // If path is available, can load from file or metadata.
        // For simplicity, we can load from the file path directly if it has an embedded
        // image,
        // or just use a placeholder. Glide handles audio files by extracting album art
        // on some versions/configs,
        // but explicit Uri parsing is safer.
        // Ideally we should have an album art URI, but we can pass the file path or a
        // default image.

        if (song_DGB.getAlbumId() != -1) {
            // Carga normal para canciones del sistema
            Uri albumArtUri_DGB = android.content.ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"), song_DGB.getAlbumId());
            Glide.with(context_DGB)
                    .asBitmap()
                    .load(albumArtUri_DGB)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder_DGB.imgAlbumArt_DGB);
        } else {
            // Carga MANUAL para archivos sueltos (Descargas) usando MediaMetadataRetriever
            holder_DGB.imgAlbumArt_DGB.setImageResource(R.drawable.ic_launcher_background);
            new Thread(() -> {
                android.media.MediaMetadataRetriever retriever_DGB = new android.media.MediaMetadataRetriever();
                try {
                    retriever_DGB.setDataSource(context_DGB, Uri.parse(song_DGB.getPath()));
                    byte[] art_DGB = retriever_DGB.getEmbeddedPicture();
                    if (art_DGB != null) {
                        android.graphics.Bitmap bitmap_DGB = android.graphics.BitmapFactory.decodeByteArray(art_DGB, 0, art_DGB.length);
                        holder_DGB.itemView.post(() -> holder_DGB.imgAlbumArt_DGB.setImageBitmap(bitmap_DGB));
                    }
                } catch (Exception e_DGB) {
                    e_DGB.printStackTrace();
                } finally {
                    try { retriever_DGB.release(); } catch (Exception e_DGB) { }
                }
            }).start();
        }

        holder_DGB.itemView.setOnClickListener(v_DGB -> listener_DGB.onItemClick(position_DGB));
    }

    @Override
    public int getItemCount() {
        return songList_DGB.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAlbumArt_DGB;
        TextView txtTitle_DGB, txtArtist_DGB, txtAlbum_DGB;

        public SongViewHolder(@NonNull View itemView_DGB) {
            super(itemView_DGB);
            imgAlbumArt_DGB = itemView_DGB.findViewById(R.id.imgAlbumArt);
            txtTitle_DGB = itemView_DGB.findViewById(R.id.txtTitle);
            txtArtist_DGB = itemView_DGB.findViewById(R.id.txtArtist);
            txtAlbum_DGB = itemView_DGB.findViewById(R.id.txtAlbum);
        }
    }
}
