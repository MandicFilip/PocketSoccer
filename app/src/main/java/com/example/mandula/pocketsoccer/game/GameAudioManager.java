package com.example.mandula.pocketsoccer.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.example.mandula.pocketsoccer.R;

public class GameAudioManager implements AudioManager.OnAudioFocusChangeListener {
    private boolean goalSoundPlaying = false;
    private boolean audioEnabled = false;

    private AudioManager audioManager;
    private AudioFocusRequest focusRequest;

    private static GameAudioManager gameAudioManager;
    private static Context context;

    public static GameAudioManager getInstance() {
        return gameAudioManager;
    }

    public GameAudioManager(Context context, AudioManager audioManager) {
        this.audioManager = audioManager;
        GameAudioManager.gameAudioManager = this;
        GameAudioManager.context = context;
    }

    public void playDiskAndBallCollisionSound() {
        if (audioEnabled && !goalSoundPlaying) {
            playSound(R.raw.ball_kick, 500, 0);
        }
    }

    public void playGoalScoredSound() {
        if (audioEnabled) {
            goalSoundPlaying = true;
            playSound(R.raw.score, 2000, 1);
        }
    }

    public void enableAudio() {
        audioEnabled = true;
    }

    public void disableAudio() {
        audioEnabled = false;
    }

    private void playSound(final int fileID, final int milis, final int type) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                        .setAudioAttributes(audioAttributes)
                        .setOnAudioFocusChangeListener(gameAudioManager)
                        .build();

                int isObtained = audioManager.requestAudioFocus(focusRequest);
                MediaPlayer mediaPlayer;
                if (isObtained == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //kod za media player
                    mediaPlayer = MediaPlayer.create(context, fileID);
                    mediaPlayer.start();
                } else {
                    return;
                }
                try {
                    Thread.sleep(milis);
                } catch (InterruptedException e) {
                    return;
                }
                mediaPlayer.stop();
                mediaPlayer.release();

                if (audioManager != null) audioManager.abandonAudioFocusRequest(focusRequest);
                if (type == 1) {
                    goalSoundPlaying = false;
                }
            }
        })).start();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (audioManager != null) audioManager.abandonAudioFocusRequest(focusRequest);
        disableAudio();
    }
}
