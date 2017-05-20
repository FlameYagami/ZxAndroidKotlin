package com.zx.game.message

import android.text.TextUtils

import com.zx.game.DeckManager
import com.zx.game.Player
import com.zx.game.enums.ClientMessage
import com.zx.game.enums.PlayerChange
import com.zx.uitls.JsonUtils

object ModBusCreator {
    val TAG = ModBusCreator::class.java.simpleName

    private fun getModBus(cmd: Byte, data: String = ""): ClientPacket {
        val packet = ClientPacket()
        packet.write(cmd)
        if (!TextUtils.isEmpty(data)) {
            packet.write(data)
        }
        return packet
    }

    fun onHeart(): ClientPacket {
        return getModBus(ClientMessage.Response, "")
    }

    /**
     * 创建房间
     */
    fun onCreateRoom(player: Player): ClientPacket {
        return getModBus(ClientMessage.CreateGame, player.name.toString())
    }

    /**
     * 进入房间

     * @param roomId 8位房间编号
     */
    fun onJoinRoom(roomId: String, player: Player): ClientPacket {
        return getModBus(ClientMessage.JoinGame, player.name + "#" + roomId)
    }

    /**
     * 离开房间
     */
    fun onLeaveRoom(player: Player): ClientPacket {
        val packet = getModBus(ClientMessage.LeaveGame)
        packet.write(player.type)
        return packet
    }

    /**
     * 设置准备、取消准备
     */
    fun onPlayerState(player: Player): ClientPacket {
        val packet = getModBus(ClientMessage.DuelistState)
        packet.write(if (player.isReady) PlayerChange.NotReady else PlayerChange.Ready)
        return packet
    }

    /**
     * 开始游戏、更新卡组信息到服务器
     */
    fun onStartGame(deckManager: DeckManager): ClientPacket {
        return getModBus(ClientMessage.StartGame, JsonUtils.serializer(deckManager.numberExList))
    }
}