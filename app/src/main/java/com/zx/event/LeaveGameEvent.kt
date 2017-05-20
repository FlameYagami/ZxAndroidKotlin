package com.zx.event

import com.zx.game.Player

/**
 * Created by 八神火焰 on 2017/2/20.
 */

class LeaveGameEvent(player: Player, ownerType: Byte) {
    var player: Player
        internal set
    var ownerType: Byte = 0
        internal set

    init {
        this.player = player
        this.ownerType = ownerType
    }
}
