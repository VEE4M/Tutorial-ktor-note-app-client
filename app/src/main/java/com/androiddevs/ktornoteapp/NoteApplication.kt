package com.androiddevs.ktornoteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 *Veli-Matti Tikkanen, 23.6.2021
 */
@HiltAndroidApp
class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}

