package com.wl.ireadbook.ui.base

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.wl.ireadbook.BaseApplication
import com.wl.ireadbook.injection.component.ActivityComponent
import com.wl.ireadbook.injection.component.ConfigPersistentComponent
import com.wl.ireadbook.injection.component.DaggerConfigPersistentComponent
import com.wl.ireadbook.injection.module.ActivityModule
import timber.log.Timber
import java.util.*
import java.util.concurrent.atomic.AtomicLong


/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
abstract class BaseActivity : RxAppCompatActivity() {

    private var mActivityComponent: ActivityComponent? = null
    private var mActivityId: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        addBackEvent()
        mActivityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent?
        if (!sComponentsMap.containsKey(mActivityId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId)
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(BaseApplication[this].mApplicationComponent)
                    .build()
            sComponentsMap.put(mActivityId, configPersistentComponent)
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId)
            configPersistentComponent = sComponentsMap[mActivityId]
        }
        if (configPersistentComponent != null) {
            mActivityComponent = configPersistentComponent.activityComponent(ActivityModule(this))
        }
        initViews()
    }

    /**
     * 标题栏添加返回键和去除ActionBar底部的阴影部分
     */
    protected fun addBackEvent() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            setActionBarElevation()
        }
    }

    protected abstract fun attachLayoutRes(): Int
    protected abstract fun initViews()

    /**
     * 去除ActionBar底部的阴影部分
     */
    protected fun setActionBarElevation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.elevation = 0f
            }
        }
    }

    /**
     * 点击返回按钮额外处理
     */
    protected fun onBackEvent(): Boolean {
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putLong(KEY_ACTIVITY_ID, mActivityId)
    }

    override fun onDestroy() {
        //        activityList.remove(this);
        if (!isChangingConfigurations) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId)
            sComponentsMap.remove(mActivityId)
        }
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (onBackEvent()) {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun activityComponent(): ActivityComponent? {
        return mActivityComponent
    }

    companion object {
        private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        private val NEXT_ID = AtomicLong(0)
        private val sComponentsMap = HashMap<Long, ConfigPersistentComponent>()
    }

}
