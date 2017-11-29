package com.wl.ireadbook.injection.component

import android.app.Application
import android.content.Context
import com.wl.ireadbook.injection.ApplicationContext
import com.wl.ireadbook.injection.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application
}
