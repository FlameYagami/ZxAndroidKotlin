package com.zx.game

import android.content.Context
import com.zx.config.MyApp.Companion.context
import com.zx.game.enums.PlayerType
import com.zx.game.message.ClientPacket
import com.zx.game.message.MessageManager
import com.zx.game.service.ClientService
import com.zx.uitls.IntentUtils

/**
 * Created by 八神火焰 on 2017/2/22.
 */

class Client {
    var Player: Player? = null
    var Room: Room? = null
    var Game: Game? = null
    private var mMessageManager: MessageManager? = null

    fun start() {
        IntentUtils.startService(ClientService::class.java)
    }

    fun initPlayer(playerName: String) {
        Player = Player(playerName)
    }

    fun initMessageManager(mMessageManager: MessageManager) {
        this.mMessageManager = mMessageManager
    }

    /**
     * 创建房间缓存
     */
    fun createGame(roomId: String, player: Player) {
        if (null == Room) {
            Room = Room(roomId)
        }
        if (null == Game) {
            Game = Game(player)
        }
    }

    /**
     * 销毁房间缓存
     */
    fun leaveGame() {
        Room = null
        Game = null
        Player?.type = PlayerType.Undefined
    }

    fun finish() {
        IntentUtils.sendBroadcast(context as Context, ClientService.STOP_SERVICE)
    }

    fun send(clientPacket: ClientPacket) {
        if (null != mMessageManager) {
            mMessageManager?.sendMessage(clientPacket)
        }
    }

    fun receive(bytes: ByteArray) {
        if (null != mMessageManager) {
            mMessageManager?.receiveMessage(bytes)
        }
    }
}
