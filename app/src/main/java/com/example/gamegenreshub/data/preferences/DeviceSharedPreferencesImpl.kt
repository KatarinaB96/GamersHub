package com.example.gamegenreshub.data.preferences

import android.content.Context
import android.content.SharedPreferences

class DeviceSharedPreferencesImpl(context: Context) : DeviceSharedPreferences {

    companion object {

        private const val SHARED_PREFS_FILE_NAME = "device_preferences"
        private const val SIGNED_IN_AS_GUEST_KEY = "signed_in_as_guest"
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun setIsSignedInAsGuest(signedIn: Boolean) = sharedPreferences.edit().putBoolean(SIGNED_IN_AS_GUEST_KEY, signedIn).apply()

    override fun isSignedInAsGuest(): Boolean = sharedPreferences.getBoolean(SIGNED_IN_AS_GUEST_KEY, false)
}