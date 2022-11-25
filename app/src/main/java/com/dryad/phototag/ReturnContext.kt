package com.dryad.phototag

import android.app.Application
import android.content.Context

public class ReturnContext : Application() {

    private var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        this.context = applicationContext
    }

    fun getAppContext(): Context? {
        return this.context
    }
}