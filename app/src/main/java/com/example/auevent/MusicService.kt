package com.example.auevent

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)

        if (mediaPlayer == null) {
            Log.e("MusicService", "MediaPlayer failed to create. Check the audio file path.")
        } else {
            mediaPlayer?.isLooping = true // Repeat music
            mediaPlayer?.start()
            Log.d("MusicService", "Music started successfully.")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // Keeps the service running even if the app is closed
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        Log.d("MusicService", "Music stopped.")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
