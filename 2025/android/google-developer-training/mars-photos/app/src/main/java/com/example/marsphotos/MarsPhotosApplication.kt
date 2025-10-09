package com.example.marsphotos

import android.app.Application
import android.util.Log
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer

class MarsPhotosApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        Log.d("MarsPhotosApplication", "onCreate")
        super.onCreate()
        container = DefaultAppContainer()
    }
}