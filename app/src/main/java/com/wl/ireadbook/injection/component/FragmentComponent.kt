package com.wl.ireadbook.injection.component


import com.wl.ireadbook.injection.PerFragment
import com.wl.ireadbook.injection.module.FragmentModule
import com.wl.ireadbook.ui.mainbookrack.BookRackFragment
import dagger.Subcomponent

/**
 * This component inject dependencies to all Activities across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(rackFragment: BookRackFragment)
}
