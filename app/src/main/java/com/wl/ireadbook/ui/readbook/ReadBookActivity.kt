package com.wl.ireadbook.ui.readbook

import android.content.Context
import android.content.Intent
import com.wl.ireadbook.R
import com.wl.ireadbook.ui.base.BaseActivity

/**
 * author：WangLei
 * date:2017/11/22.
 * QQ:619321796
 * 看书界面
 */
class ReadBookActivity : BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ReadBookActivity::class.java)
        }
    }

    override fun attachLayoutRes(): Int {
       return R.layout.activity_read_book
    }

    override fun initViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}