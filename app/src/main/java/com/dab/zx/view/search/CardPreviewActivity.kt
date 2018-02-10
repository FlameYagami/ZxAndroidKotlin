package com.dab.zx.view.search

import android.databinding.ViewDataBinding
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.databinding.ActivityCardPreviewBinding
import com.dab.zx.view.base.BaseBindingActivity
import com.dab.zx.viewmodel.CardPreviewVm
import kotlinx.android.synthetic.main.activity_card_preview.*

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class CardPreviewActivity : BaseBindingActivity() {

    private lateinit var mCardPreviewVm: CardPreviewVm

    /**
     * 因为Intent传递最大1M限制,导致必须静态初始化
     */
    companion object {
        lateinit var cardModels : List<CardBean>
    }

    override val layoutId: Int
        get() = R.layout.activity_card_preview

    override fun initViewAndData(mViewDataBinding: ViewDataBinding) {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        mCardPreviewVm = CardPreviewVm(this,cardModels)
        (mViewDataBinding as ActivityCardPreviewBinding).vm = mCardPreviewVm
    }
}
