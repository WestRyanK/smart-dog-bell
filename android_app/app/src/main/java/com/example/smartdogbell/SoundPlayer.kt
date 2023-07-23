package com.example.smartdogbell

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast

class SoundPlayer(val context: Context, val settings: Settings) {
    private var player: MediaPlayer

    init {
        player = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_NOTIFICATION)
        }
    }

    fun playSound() {
        if (player.isPlaying) {
            return
        }

        try {
            val soundUri: Uri = Uri.parse(settings.soundFilePath)
            player.stop()
            player.reset()
            player.setDataSource(context, soundUri)
            player.prepare()
            player.start()
        }
        catch (ex: Exception) {
            Toast.makeText(context, "No Sound File Set!", Toast.LENGTH_SHORT).show()
        }
    }

    fun release() {
        player.release()
    }

    val isPlaying: Boolean
        get() = player.isPlaying
}