package com.example.smartdogbell

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color

class Settings(context: Context) {

    val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences("SmartDogBell_Preferences", MODE_PRIVATE)
    }

    private val buttonTextKey: String = "ButtonText"
    var buttonText: String
        get() = preferences.getString(buttonTextKey, null) ?: "Boop to Poop"
        set(value) = putString(buttonTextKey, value)

    private val soundFilePathKey: String = "SoundFilePath"
    var soundFilePath: String
        get() = preferences.getString(soundFilePathKey, null) ?: ""
        set(value) = putString(soundFilePathKey, value)

    private val backgroundColorKey: String = "BackgroundColor"
    var backgroundColor: Int
        get() = preferences.getInt(backgroundColorKey, Color.WHITE)
        set(value) = putInt(backgroundColorKey, value)

    private val foregroundColorKey: String = "ForegroundColor"
    var foregroundColor: Int
        get() = preferences.getInt(foregroundColorKey, Color.BLUE)
        set(value) = putInt(foregroundColorKey, value)

    private fun putInt(key: String, value: Int) {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun putString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}