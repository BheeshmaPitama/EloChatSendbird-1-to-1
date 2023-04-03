package com.anubhav.elochatsendbird.onetoone.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.anubhav.elochatsendbird.onetoone.BuildConfig
import com.sendbird.android.SendbirdChat
import com.sendbird.android.exception.SendbirdException
import com.sendbird.android.handler.InitResultHandler
import com.sendbird.android.params.InitParams

private const val TAG = "SendbirdInitializer"

class SendbirdInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val initParams = InitParams(
            appId = BuildConfig.SENDBIRD_APP_ID,
            context = context.applicationContext,
            useCaching = true
        )

        val initResultHandler = object : InitResultHandler {
            override fun onInitFailed(e: SendbirdException) {
                Log.v(TAG, "Initialization failed. SDK will still operate properly as if useLocalCaching is set to false.")
            }

            override fun onInitSucceed() {
                Log.v(TAG, "Initialization is completed.")
            }

            override fun onMigrationStarted() {
                Log.v(TAG, "There's an update on Sendbird server.")
            }
        }

        return SendbirdChat.init(initParams,initResultHandler)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}