package com.example.seontestapplication

import android.app.Application
import com.example.seonsdk.main.PhotoSDK

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize PhotoSDK with the application context
        PhotoSDK.initialize(this)
    }
}