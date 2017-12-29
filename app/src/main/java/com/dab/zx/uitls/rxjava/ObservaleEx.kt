package com.dab.zx.uitls.rxjava

import io.reactivex.Observable

/**
 * Created by 八神火焰 on 2017/12/27.
 */
class ObservableEx(val observable: Observable<Int>, val message: String) {
    var isExecute: Boolean = false

    init {
        isExecute = false
    }
}