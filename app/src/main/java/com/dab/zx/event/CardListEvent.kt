package com.dab.zx.event

import com.dab.zx.bean.CardBean
import java.io.Serializable
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/23.
 */

class CardListEvent(cardList: List<CardBean>) : Serializable {
    internal val cardList = ArrayList<CardBean>()

    fun getCardList(): List<CardBean> {
        return cardList
    }

    init {
        this.cardList.addAll(cardList)
    }
}
