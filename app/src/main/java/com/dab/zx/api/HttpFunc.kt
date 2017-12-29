package com.dab.zx.api

import io.reactivex.functions.Function

/**
 * Created by 时空管理局 on 2016/8/4.
 */
internal class HttpFunc<T> : Function<HttpResult<T>, T> {
    override fun apply(t: HttpResult<T>): T {
        if (200 != t.code) {
            throw RuntimeException(t.code.toString() + t.reason)
        }
        @Suppress("UNCHECKED_CAST")
        return t.result as T
    }
}


