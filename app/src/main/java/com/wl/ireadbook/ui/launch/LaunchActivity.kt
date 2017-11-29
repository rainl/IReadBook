package com.wl.ireadbook.ui.launch

import com.wl.ireadbook.BaseApplication
import com.wl.ireadbook.R
import com.wl.ireadbook.ui.base.BaseActivity
import com.wl.ireadbook.ui.main.MainActivity

/**
 * author：WangLei
 * date:2017/11/22.
 * QQ:619321796
 */
class LaunchActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.activity_launch
    }

    override fun initViews() {
        BaseApplication[this].mainHandler.postDelayed({ start() }, 2 * 1000)
    }

    private fun start() {
        startActivity(MainActivity.createIntent(this))
        finish()
    }
}