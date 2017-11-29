package com.wl.ireadbook.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment
import com.wl.ireadbook.BaseApplication
import com.wl.ireadbook.injection.component.ConfigPersistentComponent
import com.wl.ireadbook.injection.component.DaggerConfigPersistentComponent
import com.wl.ireadbook.injection.component.FragmentComponent
import com.wl.ireadbook.injection.module.FragmentModule
import timber.log.Timber
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
abstract class BaseFragment : RxFragment() {

    private var mFragmentComponent: FragmentComponent? = null
    private var mFragmentId: Long = 0

    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent?
        if (!sComponentsMap.containsKey(mFragmentId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mFragmentId)
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(BaseApplication[context].mApplicationComponent)
                    .build()
            sComponentsMap.put(mFragmentId, configPersistentComponent)
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", mFragmentId)
            configPersistentComponent = sComponentsMap[mFragmentId]
        }
        if (configPersistentComponent != null) {
            mFragmentComponent = configPersistentComponent.fragmentComponent(FragmentModule(this))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putLong(KEY_FRAGMENT_ID, mFragmentId)
    }

    override fun onDestroy() {
        Timber.i("Clearing ConfigPersistentComponent id=%d", mFragmentId)
        sComponentsMap.remove(mFragmentId)
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater?.inflate(attachLayoutRes(), container, false)
        injection()
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    fun fragmentComponent(): FragmentComponent? {
        return mFragmentComponent
    }

    protected abstract fun attachLayoutRes(): Int
    protected abstract fun initViews()
    protected abstract fun injection()

    companion object {
        private val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
        private val NEXT_ID = AtomicLong(0)
        @SuppressLint("UseSparseArrays")
        private val sComponentsMap = HashMap<Long, ConfigPersistentComponent>()
    }

}
