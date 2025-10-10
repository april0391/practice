package com.example.androidplayground

import android.app.Application
import com.example.androidplayground.data.AppContainer
import com.example.androidplayground.data.DefaultAppContainer

class MainApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}