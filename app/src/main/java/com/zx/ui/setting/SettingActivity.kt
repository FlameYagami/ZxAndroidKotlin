package com.zx.ui.setting

import com.zx.R
import com.zx.ui.about.AboutActivity
import com.zx.ui.advanced.AdvancedActivity
import com.zx.ui.base.BaseActivity
import com.zx.ui.document.DocumentActivity
import com.zx.uitls.IntentUtils
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class SettingActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initViewAndData() {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        msg_advance.setOnClickListener { IntentUtils.gotoActivity(this, AdvancedActivity::class.java) }
        msg_document.setOnClickListener { IntentUtils.gotoActivity(this, DocumentActivity::class.java) }
        msg_about.setOnClickListener { IntentUtils.gotoActivity(this, AboutActivity::class.java) }
    }
}
