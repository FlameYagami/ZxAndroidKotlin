package com.zx.event

import com.zx.game.Player

/**
 * Created by 八神火焰 on 2017/2/24.
 */

class EnterGameEvent(player: Player) {
    var player: Player
        internal set

    init {
        this.player = player
    }
}
