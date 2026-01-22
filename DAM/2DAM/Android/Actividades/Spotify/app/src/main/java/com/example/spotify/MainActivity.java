package com.example.spotify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
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
        File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        if (downloadsFolder != null && downloadsFolder.exists() && downloadsFolder.isDirectory()) {
            File[] files = downloadsFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                        Song song = extractSongData(file);
                        songList.add(song);
                    }
                }
            }
        }

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

    private Song extractSongData(File file) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        String title = file.getName();
        String artist = "Unknown Artist";
        String album = "Unknown Album";
        long duration = 0;

        try {
            retriever.setDataSource(file.getAbsolutePath());
            String extractedTitle = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String extractedArtist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String extractedAlbum = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String extractedDuration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            if (extractedTitle != null && !extractedTitle.isEmpty())
                title = extractedTitle;
            if (extractedArtist != null && !extractedArtist.isEmpty())
                artist = extractedArtist;
            if (extractedAlbum != null && !extractedAlbum.isEmpty())
                album = extractedAlbum;
            if (extractedDuration != null && !extractedDuration.isEmpty())
                duration = Long.parseLong(extractedDuration);
        } catch (Exception e) {
            Log.e("MainActivity", "Error extracting metadata", e);
        }

        return new Song(title, artist, album, file.getAbsolutePath(), duration);
    }
}