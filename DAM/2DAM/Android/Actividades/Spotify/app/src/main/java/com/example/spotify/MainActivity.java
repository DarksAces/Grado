package com.example.spotify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE_DGB = 100;
    private RecyclerView recyclerView_DGB;
    private SongAdapter adapter_DGB;
    private List<Song> songList_DGB;
    private TextView txtEmptyState_DGB;

    @Override
    protected void onCreate(Bundle savedInstanceState_DGB) {
        super.onCreate(savedInstanceState_DGB);
        setContentView(R.layout.activity_main);

        recyclerView_DGB = findViewById(R.id.recyclerView);
        txtEmptyState_DGB = findViewById(R.id.txtEmptyState);
        recyclerView_DGB.setLayoutManager(new LinearLayoutManager(this));
        songList_DGB = new ArrayList<>();

        if (checkPermission()) {
            loadSongs();
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_MEDIA_AUDIO },
                    PERMISSION_REQUEST_CODE_DGB);
        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE_DGB);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode_DGB, @NonNull String[] permissions_DGB,
            @NonNull int[] grantResults_DGB) {
        super.onRequestPermissionsResult(requestCode_DGB, permissions_DGB, grantResults_DGB);
        if (requestCode_DGB == PERMISSION_REQUEST_CODE_DGB) {
            if (grantResults_DGB.length > 0 && grantResults_DGB[0] == PackageManager.PERMISSION_GRANTED) {
                loadSongs();
            } else {
                Toast.makeText(this, "Permission Denied. Please allow permission to load songs.", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void loadSongs() {
        songList_DGB.clear();
        java.util.HashSet<String> addedPaths_DGB = new java.util.HashSet<>();

        // 1. Cargar desde MediaStore (Sistema)
        try (android.database.Cursor cursor_DGB = getContentResolver().query(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0",
                null,
                android.provider.MediaStore.Audio.Media.TITLE + " ASC")) {

            if (cursor_DGB != null) {
                int idColumn_DGB = cursor_DGB.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
                int titleColumn_DGB = cursor_DGB.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
                int artistColumn_DGB = cursor_DGB.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
                int albumColumn_DGB = cursor_DGB.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM);
                int durationColumn_DGB = cursor_DGB.getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION);
                int albumIdColumn_DGB = cursor_DGB.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM_ID);
                
                while (cursor_DGB.moveToNext()) {
                    long id_DGB = cursor_DGB.getLong(idColumn_DGB);
                    String title_DGB = cursor_DGB.getString(titleColumn_DGB);
                    String artist_DGB = cursor_DGB.getString(artistColumn_DGB);
                    String album_DGB = cursor_DGB.getString(albumColumn_DGB);
                    long duration_DGB = cursor_DGB.getLong(durationColumn_DGB);
                    long albumId_DGB = cursor_DGB.getLong(albumIdColumn_DGB);

                    android.net.Uri contentUri_DGB = android.content.ContentUris.withAppendedId(
                            android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id_DGB);

                    if (artist_DGB == null || artist_DGB.equals("<unknown>")) artist_DGB = "Unknown Artist";
                    if (album_DGB == null || album_DGB.equals("<unknown>")) album_DGB = "Unknown Album";

                    String uriString_DGB = contentUri_DGB.toString();
                    songList_DGB.add(new Song(title_DGB, artist_DGB, album_DGB, uriString_DGB, duration_DGB, albumId_DGB));
                    addedPaths_DGB.add(uriString_DGB);
                }
            }
        } catch (Exception e_DGB) {
            Log.e("MainActivity", "Error querying MediaStore", e_DGB);
        }

        // 2. Escanear carpeta de Descargas manualmente (SIEMPRE, para asegurar)
        File downloadsFolder_DGB = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (downloadsFolder_DGB != null && downloadsFolder_DGB.exists() && downloadsFolder_DGB.isDirectory()) {
            File[] files_DGB = downloadsFolder_DGB.listFiles();
            if (files_DGB != null) {
                for (File file_DGB : files_DGB) {
                    if (file_DGB.isFile() && file_DGB.getName().toLowerCase().endsWith(".mp3")) {
                        String distinctPath_DGB = Uri.fromFile(file_DGB).toString();
                        
                        MediaMetadataRetriever retriever_DGB = new MediaMetadataRetriever();
                        String title_DGB = file_DGB.getName();
                        if (title_DGB.lastIndexOf(".") > 0) title_DGB = title_DGB.substring(0, title_DGB.lastIndexOf("."));
                        String artist_DGB = "Unknown Artist";
                        String album_DGB = "Unknown Album";
                        long duration_DGB = 0;

                        try {
                            retriever_DGB.setDataSource(this, Uri.fromFile(file_DGB));
                            String t_DGB = retriever_DGB.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                            String a_DGB = retriever_DGB.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                            String al_DGB = retriever_DGB.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                            String d_DGB = retriever_DGB.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                            if (t_DGB != null && !t_DGB.isEmpty()) title_DGB = t_DGB;
                            if (a_DGB != null && !a_DGB.isEmpty()) artist_DGB = a_DGB;
                            if (al_DGB != null && !al_DGB.isEmpty()) album_DGB = al_DGB;
                            if (d_DGB != null && !d_DGB.isEmpty()) duration_DGB = Long.parseLong(d_DGB);
                        } catch (Exception e_DGB) {
                            Log.e("MainActivity", "Error manual metadata", e_DGB);
                        } finally {
                            try { retriever_DGB.release(); } catch (IOException e_DGB) { e_DGB.printStackTrace(); }
                        }
                        
                        // Añadimos a la lista con albumId -1 (no disponible desde archivo directo fácilmente)
                        songList_DGB.add(new Song(title_DGB, artist_DGB, album_DGB, distinctPath_DGB, duration_DGB, -1));
                    }
                }
            }
        }

        Toast.makeText(this, "Encontradas: " + songList_DGB.size() + " canciones", Toast.LENGTH_SHORT).show();

        if (songList_DGB.isEmpty()) {
            txtEmptyState_DGB.setVisibility(View.VISIBLE);
            recyclerView_DGB.setVisibility(View.GONE);
        } else {
            txtEmptyState_DGB.setVisibility(View.GONE);
            recyclerView_DGB.setVisibility(View.VISIBLE);
            adapter_DGB = new SongAdapter(this, songList_DGB, position_DGB -> {
                Intent intent_DGB = new Intent(MainActivity.this, PlayerActivity.class);
                intent_DGB.putParcelableArrayListExtra("SONG_LIST", (ArrayList<Song>) songList_DGB);
                intent_DGB.putExtra("POSITION", position_DGB);
                startActivity(intent_DGB);
            });
            recyclerView_DGB.setAdapter(adapter_DGB);
        }
    }
}
 