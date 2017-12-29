package com.dab.zx.uitls.rxjava

import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import com.dab.zx.uc.dialog.DialogLoadingUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by 八神火焰 on 2017/12/27.
 */
/**
 * Created by 八神火焰 on 2017/6/6.
 * 描述：Http请求等待提示框封装类
 * 功能：网络请求时自动显示及隐藏等待提示框,同时支持返回键取消网络请求
 */

class ProgressSubscriber<T> {
    private var mContext: Context? = null
    private var mObservable: Observable<T>? = null
    private var mDisposable: Disposable? = null
    private var mMessage: String = ""

    /**
     * 返回键的监听
     */
    private val KeyListener = DialogInterface.OnKeyListener { dialog, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DialogLoadingUtils.hide()
            mDisposable!!.dispose()
        }
        false
    }

    constructor(mContext: Context, mObservable: Observable<T>) {
        this.mContext = mContext
        this.mObservable = mObservable
    }

    constructor(mContext: Context, mObservable: Observable<T>, mMessage: String) {
        this.mContext = mContext
        this.mObservable = mObservable
        this.mMessage = mMessage
    }

    /**
     * 构造函数,等待提示框能否取消收参数影响
     *
     * @param cancelable 等待提示框是否可取消
     * @return 被观测对象
     */
    @JvmOverloads
    fun apply(cancelable: Boolean = false): Observable<T> {
        return Observable.create { subscriber ->
            mObservable!!.subscribe(object : Observer<T> {
                override fun onSubscribe(disposable: Disposable) {
                    mDisposable = disposable
                    DialogLoadingUtils.show(mContext, mMessage, if (cancelable) KeyListener else null)
                }

                override fun onNext(t: T) {
                    subscriber.onNext(t)
                    onComplete()
                }

                override fun onError(e: Throwable) {
                    subscriber.onError(Throwable(ThrowableTransform.apply(e)))
                    onComplete()
                }

                override fun onComplete() {
                    DialogLoadingUtils.hide()
                }
            })
        }
    }
}