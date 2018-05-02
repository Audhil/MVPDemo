package com.audhil.medium.mvpdemo

import android.app.Application

/*
 * Created by mohammed-2284 on 01/05/18.
 */

class AppDelegate : Application() {

    companion object {
        lateinit var INSTANCE: AppDelegate
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}