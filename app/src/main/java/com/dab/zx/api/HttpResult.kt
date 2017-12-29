package com.dab.zx.api

/**
 * Created by 时空管理局 on 2016/8/4.
 */
internal class HttpResult<T> {
    var code: Int = 0
    var reason: String? = null
    var result: T? = null
}
