package com.example.spotify;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    private ImageView imgAlbumArt;
    private TextView txtTitle, txtArtist, txtCurrentTime, txtTotalTime;
    private SeekBar seekBar;
    private ImageButton btnPlayPause, btnPrev, btnNext, btnRewind, btnForward, btnBack;
    private Button btnStop;

    private MediaPlayer mediaPlayer;
    private ArrayList<Song> songList;
    private int position;
    private Handler handler = new Handler();
    private Runnable updateSeekBarAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initViews();
        getIntentData();

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("POSITION", 0);
            int currentPos = savedInstanceState.getInt("CURRENT_POS", 0);
            boolean wasPlaying = savedInstanceState.getBoolean("IS_PLAYING", false);

            initializeMediaPlayer();
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(currentPos);
                if (wasPlaying) {
                    playMusic();
                } else {
                    // Update UI but don't start
                    txtCurrentTime.setText(formatDuration(currentPos));
                    seekBar.setProgress(currentPos);
                    btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        } else {
            initializeMediaPlayer();
            playMusic(); // Auto-play if fresh start
        }

        setupListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("POSITION", position);
        if (mediaPlayer != null) {
            outState.putInt("CURRENT_POS", mediaPlayer.getCurrentPosition());
            outState.putBoolean("IS_PLAYING", mediaPlayer.isPlaying());
        }
    }

    private void initViews() {
        imgAlbumArt = findViewById(R.id.imgPlayerAlbumArt);
        txtTitle = findViewById(R.id.txtPlayerTitle);
        txtArtist = findViewById(R.id.txtPlayerArtist);
        txtCurrentTime = findViewById(R.id.txtCurrentTime);
        txtTotalTime = findViewById(R.id.txtTotalTime);
        seekBar = findViewById(R.id.seekBar);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnRewind = findViewById(R.id.btnRewind);
        btnForward = findViewById(R.id.btnForward);
        btnStop = findViewById(R.id.btnStop);
        btnBack = findViewById(R.id.btnBack);
    }

    private void getIntentData() {
        songList = getIntent().getParcelableArrayListExtra("SONG_LIST");
        position = getIntent().getIntExtra("POSITION", 0);
    }

    private void initializeMediaPlayer() {
        if (songList != null && !songList.isEmpty()) {
            Song currentSong = songList.get(position);
            txtTitle.setText(currentSong.getTitle());
            txtArtist.setText(currentSong.getArtist());

                if (currentSong.getAlbumId() != -1) {
                    Uri artworkUri = android.content.ContentUris.withAppendedId(
                            Uri.parse("content://media/external/audio/albumart"), currentSong.getAlbumId());
                    Glide.with(this)
                            .asBitmap()
                            .load(artworkUri)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(imgAlbumArt);
                } else {
                    imgAlbumArt.setImageResource(R.drawable.ic_launcher_background);
                    new Thread(() -> {
                        android.media.MediaMetadataRetriever retriever = new android.media.MediaMetadataRetriever();
                        try {
                            retriever.setDataSource(this, Uri.parse(currentSong.getPath()));
                            byte[] art = retriever.getEmbeddedPicture();
                            if (art != null) {
                                android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeByteArray(art, 0, art.length);
                                runOnUiThread(() -> imgAlbumArt.setImageBitmap(bitmap));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try { retriever.release(); } catch (Exception e) { }
                        }
                    }).start();
                }

            String path = currentSong.getPath();
            Uri uri;
            if (path.startsWith("content://")) {
                uri = Uri.parse(path);
            } else {
                // Si es una ruta de archivo pura (/storage...), aseguramos que tenga file://
                if (path.startsWith("/") && !path.startsWith("file://")) {
                    path = "file://" + path;
                }
                uri = Uri.parse(path);
            }

            if (mediaPlayer != null) {
                mediaPlayer.reset(); // Usar reset en lugar de stop/release para reutilizar si es posible, o release para limpiar
                mediaPlayer.release();
                mediaPlayer = null;
            }

            try {
                mediaPlayer = MediaPlayer.create(this, uri);
                
                if (mediaPlayer != null) {
                    mediaPlayer.setOnCompletionListener(mp -> playNext());
                    txtTotalTime.setText(formatDuration(mediaPlayer.getDuration()));
                    seekBar.setMax(mediaPlayer.getDuration());
                    playMusic();
                } else {
                    // Si create devuelve null, a veces es porque el archivo no es leíble o codec no soportado
                    Toast.makeText(this, "Error: Codec no soportado o archivo dañado.", Toast.LENGTH_LONG).show();
                    // Intentar método alternativo setDataSource
                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(this, uri);
                        mediaPlayer.prepare();
                        mediaPlayer.setOnCompletionListener(mp -> playNext());
                        txtTotalTime.setText(formatDuration(mediaPlayer.getDuration()));
                        seekBar.setMax(mediaPlayer.getDuration());
                        playMusic();
                    } catch (IOException io) {
                        Toast.makeText(this, "Error crítico al abrir archivo.", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Excepción al reproducir: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
            updateSeekBar();
        }
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
            handler.removeCallbacks(updateSeekBarAction);
        }
    }

    private void playNext() {
        if (songList == null || songList.isEmpty())
            return;
        position = (position + 1) % songList.size();
        initializeMediaPlayer();
        playMusic();
    }

    private void playPrev() {
        if (songList == null || songList.isEmpty())
            return;
        position = (position - 1 < 0) ? (songList.size() - 1) : (position - 1);
        initializeMediaPlayer();
        playMusic();
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            // Requirement says reset or stop. Stopping usually releases or seeks to 0.
            // "Volver a la primera actividad. La canción se ha de dejar de escuchar."
            // But there is also a "Stop" button required which just stops playback usually.
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare(); // Prepare for next start
                mediaPlayer.seekTo(0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
            if (handler != null && updateSeekBarAction != null) {
                handler.removeCallbacks(updateSeekBarAction);
            }
            seekBar.setProgress(0);
            txtCurrentTime.setText(formatDuration(0));
        }
    }

    private void updateSeekBar() {
        updateSeekBarAction = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                    txtCurrentTime.setText(formatDuration(currentPosition));
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(updateSeekBarAction);
    }

    private String formatDuration(long duration) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void setupListeners() {
        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                pauseMusic();
            } else {
                playMusic();
            }
        });

        btnPrev.setOnClickListener(v -> playPrev());
        btnNext.setOnClickListener(v -> playNext());

        btnRewind.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int seekTo = Math.max(0, currentPosition - 10000); // Rewind 10s
                mediaPlayer.seekTo(seekTo);
            }
        });

        btnForward.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                int seekTo = Math.min(duration, currentPosition + 10000); // Forward 10s
                mediaPlayer.seekTo(seekTo);
            }
        });

        btnStop.setOnClickListener(v -> {
            stopMusic();
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                    txtCurrentTime.setText(formatDuration(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            updateSeekBar();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null && updateSeekBarAction != null) {
            handler.removeCallbacks(updateSeekBarAction);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateSeekBarAction);
    }
}
