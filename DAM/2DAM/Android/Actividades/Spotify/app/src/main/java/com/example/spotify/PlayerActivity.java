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

    private ImageView imgAlbumArt_DGB;
    private TextView txtTitle_DGB, txtArtist_DGB, txtCurrentTime_DGB, txtTotalTime_DGB;
    private SeekBar seekBar_DGB;
    private ImageButton btnPlayPause_DGB, btnPrev_DGB, btnNext_DGB, btnRewind_DGB, btnForward_DGB, btnBack_DGB;
    private Button btnStop_DGB;

    private MediaPlayer mediaPlayer_DGB;
    private ArrayList<Song> songList_DGB;
    private int position_DGB;
    private Handler handler_DGB = new Handler();
    private Runnable updateSeekBarAction_DGB;

    @Override
    protected void onCreate(Bundle savedInstanceState_DGB) {
        super.onCreate(savedInstanceState_DGB);
        setContentView(R.layout.activity_player);

        initViews();
        getIntentData();

        if (savedInstanceState_DGB != null) {
            position_DGB = savedInstanceState_DGB.getInt("POSITION", 0);
            int currentPos_DGB = savedInstanceState_DGB.getInt("CURRENT_POS", 0);
            boolean wasPlaying_DGB = savedInstanceState_DGB.getBoolean("IS_PLAYING", false);

            initializeMediaPlayer();
            if (mediaPlayer_DGB != null) {
                mediaPlayer_DGB.seekTo(currentPos_DGB);
                if (wasPlaying_DGB) {
                    playMusic();
                } else {
                    // Update UI but don't start
                    txtCurrentTime_DGB.setText(formatDuration(currentPos_DGB));
                    seekBar_DGB.setProgress(currentPos_DGB);
                    btnPlayPause_DGB.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        } else {
            initializeMediaPlayer();
            playMusic(); // Auto-play if fresh start
        }

        setupListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState_DGB) {
        super.onSaveInstanceState(outState_DGB);
        outState_DGB.putInt("POSITION", position_DGB);
        if (mediaPlayer_DGB != null) {
            outState_DGB.putInt("CURRENT_POS", mediaPlayer_DGB.getCurrentPosition());
            outState_DGB.putBoolean("IS_PLAYING", mediaPlayer_DGB.isPlaying());
        }
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
        if (songList_DGB != null && !songList_DGB.isEmpty()) {
            Song currentSong_DGB = songList_DGB.get(position_DGB);
            txtTitle_DGB.setText(currentSong_DGB.getTitle());
            txtArtist_DGB.setText(currentSong_DGB.getArtist());

            if (currentSong_DGB.getAlbumId() != -1) {
                Uri artworkUri_DGB = android.content.ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"), currentSong_DGB.getAlbumId());
                Glide.with(this)
                        .asBitmap()
                        .load(artworkUri_DGB)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imgAlbumArt_DGB);
            } else {
                imgAlbumArt_DGB.setImageResource(R.drawable.ic_launcher_background);
                new Thread(() -> {
                    android.media.MediaMetadataRetriever retriever_DGB = new android.media.MediaMetadataRetriever();
                    try {
                        retriever_DGB.setDataSource(this, Uri.parse(currentSong_DGB.getPath()));
                        byte[] art_DGB = retriever_DGB.getEmbeddedPicture();
                        if (art_DGB != null) {
                            android.graphics.Bitmap bitmap_DGB = android.graphics.BitmapFactory.decodeByteArray(art_DGB, 0, art_DGB.length);
                            runOnUiThread(() -> imgAlbumArt_DGB.setImageBitmap(bitmap_DGB));
                        }
                    } catch (Exception e_DGB) {
                        e_DGB.printStackTrace();
                    } finally {
                        try { retriever_DGB.release(); } catch (Exception e_DGB) { }
                    }
                }).start();
            }

            String path_DGB = currentSong_DGB.getPath();
            Uri uri_DGB;
            if (path_DGB.startsWith("content://")) {
                uri_DGB = Uri.parse(path_DGB);
            } else {
                if (path_DGB.startsWith("/") && !path_DGB.startsWith("file://")) {
                    path_DGB = "file://" + path_DGB;
                }
                uri_DGB = Uri.parse(path_DGB);
            }

            if (mediaPlayer_DGB != null) {
                mediaPlayer_DGB.reset();
                mediaPlayer_DGB.release();
                mediaPlayer_DGB = null;
            }

            try {
                mediaPlayer_DGB = MediaPlayer.create(this, uri_DGB);
                
                if (mediaPlayer_DGB != null) {
                    mediaPlayer_DGB.setOnCompletionListener(mp_DGB -> playNext());
                    txtTotalTime_DGB.setText(formatDuration(mediaPlayer_DGB.getDuration()));
                    seekBar_DGB.setMax(mediaPlayer_DGB.getDuration());
                    playMusic();
                } else {
                    Toast.makeText(this, "Error: Codec no soportado o archivo dañado.", Toast.LENGTH_LONG).show();
                    try {
                        mediaPlayer_DGB = new MediaPlayer();
                        mediaPlayer_DGB.setDataSource(this, uri_DGB);
                        mediaPlayer_DGB.prepare();
                        mediaPlayer_DGB.setOnCompletionListener(mp_DGB -> playNext());
                        txtTotalTime_DGB.setText(formatDuration(mediaPlayer_DGB.getDuration()));
                        seekBar_DGB.setMax(mediaPlayer_DGB.getDuration());
                        playMusic();
                    } catch (IOException io_DGB) {
                        Toast.makeText(this, "Error crítico al abrir archivo.", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e_DGB) {
                e_DGB.printStackTrace();
                Toast.makeText(this, "Excepción al reproducir: " + e_DGB.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
            handler_DGB.removeCallbacks(updateSeekBarAction_DGB);
        }
    }

    private void playNext() {
        if (songList_DGB == null || songList_DGB.isEmpty())
            return;
        position_DGB = (position_DGB + 1) % songList_DGB.size();
        initializeMediaPlayer();
        playMusic();
    }

    private void playPrev() {
        if (songList_DGB == null || songList_DGB.isEmpty())
            return;
        position_DGB = (position_DGB - 1 < 0) ? (songList_DGB.size() - 1) : (position_DGB - 1);
        initializeMediaPlayer();
        playMusic();
    }

    private void stopMusic() {
        if (mediaPlayer_DGB != null) {
            mediaPlayer_DGB.stop();
            try {
                mediaPlayer_DGB.prepare(); 
                mediaPlayer_DGB.seekTo(0);
            } catch (IOException e_DGB) {
                e_DGB.printStackTrace();
            } catch (IllegalStateException e_DGB) {
                e_DGB.printStackTrace();
            }
            btnPlayPause_DGB.setImageResource(android.R.drawable.ic_media_play);
            if (handler_DGB != null && updateSeekBarAction_DGB != null) {
                handler_DGB.removeCallbacks(updateSeekBarAction_DGB);
            }
            seekBar_DGB.setProgress(0);
            txtCurrentTime_DGB.setText(formatDuration(0));
        }
    }

    private void updateSeekBar() {
        updateSeekBarAction_DGB = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer_DGB != null) {
                    int currentPosition_DGB = mediaPlayer_DGB.getCurrentPosition();
                    seekBar_DGB.setProgress(currentPosition_DGB);
                    txtCurrentTime_DGB.setText(formatDuration(currentPosition_DGB));
                    handler_DGB.postDelayed(this, 1000);
                }
            }
        };
        handler_DGB.post(updateSeekBarAction_DGB);
    }

    private String formatDuration(long duration_DGB) {
        long minutes_DGB = TimeUnit.MILLISECONDS.toMinutes(duration_DGB);
        long seconds_DGB = TimeUnit.MILLISECONDS.toSeconds(duration_DGB) - TimeUnit.MINUTES.toSeconds(minutes_DGB);
        return String.format("%02d:%02d", minutes_DGB, seconds_DGB);
    }

    private void setupListeners() {
        btnPlayPause_DGB.setOnClickListener(v_DGB -> {
            if (mediaPlayer_DGB != null && mediaPlayer_DGB.isPlaying()) {
                pauseMusic();
            } else {
                playMusic();
            }
        });

        btnPrev_DGB.setOnClickListener(v_DGB -> playPrev());
        btnNext_DGB.setOnClickListener(v_DGB -> playNext());

        btnRewind_DGB.setOnClickListener(v_DGB -> {
            if (mediaPlayer_DGB != null) {
                int currentPosition_DGB = mediaPlayer_DGB.getCurrentPosition();
                int seekTo_DGB = Math.max(0, currentPosition_DGB - 10000); // Rewind 10s
                mediaPlayer_DGB.seekTo(seekTo_DGB);
            }
        });

        btnForward_DGB.setOnClickListener(v_DGB -> {
            if (mediaPlayer_DGB != null) {
                int currentPosition_DGB = mediaPlayer_DGB.getCurrentPosition();
                int duration_DGB = mediaPlayer_DGB.getDuration();
                int seekTo_DGB = Math.min(duration_DGB, currentPosition_DGB + 10000); // Forward 10s
                mediaPlayer_DGB.seekTo(seekTo_DGB);
            }
        });

        btnStop_DGB.setOnClickListener(v_DGB -> {
            stopMusic();
        });

        seekBar_DGB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar_DGB, int progress_DGB, boolean fromUser_DGB) {
                if (fromUser_DGB && mediaPlayer_DGB != null) {
                    mediaPlayer_DGB.seekTo(progress_DGB);
                    txtCurrentTime_DGB.setText(formatDuration(progress_DGB));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar_DGB) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar_DGB) {
            }
        });

        btnBack_DGB.setOnClickListener(v_DGB -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mediaPlayer_DGB != null && mediaPlayer_DGB.isPlaying()) {
            updateSeekBar();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler_DGB != null && updateSeekBarAction_DGB != null) {
            handler_DGB.removeCallbacks(updateSeekBarAction_DGB);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer_DGB != null) {
            mediaPlayer_DGB.stop();
            mediaPlayer_DGB.release();
            mediaPlayer_DGB = null;
        }
        handler_DGB.removeCallbacks(updateSeekBarAction_DGB);
    }
}
