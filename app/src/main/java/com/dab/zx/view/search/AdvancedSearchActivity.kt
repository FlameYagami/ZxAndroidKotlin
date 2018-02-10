package com.dab.zx.view.search

import android.databinding.ViewDataBinding
import com.dab.zx.R
import com.dab.zx.databinding.ActivityAdvancedSearchBinding
import com.dab.zx.view.base.BaseBindingActivity
import com.dab.zx.viewmodel.AdvancedSearchVm
import kotlinx.android.synthetic.main.activity_advanced_search.*

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class AdvancedSearchActivity : BaseBindingActivity() {
    override val layoutId: Int
        get() = R.layout.activity_advanced_search

    override fun initViewAndData(mViewDataBinding: ViewDataBinding) {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        (mViewDataBinding as ActivityAdvancedSearchBinding).vm = AdvancedSearchVm(this)
    }
}
