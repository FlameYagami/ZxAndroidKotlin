package com.zx.bean

/**
 * Created by 八神火焰 on 2017/1/13.
 */

class HandBean(val duelBean: DuelBean) {
    var isTopVisible: Boolean = false
    var isBottomVisible: Boolean = false

    init {
        this.isTopVisible = true
        this.isBottomVisible = false
    }
}
