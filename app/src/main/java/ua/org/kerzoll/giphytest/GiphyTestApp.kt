package ua.org.kerzoll.giphytest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GiphyTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}