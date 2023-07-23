package com.example.smartdogbell

import android.app.Activity
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.smartdogbell.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var changeSoundButton: Button
    private lateinit var playSoundButton: Button
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeSoundButton = binding.changeSoundButton
        playSoundButton = binding.playSoundButton

        changeSoundButton.setOnClickListener { changeSoundButtonClick() }
        playSoundButton.setOnClickListener { playSoundButtonClick() }

        settings = Settings(this)
    }

    private fun playSoundButtonClick() {
        if (settings.soundFilePath != "") {
            val soundUri: Uri = Uri.parse(settings.soundFilePath)
            val mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_NOTIFICATION)
                setDataSource(applicationContext, soundUri)
                prepare()
                start()
                setOnCompletionListener {
                    release()
                }
            }
        }
        else {
            Toast.makeText(applicationContext, "No sound file selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeSoundButtonClick() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/mpeg"
        }
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.also { uri ->
                this@SettingsActivity.settings.soundFilePath = uri.toString()
                Toast.makeText(applicationContext, uri.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}