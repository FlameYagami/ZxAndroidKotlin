package com.zx.bean

/**
 * Created by 八神火焰 on 2017/1/14.
 */

class ResourceBean(val duelBean: DuelBean) {
    var isResourceVisible: Boolean = false

    init {
        this.isResourceVisible = true
    }
}
