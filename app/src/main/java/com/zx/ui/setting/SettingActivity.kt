package com.zx.ui.setting

import com.zx.R
import com.zx.ui.about.AboutActivity
import com.zx.ui.advanced.AdvancedActivity
import com.zx.ui.base.BaseActivity
import com.zx.ui.document.DocumentActivity
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

/**
 * Created by 八神火焰 on 2016/12/21.
 */

class SettingActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initViewAndData() {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        selAdvance.setOnClickListener { startActivity<AdvancedActivity>() }
        selDocument.setOnClickListener { startActivity<DocumentActivity>() }
        selAbout.setOnClickListener { startActivity<AboutActivity>() }
    }
}
