package com.dab.zx.viewmodel

import android.content.Context
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.binding.SingleItemVm
import com.dab.zx.binding.annotation.SubitemResHolder
import com.dab.zx.databinding.ItemCardPreviewBinding
import com.dab.zx.view.search.CardDetailActivity
import org.jetbrains.anko.startActivity

/**
 * Created by 八神火焰 on 2017/12/27.
 */
@SubitemResHolder(R.layout.item_card_preview)
class CardPreviewVm(context: Context, cardModels :List<CardBean>) : SingleItemVm<ItemCardPreviewBinding, CardBean>(context) {

    init {
        setOnItemClickListener { view, data, position ->
            val card = data[position]
            view.context.startActivity<CardDetailActivity>(CardBean::class.java.simpleName to card)
        }
        data.addAll(cardModels)
    }
}