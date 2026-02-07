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

    private static final int PERMISSION_REQUEST_CODE = 100;
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private List<Song> songList;
    private TextView txtEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        txtEmptyState = findViewById(R.id.txtEmptyState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songList = new ArrayList<>();

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
                    PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadSongs();
            } else {
                Toast.makeText(this, "Permission Denied. Please allow permission to load songs.", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void loadSongs() {
        songList.clear();
        java.util.HashSet<String> addedPaths = new java.util.HashSet<>();

        // 1. Cargar desde MediaStore (Sistema)
        try (android.database.Cursor cursor = getContentResolver().query(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0",
                null,
                android.provider.MediaStore.Audio.Media.TITLE + " ASC")) {

            if (cursor != null) {
                int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
                int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
                int artistColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
                int albumColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM);
                int durationColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION);
                int albumIdColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM_ID);
                
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idColumn);
                    String title = cursor.getString(titleColumn);
                    String artist = cursor.getString(artistColumn);
                    String album = cursor.getString(albumColumn);
                    long duration = cursor.getLong(durationColumn);
                    long albumId = cursor.getLong(albumIdColumn);

                    android.net.Uri contentUri = android.content.ContentUris.withAppendedId(
                            android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                    if (artist == null || artist.equals("<unknown>")) artist = "Unknown Artist";
                    if (album == null || album.equals("<unknown>")) album = "Unknown Album";

                    String uriString = contentUri.toString();
                    songList.add(new Song(title, artist, album, uriString, duration, albumId));
                    addedPaths.add(uriString);
                }
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error querying MediaStore", e);
        }

        // 2. Escanear carpeta de Descargas manualmente (SIEMPRE, para asegurar)
        File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (downloadsFolder != null && downloadsFolder.exists() && downloadsFolder.isDirectory()) {
            File[] files = downloadsFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                        String distinctPath = Uri.fromFile(file).toString();
                        
                        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                        String title = file.getName();
                        if (title.lastIndexOf(".") > 0) title = title.substring(0, title.lastIndexOf("."));
                        String artist = "Unknown Artist";
                        String album = "Unknown Album";
                        long duration = 0;

                        try {
                            retriever.setDataSource(this, Uri.fromFile(file));
                            String t = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                            String a = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                            String al = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                            String d = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                            if (t != null && !t.isEmpty()) title = t;
                            if (a != null && !a.isEmpty()) artist = a;
                            if (al != null && !al.isEmpty()) album = al;
                            if (d != null && !d.isEmpty()) duration = Long.parseLong(d);
                        } catch (Exception e) {
                            Log.e("MainActivity", "Error manual metadata", e);
                        } finally {
                            try { retriever.release(); } catch (IOException e) { e.printStackTrace(); }
                        }
                        
                        // Añadimos a la lista con albumId -1 (no disponible desde archivo directo fácilmente)
                        songList.add(new Song(title, artist, album, distinctPath, duration, -1));
                    }
                }
            }
        }

        Toast.makeText(this, "Encontradas: " + songList.size() + " canciones", Toast.LENGTH_SHORT).show();

        if (songList.isEmpty()) {
            txtEmptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new SongAdapter(this, songList, position -> {
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putParcelableArrayListExtra("SONG_LIST", (ArrayList<Song>) songList);
                intent.putExtra("POSITION", position);
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        }
    }
} 