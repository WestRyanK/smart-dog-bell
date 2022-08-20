package com.example.smartdogbell

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class DogBellMessagingService : FirebaseMessagingService() {
    private val TAG = "DOG_BELL_SERVICE"

    init {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d(TAG, "Firebase Token: '$it'")
        }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d(TAG, "New Firebase Token: '$newToken'")
    }
}