package com.zx.game.utils

import com.zx.bean.CardBean
import com.zx.bean.RestrictBean
import com.zx.uitls.FileUtils
import com.zx.uitls.JsonUtils
import java.util.*

/**
 * Created by 八神火焰 on 2017/5/4.
 */

object RestrictUtils {
    private val TAG = RestrictUtils::class.java.simpleName

    private var restrictList: List<RestrictBean> = ArrayList()

    private enum class RestrictType {
        NONE, RESTRICT_0, RESTRICT_4, RESTRICT_20, RESTRICT_30
    }

    /**
     * 获取制限枚举类型

     * @param restrict 制限数量字符串
     * *
     * @return 限制枚举类型
     */
    private fun getRestrictType(restrict: String): RestrictType {
        when (restrict) {
            "0" -> return RestrictType.RESTRICT_0
            "4" -> return RestrictType.RESTRICT_4
            "20" -> return RestrictType.RESTRICT_20
            "30" -> return RestrictType.RESTRICT_30
            else -> return RestrictType.NONE
        }
    }

    /**
     * 获取制限列表

     * @return 限制列表
     */
    fun getRestrictList(): List<RestrictBean> {
        if (restrictList.isEmpty()) {
            val restrictJson = FileUtils.getAssetsContent("restrict")
            restrictList = JsonUtils.deserializerList(restrictJson, RestrictBean::class.java)!!
        }
        return restrictList
    }

    /**
     * 获取制限后的卡牌集合

     * @param cardList 源卡牌集合
     * *
     * @param cardBean 查询条件实例
     * *
     * @return 制限后的卡牌集合
     */
    fun getRestrictCardList(cardList: List<CardBean>, cardBean: CardBean): List<CardBean> {
        val mRestrictType = getRestrictType(cardBean.restrict)
        if (mRestrictType == RestrictType.NONE) {
            return cardList
        }
        val restrictCardList = ArrayList<CardBean>()
        val tempCardList = cardList.filter { bean -> getRestrictType(bean.restrict) == mRestrictType }.toMutableList()
        restrictCardList.addAll(tempCardList)
        return restrictCardList
    }
}
