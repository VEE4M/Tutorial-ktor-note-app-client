package com.androiddevs.ktornoteapp.other

/**
 *Veli-Matti Tikkanen, 22.6.2021
 */
object Constants {

    const val DATABASE_NAME = "notes_db"
    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"

    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val NO_EMAIL = "NO_EMAIL"
    const val NO_PASSWORD = "NO_PASSWORD"

    const val DEFAULT_NOTE_COLOR = "FFA500"

    const val ENCRYPTED_SHARED_PREF_NAME = "enc_shared_pref"
    const val BASE_URL = "http://10.0.2.2:8001"

    val IGNORE_AUTH_URLS = listOf("/register", "/login")

}