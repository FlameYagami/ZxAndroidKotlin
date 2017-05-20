package com.zx.uitls

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Created by 八神火焰 on 2016/11/5.
 */
class RxBus private constructor() {
    private val mSubject: SerializedSubject<Any, Any> = SerializedSubject(PublishSubject.create<Any>())
    private var mSubscriptionMap: HashMap<String, CompositeSubscription>? = null

    /**
     * 发送事件
     */
    fun post(`object`: Any) {
        mSubject.onNext(`object`)
    }

    /**
     * 返回指定类型的Observable实例
     */
    fun <T> toObservable(type: Class<T>): Observable<T> {
        return mSubject.ofType(type)
    }

    /**
     * 是否已有观察者订阅
     */
    fun hasObservers(): Boolean {
        return mSubject.hasObservers()
    }

    /**
     * 一个默认的订阅方法
     */
    fun <T> doSubscribe(type: Class<T>, next: Action1<T>, error: Action1<Throwable>): Subscription {
        return toObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error)
    }

    /**
     * 保存订阅后的subscription
     */
    fun addSubscription(`object`: Any, subscription: Subscription) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = HashMap<String, CompositeSubscription>()
        }
        val key = `object`.javaClass.name
        if (mSubscriptionMap!![key] != null) {
            mSubscriptionMap!![key]?.add(subscription)
        } else {
            val compositeSubscription = CompositeSubscription()
            compositeSubscription.add(subscription)
            mSubscriptionMap?.put(key, compositeSubscription)
        }
    }

    /**
     * 取消订阅
     */
    fun unSubscribe(`object`: Any) {
        if (mSubscriptionMap == null) {
            return
        }
        val key = `object`.javaClass.name
        if (!mSubscriptionMap?.containsKey(key)!!) {
            return
        }
        if (mSubscriptionMap!![key] != null) {
            mSubscriptionMap!![key]?.unsubscribe()
        }
        mSubscriptionMap?.remove(key)
    }

    companion object {
        @Volatile private var mInstance: RxBus? = null

        val instance: RxBus
            get() {
                if (mInstance == null) {
                    synchronized(RxBus::class.java) {
                        if (mInstance == null) {
                            mInstance = RxBus()
                        }
                    }
                }
                return mInstance!!
            }
    }

}
