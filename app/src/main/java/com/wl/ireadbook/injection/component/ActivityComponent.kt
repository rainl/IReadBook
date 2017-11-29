package com.wl.ireadbook.injection.component


import com.wl.ireadbook.injection.PerActivity
import com.wl.ireadbook.injection.module.ActivityModule
import com.wl.ireadbook.ui.main.MainActivity
import dagger.Subcomponent

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}
