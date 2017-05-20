package com.zx.game.message

import com.zx.config.MyApp
import com.zx.event.*
import com.zx.game.Client
import com.zx.game.GameConfig
import com.zx.game.Player
import com.zx.game.enums.PlayerChange
import com.zx.game.enums.PlayerType
import com.zx.game.enums.ServiceMessage
import com.zx.uitls.RxBus

/**
 * Created by 八神火焰 on 2017/2/11.
 */

internal class ModBusAnalyser {
    fun analysis(servicePacket: ServicePacket) {
        // 读取设备类型
        servicePacket.readByte()
        // 读取指令
        when (servicePacket.readByte()) {
            ServiceMessage.PlayerEnter -> onPlayerEnter(servicePacket)
            ServiceMessage.JoinGame -> onJoinGame(servicePacket)
            ServiceMessage.GameConfig -> onGameConfig(servicePacket)
            ServiceMessage.DuelistInfo -> onDuelistInfo(servicePacket)
            ServiceMessage.DuelistState -> onDuelistState(servicePacket)
            ServiceMessage.LeaveGame -> onLeaveGame(servicePacket)
            ServiceMessage.StartGame -> onStartGame(servicePacket)
        }
    }

    /**
     * 开始游戏
     */
    private fun onStartGame(servicePacket: ServicePacket) {
        RxBus.instance.post(StartGameEvent())
    }

    /**
     * 向本地玩家传递其他玩家进入房间的消息,并将信息数据存入缓存
     */
    private fun onPlayerEnter(servicePacket: ServicePacket) {
        val playerType = servicePacket.readByte()
        // 成功创建房间或是找到对应房间
        if (playerType != PlayerType.Undefined) {
            val playerName = servicePacket.readStringToEnd()
            val player = Player(playerType, playerName)
            // 对已经在房间的玩家发送其他玩家进入的消息
            RxBus.instance.post(EnterGameEvent(player))
            if (null != MyApp.Client?.Game) {
                (MyApp.Client as Client).Game?.updateDuelist(player)
            }
        }
    }

    /**
     * 向本地玩家传递进入房间的消息,并更新本地玩家类型
     */
    private fun onJoinGame(servicePacket: ServicePacket) {
        val playerType = servicePacket.readByte()
        // 成功创建房间或是找到对应房间
        if (playerType != PlayerType.Undefined) {
            val roomId = servicePacket.readCSharpInt()
            // Game中的本地玩家取自索引
            MyApp.Client?.Player?.type = playerType
            MyApp.Client?.createGame(roomId.toString(), (MyApp.Client as Client).Player as Player)
            RxBus.instance.post(JoinGameEvent(playerType))
        } else {
            RxBus.instance.post(JoinGameEvent(playerType, false))
        }
    }

    private fun onGameConfig(servicePacket: ServicePacket) {
        MyApp.Client?.Game?.GameConfig = GameConfig(servicePacket)
    }

    private fun onDuelistInfo(servicePacket: ServicePacket) {
        val playerType = servicePacket.readByte()
        val isReady = servicePacket.readByte() == PlayerChange.Ready
        val playerName = servicePacket.readStringToEnd()
        MyApp.Client?.Game?.updateDuelist(Player(playerType, playerName, isReady))
    }

    private fun onDuelistState(servicePacket: ServicePacket) {
        val playerType = servicePacket.readByte()
        val isReady = servicePacket.readByte() == PlayerChange.Ready
        MyApp.Client?.Game?.setPlayerReady(playerType, isReady)
        // 向界面告知选手状态改变
        RxBus.instance.post(DuelistStateEvent())
    }

    private fun onLeaveGame(servicePacket: ServicePacket) {
        val playerType = servicePacket.readByte()
        val playerName = servicePacket.readStringToEnd()
        // 向界面告知选手离开
        RxBus.instance.post(LeaveGameEvent(Player(playerType, playerName), MyApp.Client?.Player?.type as Byte))
        // 对应本地用户准备状态更新
        if (playerType == (MyApp.Client as Client).Player?.type) {
            (MyApp.Client as Client).leaveGame()
        } else {
            (MyApp.Client as Client).Game?.removePlayer(Player(playerType, playerName))
        }
    }
}
