package com.zx.game.enums

/**
 * Created by 八神火焰 on 2017/2/22.
 */

object ServiceMessage {
    val GameMsg: Byte = 1
    val ErrorMsg: Byte = 2
    val HandResult: Byte = 5
    val TpResult: Byte = 6
    val CreateGame: Byte = 11
    val JoinGame: Byte = 12
    val TypeChange: Byte = 13
    val LeaveGame: Byte = 14
    val DuelStart: Byte = 15
    val DuelEnd: Byte = 16
    val Replay: Byte = 17
    val TimeLimit: Byte = 18
    val Chat: Byte = 19
    val PlayerEnter: Byte = 20
    val DuelistInfo: Byte = 21
    val DuelistState: Byte = 22
    val GameConfig: Byte = 23
    val StartGame: Byte = 25
}
