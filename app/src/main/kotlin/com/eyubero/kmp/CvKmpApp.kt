package com.eyubero.kmp

import android.app.Application
import android.util.Log
import com.eyubero.kmp.di.initKoin
import com.eyubero.kmp.di.smokeMessage

class CvKmpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        Log.d("KMP init", smokeMessage())
    }
}
