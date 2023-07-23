package com.example.smartdogbell

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.smartdogbell.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var changeSoundButton: Button
    private lateinit var playSoundButton: Button
    private lateinit var buttonTextEditText: EditText
    private lateinit var foregroundColorEditText: EditText
    private lateinit var backgroundColorEditText: EditText
    private lateinit var foregroundColorImageView: ImageView
    private lateinit var backgroundColorImageView: ImageView
    private lateinit var settings: Settings
    private lateinit var player: SoundPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settings = Settings(this)

        changeSoundButton = binding.changeSoundButton
        playSoundButton = binding.playSoundButton
        buttonTextEditText = binding.buttonTextEditText
        foregroundColorEditText = binding.foregroundColorEditText
        backgroundColorEditText = binding.backgroundColorEditText
        foregroundColorImageView = binding.foregroundColorImageView
        backgroundColorImageView = binding.backgroundColorImageView

        changeSoundButton.setOnClickListener { changeSoundButtonClick() }
        playSoundButton.setOnClickListener { player.playSound() }

        buttonTextEditText.setText(settings.buttonText)
        buttonTextEditText.addTextChangedListener { buttonTextEditTextChanged() }

        loadColor(foregroundColorEditText, foregroundColorImageView, settings.foregroundColor)
        foregroundColorEditText.addTextChangedListener {
            extractColor(foregroundColorEditText)?.let {
                settings.foregroundColor = it
            }
        }
        foregroundColorEditText.setOnFocusChangeListener { view, b ->
            loadColor(
                foregroundColorEditText,
                foregroundColorImageView,
                settings.foregroundColor
            )
        }

        loadColor(backgroundColorEditText, backgroundColorImageView, settings.backgroundColor)
        backgroundColorEditText.addTextChangedListener {
            extractColor(backgroundColorEditText)?.let {
                settings.backgroundColor = it
            }
        }
        backgroundColorEditText.setOnFocusChangeListener { view, b ->
            loadColor(
                backgroundColorEditText,
                backgroundColorImageView,
                settings.backgroundColor
            )
        }
    }

    override fun onResume() {
        super.onResume()
        player = SoundPlayer(this, settings)
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

    private fun loadColor(editText: EditText, imageView: ImageView, color: Int) {
        val rgbHex = toRgbHex(color)
        val poundRgbHex = "#$rgbHex"
        editText.setText(poundRgbHex)
        imageView.setBackgroundColor(color)
    }

    private fun toRgbHex(color: Int): String {
        var hex = color.toUInt().toString(16)
        val rgbHex = toRgbHex(hex)
        return rgbHex
    }

    private fun toRgbHex(hex: String): String {
        val poundlessHex = if (hex.length > 0 && hex[0] == '#') {
            hex.substring(1)
        }
        else {
            hex
        }
        val substringStart = poundlessHex.length - 6
        val noAlphaHex = if (substringStart > 0) {
            poundlessHex.substring(substringStart)
        }
        else {
            poundlessHex
        }
        val rgbHex = noAlphaHex + "0".repeat(6 - noAlphaHex.length)
        return rgbHex
    }

    private fun extractColor(editText: EditText): Int? {
        return try {
            val rgbHex = toRgbHex(editText.text.toString())
            val argbHex = "FF$rgbHex"
            val longHex: Long = java.lang.Long.parseLong(argbHex, 16)
            longHex.toInt()
        } catch (ex: Exception) {
            null
        }
    }

    private fun buttonTextEditTextChanged() {
        settings.buttonText = buttonTextEditText.text.toString()
    }

    private fun changeSoundButtonClick() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/mpeg"
        }
        resultLauncher.launch(intent)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.also { uri ->
                    this@SettingsActivity.settings.soundFilePath = uri.toString()
                    Toast.makeText(applicationContext, uri.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
}