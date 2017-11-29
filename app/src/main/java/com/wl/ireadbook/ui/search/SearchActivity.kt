package com.wl.ireadbook.ui.search

import android.content.Context
import android.content.Intent
import com.wl.ireadbook.R
import com.wl.ireadbook.ui.base.BaseActivity

/**
 * author：WangLei
 * date:2017/11/21.
 * QQ:619321796
 */
class SearchActivity : BaseActivity() {
    companion object {
        fun  createIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_search
    }

    override fun initViews() {
        setTitle(R.string.menu_search)
    }

}