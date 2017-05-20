package com.zx.game

import com.zx.uitls.LogUtils
import java.util.*

/**
 * Created by 八神火焰 on 2017/2/22.
 */

class Game(player: Player) {

    var GameConfig: GameConfig? = null
    val duelists = arrayOfNulls<Player>(2)
    private val observerList = ArrayList<Player>()

    init {
        if (2 != player.type.toInt()) {
            duelists[player.type.toInt()] = player
        } else {
            observerList.add(player)
        }
    }

    fun updateDuelist(player: Player) {
        if (0 == player.type.toInt() || 1 == player.type.toInt()) {
            if (null == duelists[player.type.toInt()]) {
                duelists[player.type.toInt()] = player
            } else {
                duelists[player.type.toInt()]?.isReady = player.isReady
            }
        }
    }

    val duelistCount: Int
        get() = duelists.count { duelist -> null != duelist }

    val observerCount: Int
        get() = observerList.size

    fun getObserverList(): List<Player> {
        return observerList
    }

    fun removePlayer(player: Player) {
        if (1 == player.type.toInt()) {
            duelists[1] = null
        } else {
            observerList.remove(observerList.first { observer -> observer.name == player.name })
        }
    }

    fun setPlayerReady(playerType: Byte, isReady: Boolean) {
        if (0 == playerType.toInt() || 1 == playerType.toInt()) {
            duelists[playerType.toInt()]?.isReady = isReady
        } else {
            LogUtils.e(TAG, "setPlayerReady->越界")
        }
    }

    companion object {
        private val TAG = Game::class.java.simpleName
    }

    //    public boolean moveToDuelist(String playerName) {
    //        Player tempPlayer = stream(duelists).first(player -> player.getName().equals(playerName));
    //        return duelists.add(tempPlayer) && observerList.remove(tempPlayer);
    //    }

    //    public boolean moveToObserver(String playerName) {
    //        Player tempPlayer = stream(observerList).first(player -> player.getName().equals(playerName));
    //        return observerList.add(tempPlayer) && duelists.remove(tempPlayer);
    //
    //    }
}
