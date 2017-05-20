package com.zx.api

import android.util.Log
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.zx.bean.UpdateBean
import com.zx.uitls.FileUtils
import com.zx.uitls.JsonUtils
import com.zx.uitls.PathManager.downloadPath
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by sll on 2016/3/10.
 */
class RequestApi {

    private var requestService: RequestService? = null

    private constructor() {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
        builder.addInterceptor(LogInterceptor())
        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        requestService = retrofit.create<RequestService>(RequestService::class.java)
    }

    constructor(url: String) {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
        builder.addInterceptor(LogInterceptor())
        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build()
        requestService = retrofit.create<RequestService>(RequestService::class.java)
    }

    /**
     * OKHttp截断器
     */
    private inner class LogInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            Log.d(TAG, "request:" + request.toString())
            //            long             t1       = System.nanoTime();
            val response = chain.proceed(chain.request())
            //            long             t2       = System.nanoTime();
            //            Log.d(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            val mediaType = response.body().contentType()
            val content = response.body().string()
            Log.d(TAG, "response body:" + content)
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build()
        }
    }

    companion object {
        private val TAG = RequestApi::class.java.simpleName

        private val BASE_URL = "https://github.com/FlameYagami/ZxAndroid/"

        private val DEFAULT_TIMEOUT = 5000// 默认超时时间5秒

        @Volatile private var mInstance: RequestApi? = null

        /**
         * 获取单例模式

         * @return RequestApi实例
         */
        fun getInstance(): RequestApi? {
            if (mInstance == null) {
                synchronized(RequestApi::class.java) {
                    if (mInstance == null) {
                        mInstance = RequestApi()
                    }
                }
            }
            return mInstance
        }

        /**
         * 获取更新信息接口

         * @return 报警信息列表
         */
        fun checkUpdate(): Observable<UpdateBean> {
            return Observable.create<UpdateBean> { subscriber ->
                FileDownloader.getImpl().create(BASE_URL + "releases/download/UpdateJson/UpdateJson.txt")
                        .setPath(downloadPath + "UpdateJson")
                        .setListener(object : FileDownloadListener() {
                            override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

                            override fun connected(task: BaseDownloadTask?, etag: String?, isContinue: Boolean, soFarBytes: Int, totalBytes: Int) {}

                            override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

                            override fun blockComplete(task: BaseDownloadTask?) {}

                            override fun retry(task: BaseDownloadTask?, ex: Throwable?, retryingTimes: Int, soFarBytes: Int) {}

                            override fun completed(task: BaseDownloadTask) {
                                val updateJson = FileUtils.getFileContent(downloadPath + "UpdateJson")
                                val updateBean = JsonUtils.deserializer<UpdateBean>(updateJson, UpdateBean::class.java)
                                subscriber.onNext(updateBean)
                            }

                            override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

                            override fun error(task: BaseDownloadTask, e: Throwable) {
                                subscriber.onError(e)
                            }

                            override fun warn(task: BaseDownloadTask) {}
                        }).start()
            }
        }
    }
}
