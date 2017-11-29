package com.wl.ireadbook.ui.main

import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.wl.ireadbook.R
import com.wl.ireadbook.adapter.TabFragmentAdapter
import com.wl.ireadbook.ui.base.BaseActivity
import com.wl.ireadbook.ui.mainbookrack.BookRackFragment
import com.wl.ireadbook.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        setSupportActionBar(toolbar)
        initTab()
    }

    private fun initTab() {
        val tabList = ArrayList<String>()
        tabList.add("书架")
        tabList.add("发现")
//        tabList.add("女生")
        main_tablayout.tabMode = TabLayout.MODE_FIXED

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(BookRackFragment.fragment)
        fragmentList.add(BookRackFragment.fragment)
//        fragmentList.add(BookRackFragment.fragment)

        val fragmentAdapter = TabFragmentAdapter(supportFragmentManager, fragmentList, tabList)
        main_viewpager.adapter = fragmentAdapter
        main_tablayout.setupWithViewPager(main_viewpager)
        main_viewpager.offscreenPageLimit = tabList.size
        main_viewpager.currentItem = 1
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_search) {
            startActivity(SearchActivity.createIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }
}
