package com.dab.zx.viewmodel

import android.app.Activity
import android.databinding.ObservableField
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.view.search.CardPreviewAdapter

/**
 * Created by 八神火焰 on 2017/12/27.
 */
class CardPreviewVm {

    var cardList: List<CardBean>
    var adapter: CardPreviewAdapter
    var title: ObservableField<String>

    constructor(activity: Activity) {
        cardList = activity.intent.extras.getSerializable(CardBean::class.java.simpleName) as List<CardBean>
        adapter = CardPreviewAdapter(activity)
        title = ObservableField(activity.getString(R.string.card_preview_about_title) + cardList.size)
    }
}