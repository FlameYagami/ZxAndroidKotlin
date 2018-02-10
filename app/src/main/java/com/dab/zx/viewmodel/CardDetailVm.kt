package com.dab.zx.viewmodel

import android.app.Activity
import android.content.Context
import com.dab.zx.bean.CardBean
import com.dab.zx.game.utils.CardUtils
import com.dab.zx.model.BannerModel

/**
 * Created by 八神火焰 on 2018/1/5.
 */
class CardDetailVm(context: Context) {

    var bannerModel: BannerModel = BannerModel()
    var cardModel: CardBean? = null
    var signResId: Int? = null
    var campResIdList: List<Int>? = null

    init {
        cardModel = (context as Activity).intent.extras.getSerializable(CardBean::class.java.simpleName) as CardBean
        signResId = CardUtils.getSignResId(cardModel!!.sign)
        campResIdList = CardUtils.getCampResIdList(cardModel!!.camp)
        bannerModel.images = CardUtils.getImagePathList(cardModel!!.image)
    }
}