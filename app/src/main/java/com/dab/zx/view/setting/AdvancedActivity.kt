package com.dab.zx.view.setting

import android.support.v7.app.AlertDialog
import com.dab.zx.R
import com.dab.zx.bean.KeySearchBean
import com.dab.zx.config.SpConst
import com.dab.zx.uc.dialog.DialogBindingCheckBox
import com.dab.zx.uitls.StringUtils
import com.dab.zx.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_advanced.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by 八神火焰 on 2017/1/5.
 */

class AdvancedActivity : BaseActivity() {

    lateinit var mOrderPatternArrays: Array<String>

    override val layoutId: Int
        get() = R.layout.activity_advanced

    override fun initViewAndData() {
        mOrderPatternArrays = resources.getStringArray(R.array.preview_order)
        viewAppBar.setNavigationClickListener { onBackPressed() }
        msgPreviewOrder.setDefaultSp(SpConst.OrderPattern, mOrderPatternArrays[0])
        msgKeySearch.setValue(StringUtils.changeList2String(KeySearchBean.selectKeySearchList))
        msgPreviewOrder.onClick { onOrderPattern_Click() }
        msgKeySearch.onClick { onKeySearch_Click() }
    }

    fun onOrderPattern_Click() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.adv_preview_order))
                .setItems(mOrderPatternArrays) { _, index -> msgPreviewOrder.setDefaultSp(SpConst.OrderPattern, mOrderPatternArrays[index]) }
                .show()
    }

    fun onKeySearch_Click() {
        DialogBindingCheckBox(this, getString(R.string.adv_key_search), KeySearchBean.getKeySearchMap(), { mCheckboxMap ->
            KeySearchBean.saveKeySearchMap(mCheckboxMap)
            msgKeySearch.setValue(StringUtils.changeList2String(KeySearchBean.selectKeySearchList))
        }).show()
    }
}
