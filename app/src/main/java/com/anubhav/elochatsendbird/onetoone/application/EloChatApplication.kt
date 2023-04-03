package com.anubhav.elochatsendbird.onetoone.application

import android.app.Application
import androidx.startup.AppInitializer
import com.anubhav.elochatsendbird.onetoone.initializer.SendbirdInitializer

class EloChatApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        AppInitializer.getInstance(this).run {
            initializeComponent(SendbirdInitializer::class.java)
        }
    }
}