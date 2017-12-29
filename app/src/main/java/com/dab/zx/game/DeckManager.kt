package com.dab.zx.game

import android.text.TextUtils
import com.dab.zx.R
import com.dab.zx.bean.DeckBean
import com.dab.zx.config.Enum
import com.dab.zx.config.MyApp.Companion.context
import com.dab.zx.game.utils.CardUtils
import com.dab.zx.game.utils.CardUtils.getAreaType
import com.dab.zx.game.utils.CardUtils.isLife
import com.dab.zx.game.utils.CardUtils.isVoid
import com.dab.zx.uitls.PathManager.pictureDir
import com.dab.zx.uitls.database.SQLiteUtils
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by 八神火焰 on 2017/1/14.
 */

class DeckManager
/**
 * 初始化

 * @param deckName     卡组名称
 * *
 * @param numberExList 编号扩展集合(B01-001A、B01-001B)
 */
(// 卡组名称
        val deckName: String, numberExList: List<String>?) {

    // 卡组Md5集合
    private val Md5List = ArrayList<String>()

    // 卡组扩展名集合
    private var NumberExList = ArrayList<String>()

    // 玩家卡数据缓存
    private val PlayerList = ArrayList<DeckBean>()

    // 起始卡缓存
    private val StartList = ArrayList<DeckBean>()

    // 点燃数据缓存
    private val IgList = ArrayList<DeckBean>()

    // 非点燃数据缓存
    private val UgList = ArrayList<DeckBean>()

    // 额外数据缓存
    private val ExList = ArrayList<DeckBean>()

    init {
        clearDeck()
        this.NumberExList = numberExList as ArrayList<String>
        loadDeck()
    }

    val playerList: List<DeckBean>
        get() = PlayerList

    val startList: List<DeckBean>
        get() = StartList

    val igList: List<DeckBean>
        get() = IgList

    val ugList: List<DeckBean>
        get() = UgList

    val exList: List<DeckBean>
        get() = ExList

    val numberExList: List<String>
        get() = NumberExList

    val md5List: List<String>
        get() = Md5List

    /**
     * 清空卡组
     */
    private fun clearDeck() {
        Md5List.clear()
        NumberExList.clear()
        PlayerList.clear()
        StartList.clear()
        IgList.clear()
        UgList.clear()
        ExList.clear()
    }

    /**
     * 加载卡组
     */
    private fun loadDeck() {
        for (numberEx in NumberExList) {
            val areaType = getAreaType(numberEx)
            val imagePath = pictureDir + File.separator + numberEx + context.getString(R.string.image_extension)
            addCard(areaType, numberEx, imagePath)
        }
    }

    /**
     * 添加卡片到组卡区

     * @param areaType      卡片添加区域枚举类型
     * *
     * @param number        卡编
     * *
     * @param thumbnailPath 缩略图路径
     * *
     * @return 枚举类型
     */
    fun addCard(areaType: Enum.AreaType, number: String, thumbnailPath: String): Enum.AreaType {
        Md5List.add(CardUtils.getMd5(number))
        if (areaType == Enum.AreaType.Player) {
            PlayerList.clear()
            addCardToList(number, thumbnailPath, PlayerList)
            return Enum.AreaType.Player
        } else if (areaType == Enum.AreaType.Ig) {
            if (checkAreaIg(number)) {
                addCardToList(number, thumbnailPath, IgList)
                return Enum.AreaType.Ig
            }
        } else if (areaType == Enum.AreaType.Ug) {
            if (checkAreaUg(number)) {
                addCardToList(number, thumbnailPath, UgList)
                if (CardUtils.getUgType(number) == Enum.UgType.Start) {
                    addCardToList(number, thumbnailPath, StartList)
                }
                return Enum.AreaType.Ug
            }
        } else if (areaType == Enum.AreaType.Ex) {
            if (checkAreaEx(number)) {
                addCardToList(number, thumbnailPath, ExList)
                return Enum.AreaType.Ex
            }
        }
        return Enum.AreaType.None
    }

    /**
     * 从组卡区删除卡牌

     * @param areaType 卡片添加区域枚举类型
     * *
     * @param number   卡编
     * *
     * @return 枚举类型
     */
    fun deleteCard(areaType: Enum.AreaType, number: String): Enum.AreaType {
        Md5List.remove(CardUtils.getMd5(number))
        if (areaType == Enum.AreaType.Player) {
            PlayerList.clear()
            return Enum.AreaType.Player
        } else if (areaType == Enum.AreaType.Ig) {
            IgList.remove(IgList.first { bean -> bean.numberEx == number })
            return Enum.AreaType.Ig
        } else if (areaType == Enum.AreaType.Ug) {
            UgList.remove(UgList.first { bean -> bean.numberEx == number })
            if (CardUtils.getUgType(number) == Enum.UgType.Start) {
                StartList.remove(StartList.first { bean -> bean.numberEx == number })
            }
            return Enum.AreaType.Ug
        } else if (areaType == Enum.AreaType.Ex) {
            ExList.remove(ExList.first { bean -> bean.numberEx == number })
            return Enum.AreaType.Ex
        }
        return Enum.AreaType.None
    }

    /**
     * 添加卡牌信息到指定的集合

     * @param thumbnailPath 卡片缩略图
     * *
     * @param numberEx      扩展卡编
     * *
     * @param deckList    指定的集合
     */
    private fun addCardToList(numberEx: String, thumbnailPath: String, deckList: MutableList<DeckBean>) {
        val cardBean = SQLiteUtils.getAllCardList().first { bean -> bean.number == numberEx }
        val name = cardBean.cname
        val camp = cardBean.camp
        val cost = cardBean.cost
        val power = cardBean.power
        val costInt = if (TextUtils.isEmpty(cost) || cost == "-") 0 else Integer.valueOf(cost)
        val powerInt = if (TextUtils.isEmpty(power) || power == "-") 0 else Integer.valueOf(power)
        deckList.add(DeckBean(thumbnailPath, name, camp, numberEx, costInt, powerInt))
    }

    /**
     * 返回卡编是否具有添加到额外区域的权限

     * @param number 卡编
     * *
     * @return true|false
     */
    private fun checkAreaEx(number: String): Boolean {
        val name = CardUtils.getCName(number)
        return ExList.count { bean -> name == bean.cName } < CardUtils.getMaxCount(number) && ExList.size < 10
    }

    /**
     * 返回卡编是否具有添加到非点燃区域的权限

     * @param number 卡编
     * *
     * @return true|false
     */
    private fun checkAreaUg(number: String): Boolean {
        val name = CardUtils.getCName(number)
        return UgList.count { bean -> name == bean.cName } < CardUtils.getMaxCount(number) && UgList.size < 30
    }

    /**
     * 返回卡编是否具有添加到点燃区域的权限

     * @param number 卡编
     * *
     * @return true|false
     */
    private fun checkAreaIg(number: String): Boolean {
        val name = CardUtils.getCName(number)
        // 根据卡编获取卡片在点燃区的枚举类型
        val igType = CardUtils.getIgType(number)
        // 判断卡片是否超出自身添加数量以及点燃区总数量
        var canAdd = IgList.count { bean -> name == bean.cName } < CardUtils.getMaxCount(number) && IgList.size < 20
        if (igType == Enum.IgType.Life) {
            canAdd = canAdd && IgList.count { bean -> isLife(bean.numberEx) } < 4
        } else if (igType == Enum.IgType.Void) {
            canAdd = canAdd && IgList.count { bean -> isVoid(bean.numberEx) } < 4
        }
        return canAdd
    }

    /**
     * 卡组排序

     * @param value 排序枚举类型
     */
    fun orderDeck(value: Enum.DeckOrderType) {
        if (value == Enum.DeckOrderType.Value) {
            orderByValue(IgList)
            orderByValue(UgList)
            orderByValue(ExList)
        } else if (value == Enum.DeckOrderType.Random) {
            orderByRandom(IgList)
            orderByRandom(UgList)
            orderByRandom(ExList)
        }
    }

    /**
     * 数值排序

     * @param deckList 卡组集合
     */
    private fun orderByValue(deckList: MutableList<DeckBean>) {
        deckList.sortWith(compareBy({ it.camp }, { it.cost }, { it.power }, { it.numberEx }))
    }

    /**
     * 随机排序

     * @param deckList 卡组集合
     */
    private fun orderByRandom(deckList: MutableList<DeckBean>) {
        val tempDeckList = ArrayList<DeckBean>()
        val random = Random()
        for (deck in deckList) {
            tempDeckList.add(random.nextInt(tempDeckList.size + 1), deck)
        }
        deckList.clear()
        deckList.addAll(tempDeckList)
    }

    companion object {
        private val TAG = DeckManager::class.java.simpleName
    }
}
