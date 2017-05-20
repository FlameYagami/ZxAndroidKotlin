package com.zx.game.enums

/**
 * Created by 八神火焰 on 2017/2/22.
 */

object ClientMessage {
    val Response: Byte = 1
    val HandResult: Byte = 3
    val TpResult: Byte = 4
    val PlayerInfo: Byte = 10
    val CreateGame: Byte = 11
    val JoinGame: Byte = 12
    val LeaveGame: Byte = 13
    val Surrender: Byte = 14
    val TimeConfirm: Byte = 15
    val Chat: Byte = 16
    val HsToDuelist: Byte = 20
    val HsToObserver: Byte = 21
    val DuelistState: Byte = 22
    val HsKick: Byte = 24
    val StartGame: Byte = 25
}
