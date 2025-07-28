package com.pratiksha.rojgarihub

import android.app.Application
import com.pratiksha.rojgarihub.di.diModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RojgariHubApp : Application() {
    val applicationScope: CoroutineScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RojgariHubApp)
            modules(diModule)
        }
    }
}