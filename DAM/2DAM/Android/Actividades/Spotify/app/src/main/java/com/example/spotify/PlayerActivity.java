package com.example.spotify;

import android.content.ContentUris;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    private ImageView imgAlbumArt_DGB;
    private TextView txtTitle_DGB, txtArtist_DGB, txtCurrentTime_DGB, txtTotalTime_DGB;
    private SeekBar seekBar_DGB;
    private ImageButton btnPlayPause_DGB, btnPrev_DGB, btnNext_DGB, btnRewind_DGB, btnForward_DGB, btnBack_DGB;
    private Button btnStop_DGB;

    private MediaPlayer mediaPlayer_DGB;
    private ArrayList<Song> songList_DGB;
    private int position_DGB;
    private final Handler handler_DGB = new Handler();
    private Runnable updateSeekBarAction_DGB;

    @Override
    protected void onCreate(Bundle savedInstanceState_DGB) {
        super.onCreate(savedInstanceState_DGB);
        setContentView(R.layout.activity_player);

        initViews();
        getIntentData();
        initializeMediaPlayer();
        setupListeners();
    }

    private void initViews() {
        imgAlbumArt_DGB = findViewById(R.id.imgPlayerAlbumArt);
        txtTitle_DGB = findViewById(R.id.txtPlayerTitle);
        txtArtist_DGB = findViewById(R.id.txtPlayerArtist);
        txtCurrentTime_DGB = findViewById(R.id.txtCurrentTime);
        txtTotalTime_DGB = findViewById(R.id.txtTotalTime);
        seekBar_DGB = findViewById(R.id.seekBar);
        btnPlayPause_DGB = findViewById(R.id.btnPlayPause);
        btnPrev_DGB = findViewById(R.id.btnPrev);
        btnNext_DGB = findViewById(R.id.btnNext);
        btnRewind_DGB = findViewById(R.id.btnRewind);
        btnForward_DGB = findViewById(R.id.btnForward);
        btnStop_DGB = findViewById(R.id.btnStop);
        btnBack_DGB = findViewById(R.id.btnBack);
    }

    private void getIntentData() {
        songList_DGB = getIntent().getParcelableArrayListExtra("SONG_LIST");
        position_DGB = getIntent().getIntExtra("POSITION", 0);
    }

    private void initializeMediaPlayer() {
        if (songList_DGB == null || songList_DGB.isEmpty()) return;

        if (mediaPlayer_DGB != null) {
            mediaPlayer_DGB.release();
            mediaPlayer_DGB = null;
        }

        Song currentSong = songList_DGB.get(position_DGB);
        txtTitle_DGB.setText(currentSong.getTitle());
        txtArtist_DGB.setText(currentSong.getArtist());
        
        // Cargar portada antes de iniciar la música
        loadAlbumArt(currentSong);

        try {
            mediaPlayer_DGB = new MediaPlayer();
            mediaPlayer_DGB.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build());

            Uri trackUri = Uri.parse(currentSong.getPath());
            mediaPlayer_DGB.setDataSource(this, trackUri);
            
            mediaPlayer_DGB.prepareAsync();
            mediaPlayer_DGB.setOnPreparedListener(mp -> {
                txtTotalTime_DGB.setText(formatDuration(mp.getDuration()));
                seekBar_DGB.setMax(mp.getDuration());
                playMusic();
            });

            mediaPlayer_DGB.setOnErrorListener((mp, what, extra) -> {
                Log.e("Player", "Error: " + what + " Extra: " + extra);
                return true;
            });

            mediaPlayer_DGB.setOnCompletionListener(mp -> playNext());

        } catch (IOException e) {
            Log.e("Player", "Error initializing player", e);
        }
    }

    private void loadAlbumArt(Song song) {
        if (song.getAlbumId() != -1) {
            // Portada desde MediaStore
            Uri artworkUri = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"), song.getAlbumId());
            Glide.with(this)
                    .load(artworkUri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgAlbumArt_DGB);
        } else {
            // Portada manual desde el archivo MP3 (Descargas)
            imgAlbumArt_DGB.setImageResource(R.drawable.ic_launcher_background);
            new Thread(() -> {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                try {
                    retriever.setDataSource(this, Uri.parse(song.getPath()));
                    byte[] art = retriever.getEmbeddedPicture();
                    if (art != null) {
                        runOnUiThread(() -> Glide.with(PlayerActivity.this)
                                .load(art)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(imgAlbumArt_DGB));
                    }
                } catch (Exception e) {
                    Log.e("PlayerArt", "Error cargando portada: " + e.getMessage());
                } finally {
                    try { retriever.release(); } catch (Exception e) {}
                }
            }).start();
        }
    }

    private void playMusic() {
        if (mediaPlayer_DGB != null) {
            mediaPlayer_DGB.start();
            btnPlayPause_DGB.setImageResource(android.R.drawable.ic_media_pause);
            updateSeekBar();
        }
    }

    private void pauseMusic() {
        if (mediaPlayer_DGB != null && mediaPlayer_DGB.isPlaying()) {
            mediaPlayer_DGB.pause();
            btnPlayPause_DGB.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    private void playNext() {
        position_DGB = (position_DGB + 1) % songList_DGB.size();
        initializeMediaPlayer();
    }

    private void playPrev() {
        position_DGB = (position_DGB - 1 < 0) ? (songList_DGB.size() - 1) : (position_DGB - 1);
        initializeMediaPlayer();
    }

    private void updateSeekBar() {
        handler_DGB.removeCallbacks(updateSeekBarAction_DGB);
        updateSeekBarAction_DGB = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer_DGB != null && mediaPlayer_DGB.isPlaying()) {
                    seekBar_DGB.setProgress(mediaPlayer_DGB.getCurrentPosition());
                    txtCurrentTime_DGB.setText(formatDuration(mediaPlayer_DGB.getCurrentPosition()));
                }
                handler_DGB.postDelayed(this, 1000);
            }
        };
        handler_DGB.post(updateSeekBarAction_DGB);
    }

    private String formatDuration(long duration) {
        long min = TimeUnit.MILLISECONDS.toMinutes(duration);
        long sec = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(min);
        return String.format("%02d:%02d", min, sec);
    }

    private void setupListeners() {
        btnPlayPause_DGB.setOnClickListener(v -> {
            if (mediaPlayer_DGB != null && mediaPlayer_DGB.isPlaying()) pauseMusic();
            else playMusic();
        });
        btnNext_DGB.setOnClickListener(v -> playNext());
        btnPrev_DGB.setOnClickListener(v -> playPrev());
        btnBack_DGB.setOnClickListener(v -> finish());
        
        btnRewind_DGB.setOnClickListener(v -> {
            if (mediaPlayer_DGB != null) mediaPlayer_DGB.seekTo(Math.max(0, mediaPlayer_DGB.getCurrentPosition() - 10000));
        });
        btnForward_DGB.setOnClickListener(v -> {
            if (mediaPlayer_DGB != null) mediaPlayer_DGB.seekTo(Math.min(mediaPlayer_DGB.getDuration(), mediaPlayer_DGB.getCurrentPosition() + 10000));
        });
        btnStop_DGB.setOnClickListener(v -> {
            if (mediaPlayer_DGB != null) {
                mediaPlayer_DGB.pause();
                mediaPlayer_DGB.seekTo(0);
                btnPlayPause_DGB.setImageResource(android.R.drawable.ic_media_play);
                txtCurrentTime_DGB.setText(formatDuration(0));
                seekBar_DGB.setProgress(0);
            }
        });

        seekBar_DGB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer_DGB != null) mediaPlayer_DGB.seekTo(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer_DGB != null) {
            mediaPlayer_DGB.release();
            mediaPlayer_DGB = null;
        }
        handler_DGB.removeCallbacks(updateSeekBarAction_DGB);
    }
}
