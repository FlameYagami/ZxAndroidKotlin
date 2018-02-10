package com.dab.zx.view.setting

import com.dab.zx.R
import com.dab.zx.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_document.*

/**
 * Created by 八神火焰 on 2017/1/5.
 */

class DocumentActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_document

    override fun initViewAndData() {
        viewAppBar.setNavigationClickListener { onBackPressed() }
    }
}
