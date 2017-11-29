package com.wl.ireadbook

import android.app.Application
import android.content.Context
import android.os.Handler
import com.wl.ireadbook.injection.component.ApplicationComponent
import com.wl.ireadbook.injection.component.DaggerApplicationComponent
import com.wl.ireadbook.injection.module.ApplicationModule
import timber.log.Timber

/**
 * author：WangLei
 * date:2017/11/17.
 * QQ:619321796
 */

class BaseApplication : Application() {
    lateinit var mApplicationComponent: ApplicationComponent
    lateinit var mainHandler: Handler

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
        }
        mApplicationComponent= DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        mainHandler = Handler(mainLooper)
    }

    companion object {
        operator fun get(context: Context): BaseApplication {
            return context.applicationContext as BaseApplication
        }
    }
}
