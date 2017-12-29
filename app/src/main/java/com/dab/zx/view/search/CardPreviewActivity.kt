package com.dab.zx.view.search

import android.databinding.ViewDataBinding
import com.dab.zx.R
import com.dab.zx.databinding.ActivityCardPreviewBinding
import com.dab.zx.view.base.BaseActivity
import com.dab.zx.viewmodel.CardPreviewVm
import kotlinx.android.synthetic.main.activity_card_preview.*

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class CardPreviewActivity : BaseActivity() {

    private lateinit var mCardPreviewVm: CardPreviewVm

    override val layoutId: Int
        get() = R.layout.activity_card_preview

    override fun initViewAndData(dataBinding: ViewDataBinding) {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        mCardPreviewVm = CardPreviewVm(this)
        (dataBinding as ActivityCardPreviewBinding).vm = mCardPreviewVm
    }
}
