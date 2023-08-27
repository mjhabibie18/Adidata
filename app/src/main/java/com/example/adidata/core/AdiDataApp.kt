package com.example.adidata.core

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.adidata.di.networkModule
import com.example.adidata.di.repositoryModule
import com.example.adidata.di.useCaseModule
import com.example.adidata.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class AdiDataApp : Application() {

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        mInstance = this

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AdiDataApp)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

    fun getInstance(): AdiDataApp? {
        if (mInstance == null) {
            synchronized(AdiDataApp::class.java) { mInstance = AdiDataApp() }
        }
        return mInstance
    }

    companion object {

        private var mInstance: AdiDataApp? = null

        fun getContext(): Context? {
            return mInstance?.applicationContext
        }
    }
}