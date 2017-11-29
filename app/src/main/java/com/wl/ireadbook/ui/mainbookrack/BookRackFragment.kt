package com.wl.ireadbook.ui.mainbookrack

import android.support.v7.widget.GridLayoutManager
import com.wl.ireadbook.BaseApplication
import com.wl.ireadbook.R
import com.wl.ireadbook.data.BookRack
import com.wl.ireadbook.ui.base.BaseFragment
import com.wl.ireadbook.ui.readbook.ReadBookActivity
import kotlinx.android.synthetic.main.fragment_book_rack.*

/**
 * author：WangLei
 * date:2017/11/20.
 * QQ:619321796
 */

class BookRackFragment : BaseFragment() {

    private lateinit var adapter: BookRackAdapter

    override fun injection() {
        fragmentComponent()?.inject(this)
    }

    override fun initViews() {
        swipeLayout.setColorSchemeResources(R.color.colorPrimary)
        swipeLayout.setOnRefreshListener({
            adapter.data.clear()
            addData()
        })
        adapter = BookRackAdapter(R.layout.item_book_reck)
        list_rl.layoutManager = GridLayoutManager(context, 3)
        list_rl.adapter = adapter
        adapter.setOnItemChildClickListener { adapter, view, position ->
            startActivity(ReadBookActivity.createIntent(activity))
        }
        addData()
    }

    private fun addData() {
        resetRefreshing(true)
        BaseApplication[activity].mainHandler.postDelayed({
            val listData = ArrayList<BookRack>()
            listData.add(BookRack("飞剑问道", "https://qidian.qpic.cn/qdbimg/349573/1010468795/150"))
            listData.add(BookRack("圣墟", "https://qidian.qpic.cn/qdbimg/349573/1004608738/150"))
            listData.add(BookRack("龙皇武神", "https://qidian.qpic.cn/qdbimg/349573/2510964/150"))
            listData.add(BookRack("尘骨", "https://qidian.qpic.cn/qdbimg/349573/1010298084/150"))
            listData.add(BookRack("大王饶命", "https://qidian.qpic.cn/qdbimg/349573/1010191960/150"))
            listData.add(BookRack("帝霸", "https://qidian.qpic.cn/qdbimg/349573/3258971/150"))
            listData.add(BookRack("一念永恒", "https://qidian.qpic.cn/qdbimg/349573/1003354631/150"))
            adapter.setNewData(listData)
            resetRefreshing(false)
        }, 1000)
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_book_rack
    }

    companion object {
        val fragment: BookRackFragment
            get() = BookRackFragment()
    }

    private fun resetRefreshing(refreshing: Boolean) {
        if (refreshing) {
            if (!swipeLayout.isRefreshing) {
                swipeLayout.isRefreshing = true
            }
        } else {
            if (swipeLayout.isRefreshing) {
                swipeLayout.isRefreshing = false
            }
        }
    }
}
