package com.example.smartdogbell

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import kotlinx.coroutines.*
import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.smartdogbell.databinding.ActivityButtonBinding

class ButtonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityButtonBinding
    private lateinit var notifyButton: Button
    private lateinit var settingsButton: Button
    private val NOTIFICATION_CHANNEL_ID = "DOG_BELL"
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationPlayer: MediaPlayer
    private lateinit var settings: Settings
    private val hideSettingsDelayMS: Long = 10_000

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notificationManager =
            getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

        supportActionBar?.hide()

        settings = Settings(this)

        notifyButton = binding.notifyButton
        notifyButton.setOnClickListener { notifyButtonClick() }
        notifyButton.text = settings.buttonText

        settingsButton = binding.settingsButton
        settingsButton.setOnClickListener { settingsButtonClick() }

        notificationPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_NOTIFICATION)
        }

        createNewNotificationChannel()
        enableFullscreen()
        GlobalScope.launch(Dispatchers.Main) {
            delay(hideSettingsDelayMS)
            settingsButton.visibility = View.INVISIBLE
        }
    }

    private fun settingsButtonClick() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun enableFullscreen() {
        if (Build.VERSION.SDK_INT >= 30) {
            binding.notifyButton.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            notifyButton.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    private fun notifyButtonClick() {
        if (!isNotifying()) {
            Toast.makeText(applicationContext, "Button Clicked", Toast.LENGTH_SHORT).show()
            createNewNotification("Dog Alert", "Azula needs to go outside!")
        }
    }

    private fun createNewNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Dog Bell",
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.lightColor = Color.BLUE
                channel.enableLights(true)
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    private fun createNewNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0, 500))

        notificationManager.notify(0, builder.build())
        playNotificationSound()
    }

    private fun playNotificationSound() {
        if (notificationPlayer.isPlaying) {
            return
        }

        val soundUri: Uri = Uri.parse(settings.soundFilePath)
        notificationPlayer.setDataSource(applicationContext, soundUri)
        notificationPlayer.prepare()
        notificationPlayer.start()
    }

    private fun isNotifying(): Boolean {
        return notificationPlayer.isPlaying
    }
}