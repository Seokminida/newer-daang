package com.proj.newer_daang

import android.app.Application
import android.content.Context
var isDarkmodeOn : Boolean = false
class App : Application() {

    init{
        instance = this
    }

    companion object {
        lateinit var instance: App
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }

}