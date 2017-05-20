package com.zx.ui.document

import butterknife.ButterKnife
import com.zx.R
import com.zx.ui.base.BaseActivity

/**
 * Created by 八神火焰 on 2017/1/5.
 */

class DocumentActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_document

    override fun initViewAndData() {
        ButterKnife.bind(this)
    }

    override fun onNavigationClick() {
        onBackPressed()
    }
}
