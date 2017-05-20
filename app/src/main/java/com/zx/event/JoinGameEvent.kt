package com.zx.event

/**
 * Created by 八神火焰 on 2017/2/22.
 */

class JoinGameEvent {
    var playerType: Byte = 0
        private set
    var isSucceed: Boolean = false
        private set

    constructor(playerType: Byte) {
        this.playerType = playerType
        this.isSucceed = true
    }

    constructor(playerType: Byte, succeed: Boolean) {
        this.playerType = playerType
        this.isSucceed = succeed
    }
}
