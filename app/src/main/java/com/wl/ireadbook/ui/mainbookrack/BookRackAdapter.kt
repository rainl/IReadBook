package com.wl.ireadbook.ui.mainbookrack

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wl.ireadbook.R
import com.wl.ireadbook.data.BookRack
import com.wl.ireadbook.util.IStringUtil

/**
 * author：WangLei
 * date:2017/11/21.
 * QQ:619321796
 */
class BookRackAdapter(layoutResId: Int) : BaseQuickAdapter<BookRack, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: BookRack) {
        if (helper != null) {
            helper.setText(R.id.bookNameTv, item.bookName)
            IStringUtil.displayImage(mContext, item.bookPath, (helper.getView<ImageView>(R.id.bookImageTv) as ImageView))
        }
    }
}