package com.example.gamegenreshub.data.preferences

interface DeviceSharedPreferences {

    fun setIsSignedInAsGuest(signedIn: Boolean)

    fun isSignedInAsGuest() : Boolean
}