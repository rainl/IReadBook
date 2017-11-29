package com.wl.ireadbook.util

import android.content.Context
import android.widget.ImageView
import com.wl.ireadbook.R
import com.wl.ireadbook.util.glide.GlideImgHelper

/**
 * author：WangLei
 * date:2017/11/22.
 * QQ:619321796
 */
object IStringUtil {
    /**
     * 是否为空或空字符串
     */
    fun isNullOrEmpty(str: String?): Boolean {
        return null == str || str.trim { it <= ' ' }.isEmpty()
    }

    /**
     * 显示头像的图片
     *
     */
    fun displayAvatarImage(context: Context, path: String, imageView: ImageView) {
        if (!isNullOrEmpty(path)) {
            GlideImgHelper.loadAvatarImage(context, path, imageView)
        } else {
            imageView.setImageResource(R.drawable.contact_list_pic_big)
        }
    }

    /**
     * 显示通用的图片
     *
     */
    fun displayImage(context: Context, path: String?, imageView: ImageView) {
        if (!isNullOrEmpty(path)) {
            GlideImgHelper.loadImage(context, path!!, imageView)
        } else {
            imageView.setImageResource(R.drawable.default_image)
        }
    }
}