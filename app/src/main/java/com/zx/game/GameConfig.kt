package com.zx.game

import com.zx.game.message.ServicePacket

/**
 * Created by 八神火焰 on 2017/2/28.
 */

class GameConfig {
    var startLp: Int = 0
        private set
    var startHand: Int = 0
        private set
    var startResouce: Int = 0
        private set
    var drawCount: Int = 0
        private set
    var gameTimer: Int = 0
        private set
    var isShuffleDeck: Boolean = false
        private set

    constructor() {
        startLp = 4
        startHand = 4
        startResouce = 2
        drawCount = 2
        gameTimer = 120
        isShuffleDeck = true
    }

    constructor(packet: ServicePacket) {
        startLp = packet.readByte().toInt()
        startHand = packet.readByte().toInt()
        startResouce = packet.readByte().toInt()
        drawCount = packet.readByte().toInt()
        gameTimer = packet.readByte().toInt()
        isShuffleDeck = packet.readByte().toInt() == 1
    }
}
