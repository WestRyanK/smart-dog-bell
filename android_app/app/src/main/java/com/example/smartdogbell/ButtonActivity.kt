package com.example.smartdogbell

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.smartdogbell.databinding.ActivityButtonBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class ButtonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityButtonBinding
    private lateinit var notifyButton: TextView
    private lateinit var fullscreenContentControls: LinearLayout
    private val hideHandler = Handler(Looper.myLooper()!!)
    private lateinit var ringtone: Ringtone
    private val NOTIFICATION_CHANNEL_ID = "DOG_BELL"
    private lateinit var notificationManager: NotificationManager

//    @SuppressLint("InlinedApi")
//    private val hidePart2Runnable = Runnable {
//        // Delayed removal of status and navigation bar
//        if (Build.VERSION.SDK_INT >= 30) {
//            fullscreenContent.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//        } else {
//            // Note that some of these constants are new as of API 16 (Jelly Bean)
//            // and API 19 (KitKat). It is safe to use them, as they are inlined
//            // at compile-time and do nothing on earlier devices.
//            fullscreenContent.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LOW_PROFILE or
//                        View.SYSTEM_UI_FLAG_FULLSCREEN or
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
//                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//        }
//    }
//    private val showPart2Runnable = Runnable {
//        // Delayed display of UI elements
//        supportActionBar?.show()
//        fullscreenContentControls.visibility = View.VISIBLE
//    }
//    private var isFullscreen: Boolean = false
//
//    private val hideRunnable = Runnable { hide() }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
//    private val delayHideTouchListener = View.OnTouchListener { view, motionEvent ->
//        when (motionEvent.action) {
//            MotionEvent.ACTION_DOWN -> if (AUTO_HIDE) {
//                delayedHide(AUTO_HIDE_DELAY_MILLIS)
//            }
//            MotionEvent.ACTION_UP -> view.performClick()
//            else -> {
//            }
//        }
//        false
//    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notificationManager =
            getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

        supportActionBar?.hide()
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        isFullscreen = true

        // Set up the user interaction to manually show or hide the system UI.
        notifyButton = binding.notifyButton
        notifyButton.setOnClickListener { notifyButtonClick() }
//        fullscreenContent.setOnClickListener { toggle() }

//        fullscreenContentControls = binding.fullscreenContentControls

        val ringtoneUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ringtone = RingtoneManager.getRingtone(applicationContext, ringtoneUri)
        createNewNotificationChannel()
//        enableFullscreen()
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
        Toast.makeText(applicationContext, "Button Clicked", Toast.LENGTH_SHORT).show()
        ringtone.play()
        createNewNotification("Dog Alert", "Azula needs to go outside!")
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
            .setVibrate(longArrayOf(100, 200, 100, 600, 100, 200, 100))

        notificationManager.notify(0, builder.build())
    }


//    private fun hide() {
//        // Hide UI first
//        supportActionBar?.hide()
//        fullscreenContentControls.visibility = View.GONE
//        isFullscreen = false
//
//        // Schedule a runnable to remove the status and navigation bar after a delay
////        hideHandler.removeCallbacks(showPart2Runnable)
////        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
//    }

//    private fun show() {
//        // Show the system bar
//        if (Build.VERSION.SDK_INT >= 30) {
//            fullscreenContent.windowInsetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//        } else {
//            fullscreenContent.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//        }
//        isFullscreen = true
//
//        // Schedule a runnable to display UI elements after a delay
////        hideHandler.removeCallbacks(hidePart2Runnable)
////        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY.toLong())
//    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
//    private fun delayedHide(delayMillis: Int) {
//        hideHandler.removeCallbacks(hideRunnable)
//        hideHandler.postDelayed(hideRunnable, delayMillis.toLong())
//    }

//    companion object {
//        /**
//         * Whether or not the system UI should be auto-hidden after
//         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
//         */
//        private const val AUTO_HIDE = true
//
//        /**
//         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
//         * user interaction before hiding the system UI.
//         */
//        private const val AUTO_HIDE_DELAY_MILLIS = 3000
//
//        /**
//         * Some older devices needs a small delay between UI widget updates
//         * and a change of the status and navigation bar.
//         */
//        private const val UI_ANIMATION_DELAY = 300
//    }
}