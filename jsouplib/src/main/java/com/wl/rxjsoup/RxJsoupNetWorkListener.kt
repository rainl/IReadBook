package com.wl.rxjsoup

import org.jsoup.nodes.Document

interface RxJsoupNetWorkListener<T> {

    fun onNetWorkStart()

    fun onNetWorkError(e: Throwable)

    fun onNetWorkComplete()

    fun onNetWorkSuccess(t: T)

    fun getT(document: Document): T
}
