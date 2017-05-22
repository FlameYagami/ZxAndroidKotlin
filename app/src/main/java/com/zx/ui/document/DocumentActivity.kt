package com.zx.ui.document

import butterknife.ButterKnife
import com.zx.R
import com.zx.ui.base.BaseActivity
import com.zx.view.widget.AppBarView
import kotlinx.android.synthetic.main.activity_document.*

/**
 * Created by 八神火焰 on 2017/1/5.
 */

class DocumentActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_document

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar.setNavigationClickListener(object : AppBarView.NavigationClickListener {
            override fun onNavigationClick() {
                onBackPressed()
            }
        })
    }
}
