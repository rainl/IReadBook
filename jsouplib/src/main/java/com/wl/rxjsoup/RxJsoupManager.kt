package com.wl.rxjsoup

import android.support.annotation.IntDef
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

/**
 * author：WangLei
 * date:2017/11/24.
 * QQ:619321796
 */
class RxJsoupManager {
    val TAG = "RxJsoupManager"
    lateinit var arrayMap: HashMap<Any, Disposable>

    companion object {
        const val GET = 1L
        const val POST = 2L
    }

    @IntDef(GET, POST)
    @kotlin.annotation.Retention
    internal annotation class Method

    private var jsoupHeader = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"

    private var connection: Connection? = null

    private fun Manager() {
        arrayMap = HashMap()
    }

    fun <T> getApi(url: String, listener: RxJsoupNetWorkListener<T>): DisposableObserver<T> {
        return getApi<T>(TAG, url, GET, listener)
    }

    fun <T> getApi(tag: Any, url: String, listener: RxJsoupNetWorkListener<T>): DisposableObserver<T> {
        return getApi<T>(tag, url, GET, listener)
    }

    fun <T> postApi(url: String, listener: RxJsoupNetWorkListener<T>): DisposableObserver<T> {
        return getApi<T>(TAG, url, POST, listener)
    }

    fun <T> postApi(tag: Any, url: String, listener: RxJsoupNetWorkListener<T>): DisposableObserver<T> {
        return getApi<T>(tag, url, POST, listener)
    }

    @Throws(IOException::class)
    fun getT(url: String): Document {
        return getT(GET, url)
    }

    @Throws(IOException::class)
    fun getT(@Method type: Long, url: String): Document {
        when (type) {
            POST -> return getInstance().postDocument(url)
            GET -> return getInstance().getDocument(url)
        }
        return getInstance().getDocument(url)
    }

    fun <T> getApi(observable: Observable<T>, listener: DisposableObserver<T>): DisposableObserver<T> {
        return getApi<T>(TAG, observable, listener)
    }

    fun <T> getApi(tag: Any, observable: Observable<T>, listener: DisposableObserver<T>?): DisposableObserver<T> {
        if (listener == null) {
            throw NullPointerException("listener is null")
        }
        val disposableObserver = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(listener!!)
        arrayMap.put(tag, disposableObserver)
        return disposableObserver
    }


    fun <T> getApi(tag: Any, url: String, @Method type: Long, listener: RxJsoupNetWorkListener<T>?): DisposableObserver<T> {
        if (listener == null) {
            throw NullPointerException("listener is null")
        }
        listener.onNetWorkStart()
        val disposableObserver = Observable
                .create(ObservableOnSubscribe<T> { e ->
                            var document: Document? = null
                            when (type) {
                                GET -> document = getDocument(url)
                                POST -> document = postDocument(url)
                            }
                            e.onNext(listener.getT(document!!))
                            e.onComplete()
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<T>() {
                    override fun onNext(t: T) {
                        listener.onNetWorkSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onNetWorkError(e)
                    }

                    override fun onComplete() {
                        listener.onNetWorkComplete()
                    }
                })
        arrayMap.put(tag, disposableObserver)
        return disposableObserver
    }

    fun cancel(tag: Any) {
        val disposable = arrayMap[tag]
        if (disposable != null) {
            if (!RxUtils.isEmpty(disposable) && !disposable.isDisposed) {
                disposable.dispose()
                arrayMap.remove(tag)
            }
        }
    }

    fun cancelAll() {
        for (disposableEntry in arrayMap.entries) {
            cancel(disposableEntry.key)
        }
    }

    fun setConnection(connection: Connection): RxJsoupManager {
        this.connection = connection
        return this
    }

    fun setUserAgent(userAgent: String): RxJsoupManager {
        this.jsoupHeader = userAgent
        return this
    }

    fun getInstance(): RxJsoupManager {
        return RxJsoupManagerHolder.rxJsoupManager
    }

    @Throws(IOException::class)
    private fun getDocument(url: String): Document {
        return if (connection != null) {
            connection!!.get()
        } else connect(url).get()
    }


    @Throws(IOException::class)
    private fun postDocument(url: String): Document {
        return if (connection != null) {
            connection!!.post()
        } else connect(url).post()
    }

    private fun connect(url: String): Connection {
        return Jsoup.connect(url.trim { it <= ' ' }).header("User-Agent", jsoupHeader)
    }

    private object RxJsoupManagerHolder {
        val rxJsoupManager = RxJsoupManager()
    }
}