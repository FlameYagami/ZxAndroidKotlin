package com.dab.zx.view.setting

import com.dab.zx.R
import com.dab.zx.view.base.BaseActivity
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
