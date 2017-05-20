package com.zx.game

/**
 * Created by 八神火焰 on 2017/2/21.
 */

class Player {
    var isReady: Boolean = false
    var name: String? = null
        private set
    var type: Byte = 0

    constructor(name: String) {
        this.name = name
    }

    constructor(type: Byte, name: String) {
        this.name = name
        this.type = type
        this.isReady = false
    }

    constructor(type: Byte, name: String, isReady: Boolean) {
        this.name = name
        this.type = type
        this.isReady = isReady
    }
}
