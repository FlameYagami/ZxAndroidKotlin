package com.dab.zx.view.search

import android.databinding.ViewDataBinding
import com.dab.zx.R
import com.dab.zx.databinding.ActivityCardDetailBinding
import com.dab.zx.view.base.BaseBindingActivity
import com.dab.zx.viewmodel.CardDetailVm
import kotlinx.android.synthetic.main.activity_card_detail.*

/**
 * Created by 八神火焰 on 2016/12/13.
 */

class CardDetailActivity : BaseBindingActivity() {

    override val layoutId: Int
        get() = R.layout.activity_card_detail

    override fun initViewAndData(dataBinding: ViewDataBinding) {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        (dataBinding as ActivityCardDetailBinding).vm = CardDetailVm(this)
    }
}
