package com.dab.zx.game.utils

import android.text.TextUtils
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.bean.DeckPreviewBean
import com.dab.zx.config.Enum
import com.dab.zx.config.MyApp.Companion.context
import com.dab.zx.game.DeckManager
import com.dab.zx.game.utils.CardUtils.getAreaType
import com.dab.zx.uitls.FileUtils
import com.dab.zx.uitls.JsonUtils
import com.dab.zx.uitls.PathManager
import com.dab.zx.uitls.PathManager.deckDir
import java.io.File
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/22.
 */

object DeckUtils {
    private val TAG = DeckUtils::class.java.simpleName

    /**
     * 检测卡组名称是否已被使用

     * @param deckName 卡组名称
     * *
     * @return true|false
     */
    fun checkDeckName(deckName: String): Boolean {
        return deckNameList.any { deckName == it }
    }

    /**
     * 返回卡组名称集合

     * @return 卡组名称集合
     */
    val deckNameList: List<String>
        get() {
            val deckExtension = context.getString(R.string.deck_extension).toString()
            val deckNameList = ArrayList<String>()
            deckNameList.addAll(FileUtils.getFileNameExList(deckDir)
                    .filter { fileNameEx -> fileNameEx.endsWith(deckExtension) }
                    .map { fileNameEx -> fileNameEx.replace(deckExtension, "") }
                    .toList())
            return deckNameList
        }

    /**
     * 获取卡组路径

     * @param deckName 卡组名称
     * *
     * @return 卡组路径
     */
    fun getDeckPath(deckName: String): String {
        return PathManager.deckDir + deckName + context.getString(R.string.deck_extension)
    }

    /**
     * 获取卡组预览集合

     * @return 卡组预览集合
     */
    val deckPreviewList: List<DeckPreviewBean>
        get() {
            val deckPathList = ArrayList<String>()
            val deckPreviewList = ArrayList<DeckPreviewBean>()
            deckPathList.addAll(FileUtils.getFilePathList(deckDir).filter { deckPath -> deckPath.endsWith(context.getString(R.string.deck_extension).toString()) }.toList())
            for (deckPath in deckPathList) {
                val deckName = FileUtils.getFileName(deckPath)
                var numberListJson = FileUtils.getFileContent(deckPath)
                numberListJson = if (TextUtils.isEmpty(numberListJson)) JsonUtils.serializer(ArrayList<String>()) else numberListJson
                val numberListEx = JsonUtils.deserializerArray(numberListJson, Array<String>::class.java)
                val cardBeanList = CardUtils.getCardBeanList(numberListEx as List<String>)
                val playerPath = getPlayerImagePath(cardBeanList)
                val statusMain = context.getString(if (getMainCount(cardBeanList) == 50) R.string.deck_pre_complete else R.string.deck_pre_not_complete).toString()
                val statusExtra = context.getString(if (getExtraCount(cardBeanList) == 10) R.string.deck_pre_complete else R.string.deck_pre_not_complete).toString()
                deckPreviewList.add(DeckPreviewBean(deckName, statusMain, statusExtra, playerPath, numberListEx))
            }
            return deckPreviewList
        }

    fun getNumberListEx(deckName: String): List<String> {
        val numberListEx = ArrayList<String>()
        val deckPath = DeckUtils.getDeckPath(deckName)
        var numberListJson = FileUtils.getFileContent(deckPath)
        numberListJson = if (TextUtils.isEmpty(numberListJson)) JsonUtils.serializer(ArrayList<String>()) else numberListJson
        val tempNumberListEx = JsonUtils.deserializerArray(numberListJson, Array<String>::class.java)!!
        numberListEx.addAll(tempNumberListEx)
        return numberListEx
    }

    /**
     * 获取玩家卡组的数量

     * @param cardBeanList 卡牌信息集合
     * *
     * @return 数量
     */
    private fun getPlayerCount(cardBeanList: List<CardBean>): Int {
        return cardBeanList
                .filter { cardBean -> getAreaType(cardBean) == Enum.AreaType.Player }
                .count()
    }

    /**
     * 获取主卡组的数量

     * @param cardBeanList 卡牌信息集合
     * *
     * @return 数量
     */
    private fun getMainCount(cardBeanList: List<CardBean>): Int {
        return cardBeanList
                .filter { cardBean -> getAreaType(cardBean) == Enum.AreaType.Ig || getAreaType(cardBean) == Enum.AreaType.Ug }
                .count()
    }

    /**
     * 获取额外卡组的数量

     * @param cardBeanList 卡牌信息集合
     * *
     * @return 数量
     */
    private fun getExtraCount(cardBeanList: List<CardBean>): Int {
        return cardBeanList
                .filter { cardBean -> getAreaType(cardBean) == Enum.AreaType.Ex }
                .count()
    }

    /**
     * 获取玩家卡牌路径

     * @param cardBeanList 卡牌信息集合
     * *
     * @return 玩家卡牌路径
     */
    private fun getPlayerImagePath(cardBeanList: List<CardBean>): String {
        return PathManager.pictureDir + File.separator +
                cardBeanList
                        .first { cardBean -> getAreaType(cardBean) == Enum.AreaType.Player }.number + context.getString(R.string.image_extension)
    }

    /**
     * 获取玩家卡牌路径集合

     * @param cardBeanList 卡牌信息集合
     * *
     * @return 玩家卡牌路径
     */
    private fun getPlayerPathList(cardBeanList: List<CardBean>): List<String> {
        val pathList = ArrayList<String>()
        pathList.addAll(cardBeanList
                .filter { cardBean -> getAreaType(cardBean) == Enum.AreaType.Player }
                .map { bean -> PathManager.pictureDir + File.separator + bean.number + context.getString(R.string.image_extension) })
        return pathList
    }

    /**
     * 判断卡组是否符合标准

     * @param numberList 卡编集合
     * *
     * @return ture|false
     */
    fun checkDeck(numberList: List<String>): Boolean {
        val cardBeanList = CardUtils.getCardBeanList(numberList)
        return 1 <= getPlayerCount(cardBeanList) &&
                50 == getMainCount(cardBeanList) &&
                4 >= cardBeanList.count { bean -> CardUtils.isLife(bean.number) } &&
                4 >= cardBeanList.count { bean -> CardUtils.isVoid(bean.number) }
    }

    /**
     * 获取卡组中起始卡和生命恢复和虚空使者总数的集合

     * @return 集合
     */
    fun getStartAndLifeAndVoidCount(mDeckManager: DeckManager): List<Int> {
        val countList = ArrayList<Int>()
        countList.add(mDeckManager.ugList.count { bean -> CardUtils.isStart(bean.numberEx) })
        countList.add(mDeckManager.igList.count { bean -> CardUtils.isLife(bean.numberEx) })
        countList.add(mDeckManager.igList.count { bean -> CardUtils.isVoid(bean.numberEx) })
        return countList
    }

    /**
     * 保存卡组

     * @return ture|false
     */
    fun saveDeck(mDeckManager: DeckManager): Boolean {
        val numberList = ArrayList<String>()
        numberList.addAll(mDeckManager.igList.map { it.numberEx })
        numberList.addAll(mDeckManager.ugList.map { it.numberEx })
        numberList.addAll(mDeckManager.exList.map { it.numberEx })
        numberList.addAll(mDeckManager.playerList.map { it.numberEx })
        val numberListJson = JsonUtils.serializer(numberList)
        val deckPath = DeckUtils.getDeckPath(mDeckManager.deckName)
        return FileUtils.writeFile(numberListJson, deckPath)
    }
}
