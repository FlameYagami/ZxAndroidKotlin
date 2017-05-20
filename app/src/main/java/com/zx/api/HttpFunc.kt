package com.zx.api

import rx.functions.Func1

/**
 * Created by 时空管理局 on 2016/8/4.
 */
internal class HttpFunc<T> : Func1<HttpResult<T>, T> {
    override fun call(httpResult: HttpResult<T>): T? {
        if (200 != httpResult.code) {
            throw RuntimeException(httpResult.code.toString() + httpResult.reason)
        }
        return httpResult.result
    }
}


