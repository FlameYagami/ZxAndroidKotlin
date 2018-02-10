package com.dab.zx.game.utils

import android.text.TextUtils
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.config.Enum
import com.dab.zx.config.MapConst
import com.dab.zx.config.MyApp.Companion.context
import com.dab.zx.uitls.JsonUtils
import com.dab.zx.uitls.PathManager.pictureDir
import com.dab.zx.uitls.database.SQLiteUtils
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/13.
 */

object CardUtils {
    private val TAG = CardUtils::class.java.simpleName

    /**
     * 获取卡牌对应图片的路经集合

     * @param imageJson 卡牌图片编号Json
     * *
     * @return 卡牌对应图片的路经集合
     */
    fun getImagePathList(imageJson: String): List<String> {
        return JsonUtils.deserializerArray(imageJson, Array<String>::class.java)
                ?.map { image -> pictureDir + image + context.getString(R.string.image_extension) } as List<String>
    }

    /**
     * 获取标记的图片资源Id

     * @param sign 标记类型
     * *
     * @return Id
     */
    fun getSignResId(sign: String): Int {
        var result = -1
        for (pair: Map.Entry<String, Int> in MapConst.SignMap) {
            if (pair.key == sign) {
                result = pair.value
                break
            }
        }
        return result
    }

    /**
     * 获取阵营的图片资源Id

     * @param Camp 阵营类型
     * *
     * @return Id
     */
    fun getCampResId(Camp: String): Int {
        var result = -1
        for (pair: Map.Entry<String, Int> in MapConst.CampMap) {
            if (pair.key == Camp) {
                result = pair.value
                break
            }
        }
        return result
    }

    /**
     * 获取阵营的图片资源Id

     * @param camp 阵营类型
     * *
     * @return Id
     */
    fun getCampResIdList(camp: String): List<Int> {
        val campResIdList = camp
                .split("/".toRegex())
                .dropLastWhile({ it.isEmpty() })
                .toTypedArray()
                .mapTo(ArrayList()) { getCampResId(it) }
        while (campResIdList.size < 5) {
            campResIdList.add(-1)
        }
        return campResIdList
    }

    /**
     * 获取罕贵的图片资源Id

     * @param rare 阵营类型
     * *
     * @return Id
     */
    fun getRareResId(rare: String): Int {
        var result = -1
        for (pair: Map.Entry<String, Int> in MapConst.RareMap) {
            if (pair.key == rare) {
                result = pair.value
                break
            }
        }
        return result
    }

    /**
     * 获取画师集合

     * @return 画师集合
     */
    val illust: List<String>
        get() {
            val illustList = SQLiteUtils.getAllCardList()
                    .map { it.illust }
                    .distinct()
                    .sortedBy { it }
                    .toMutableList()
            illustList.add(0, context.getString(R.string.not_applicable).toString())
            return illustList
        }

    /**
     * 获取卡包集合

     * @return 卡包集合
     */
    val allPack: List<String>
        get() {
            val packList = ArrayList<String>()
            packList.addAll(getPartPack("B"))
            packList.addAll(getPartPack("C"))
            packList.addAll(getPartPack("E"))
            packList.addAll(getPartPack("P"))
            packList.addAll(getPartPack("L"))
            packList.addAll(getPartPack("M"))
            packList.addAll(getPartPack("I"))
            packList.addAll(getPartPack("V"))
            packList.add(0, context.getString(R.string.not_applicable) as String)
            return packList
        }

    /**
     * 获取部分卡包集合

     * @param packType 卡包类型
     * *
     * @return 部分卡包集合
     */
    private fun getPartPack(packType: String): List<String> {
        val packList = SQLiteUtils.getAllCardList()
                .filter { bean -> bean.pack.contains(packType) }
                .map { it.pack }
                .distinct()
                .sortedBy { it }
                .toMutableList()
        packList.add(0, packType + context.getString(R.string.series))
        return packList
    }

    /**
     * 获取部分种族集合

     * @param camp 阵营
     * *
     * @return 部分种族集合
     */
    fun getPartRace(camp: String): List<String> {
        val campList = SQLiteUtils.getAllCardList()
                .filter { bean -> bean.camp == camp }
                .map { it.race }
                .distinct()
                .sortedBy { it.length }
                .toMutableList()
        campList.add(0, context.getString(R.string.not_applicable).toString())
        return campList
    }

    /**
     * 获取卡片进入的区域

     * @param cardBean 卡牌信息
     * *
     * @return 枚举类型
     */
    fun getAreaType(cardBean: CardBean): Enum.AreaType {
        if (context.getString(R.string.SignIg) == cardBean.sign) {
            return Enum.AreaType.Ig
        }
        if (context.getString(R.string.TypeZxEx) == cardBean.type) {
            return Enum.AreaType.Ex
        }
        if (context.getString(R.string.TypePlayer) == cardBean.type) {
            return Enum.AreaType.Player
        }
        return Enum.AreaType.Ug
    }


    /**
     * 获取卡片进入的区域

     * @param number 编号
     * *
     * @return 枚举类型
     */
    fun getAreaType(number: String): Enum.AreaType {
        val cardBean = SQLiteUtils.getAllCardList().first { bean -> bean.number == number }
        return getAreaType(cardBean)
    }

    /**
     * 获取中文卡名

     * @param number 卡编
     * *
     * @return 卡名
     */
    fun getCName(number: String): String {
        return SQLiteUtils.getAllCardList().first { bean -> number.contains(bean.number) }.cname
    }

    /**
     * 获取卡片的最大数量

     * @param number 卡编
     * *
     * @return 数量
     */
    fun getMaxCount(number: String): Int {
        val restrict = SQLiteUtils.getAllCardList().first { bean -> bean.number == number }.restrict
        return if (TextUtils.isEmpty(restrict)) 4 else Integer.valueOf(restrict)
    }

    /**
     * 获取卡片在点燃区的枚举类型

     * @param number 卡编
     * *
     * @return Life|Void|Normal
     */
    fun getIgType(number: String): Enum.IgType {
        return getIgType(SQLiteUtils.getAllCardList().first { bean -> number.contains(bean.number) })
    }

    /**
     * 获取卡片在点燃区的枚举类型

     * @param cardBean 卡牌信息
     * *
     * @return Life|Void|Normal
     */
    fun getIgType(cardBean: CardBean): Enum.IgType {
        val ability = cardBean.ability
        if (ability.startsWith(context.getString(R.string.AbilityLife).toString())) {
            return Enum.IgType.Life
        }
        if (ability.startsWith(context.getString(R.string.AbilityVoid).toString())) {
            return Enum.IgType.Void
        }
        return Enum.IgType.Normal
    }

    /**
     * 获取卡片在非点燃区的枚举类型

     * @param number 卡编
     * *
     * @return Start|Normal
     */
    fun getUgType(number: String): Enum.UgType {
        return getUgType(SQLiteUtils.getAllCardList().first { bean -> number.contains(bean.number) })
    }

    /**
     * 获取卡片在非点燃区的枚举类型

     * @param cardBean 卡牌信息
     * *
     * @return Start|Normal
     */
    fun getUgType(cardBean: CardBean): Enum.UgType {
        val ability = cardBean.ability
        if (ability.startsWith(context.getString(R.string.AbilityStart) as String)) {
            return Enum.UgType.Start
        }
        return Enum.UgType.Normal
    }

    /**
     * 判断卡片是否为生命恢复

     * @param number 卡编
     * *
     * @return Ture|Flase
     */
    fun isLife(number: String): Boolean {
        return isLife(SQLiteUtils.getAllCardList().first { bean -> number.contains(bean.number) })
    }

    /**
     * 判断卡片是否为生命恢复

     * @param cardBean 卡牌信息
     * *
     * @return Ture|Flase
     */
    fun isLife(cardBean: CardBean): Boolean {
        return cardBean.ability.startsWith(context.getString(R.string.AbilityLife).toString())
    }

    /**
     * 判断卡片是否为虚空使者

     * @param number 卡编
     * *
     * @return Ture|Flase
     */
    fun isVoid(number: String): Boolean {
        return isVoid(SQLiteUtils.getAllCardList().first { bean -> number.contains(bean.number) })
    }

    /**
     * 判断卡片是否为虚空使者

     * @param cardBean 卡牌信息
     * *
     * @return Ture|Flase
     */
    fun isVoid(cardBean: CardBean): Boolean {
        return cardBean.ability.startsWith(context.getString(R.string.AbilityVoid).toString())
    }

    /**
     * 判断卡片是否为起始卡

     * @param number 卡编
     * *
     * @return Ture|Flase
     */
    fun isStart(number: String): Boolean {
        return isStart(SQLiteUtils.getAllCardList().first { bean -> number.contains(bean.number) })
    }

    /**
     * 判断卡片是否为起始卡

     * @param cardBean 卡牌信息
     * *
     * @return Ture|Flase
     */
    fun isStart(cardBean: CardBean): Boolean {
        return cardBean.ability.contains(context.getString(R.string.AbilityStart).toString())
    }

    /**
     * 获取卡牌的扩展编号

     * @param imageJson 卡牌图片编号Json
     * *
     * @param index     索引
     * *
     * @return 扩展编号
     */
    fun getNumberEx(imageJson: String, index: Int): String {
        return JsonUtils.deserializerArray(imageJson, Array<String>::class.java)!![index]
                .replace("/", "")
                .replace(context.getString(R.string.image_extension).toString(), "")
    }

    /**
     * 获取卡牌信息

     * @param number 卡编
     * *
     * @return 卡牌信息
     */
    fun getCardBean(number: String): CardBean {
        return SQLiteUtils.getAllCardList().first { bean -> number.contains(bean.number) }
    }

    /**
     * 获取卡牌信息集合

     * @param numberList 卡编集合
     * *
     * @return 卡牌信息集合
     */
    fun getCardBeanList(numberList: List<String>): List<CardBean> {
        return numberList.map { getCardBean(it) }
    }

    /**
     * 获取卡牌的Md5

     * @param numberEx 卡编
     * *
     * @return Md5
     */
    fun getMd5(numberEx: String): String {
        return SQLiteUtils.getAllCardList().first { bean -> numberEx.contains(bean.number) }.md5
    }
}
