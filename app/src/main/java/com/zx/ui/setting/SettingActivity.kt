package com.zx.ui.setting

import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R
import com.zx.ui.about.AboutActivity
import com.zx.ui.advanced.AdvancedActivity
import com.zx.ui.base.BaseActivity
import com.zx.ui.document.DocumentActivity
import com.zx.uitls.IntentUtils
import com.zx.view.widget.AppBarView
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class SettingActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar.setNavigationClickListener(object : AppBarView.NavigationClickListener {
            override fun onNavigationClick() {
                onBackPressed()
            }
        })
    }

    @OnClick(R.id.msg_advance)
    fun onAdvance_Click() {
        IntentUtils.gotoActivity(this, AdvancedActivity::class.java)
    }

    @OnClick(R.id.msg_document)
    fun onDocument_Click() {
        IntentUtils.gotoActivity(this, DocumentActivity::class.java)
    }

    @OnClick(R.id.msg_about)
    fun onAbout_Click() {
        IntentUtils.gotoActivity(this, AboutActivity::class.java)
    }

}
