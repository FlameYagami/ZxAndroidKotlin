package com.dab.zx.uitls.rxjava

import android.widget.ProgressBar
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by 八神火焰 on 2017/12/27.
 */
class ResourcesSubscriber(vararg observableExList: ObservableEx) {

    private var mDisposable: Disposable? = null
    private val mObservableList: List<ObservableEx>

    init {
        mObservableExecute = 0
        mObservableList = ArrayList()
        Collections.addAll(mObservableList, *observableExList)
    }

    fun apply(progressBar: ProgressBar, textView: TextView): Observable<ObservableEx> {
        return Observable.create { subscriber ->
            mDisposable = Observable.interval(100, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { aLong ->
                // 所有资源释放完毕
                if (mObservableList.size == mObservableExecute) {
                    subscriber.onComplete()
                    mDisposable!!.dispose()
                    mDisposable = null
                }

                // 执行资源文件任务
                val tempObservableEx = mObservableList[mObservableExecute]
                if (!tempObservableEx.isExecute) {
                    textView.text = tempObservableEx.message
                    tempObservableEx.isExecute = true
                    tempObservableEx.observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { progressBar.progress = it },
                            { error ->
                                subscriber.onError(error)
                                tempObservableEx.isExecute = false
                                mObservableExecute++
                            }
                    ) {
                        tempObservableEx.isExecute = false
                        mObservableExecute++
                    }
                }
            }
        }
    }

    companion object {
        @Volatile private var mObservableExecute: Int = 0
    }
}
