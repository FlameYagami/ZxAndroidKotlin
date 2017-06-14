package com.zx.ui.advanced

import android.support.v7.app.AlertDialog
import com.zx.R
import com.zx.bean.KeySearchBean
import com.zx.config.SpConst
import com.zx.ui.base.BaseActivity
import com.zx.uitls.StringUtils
import com.zx.view.dialog.DialogCheckBox
import kotlinx.android.synthetic.main.activity_advanced.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by 八神火焰 on 2017/1/5.
 */

class AdvancedActivity : BaseActivity() {

    companion object {
        var mOrderPatternArrays: Array<String>? = null
    }

    override val layoutId: Int
        get() = R.layout.activity_advanced

    override fun initViewAndData() {
        mOrderPatternArrays = resources.getStringArray(R.array.order)
        viewAppBar.setNavigationClickListener { onBackPressed() }
        msgPreviewOrder.setDefaultSp(SpConst.OrderPattern, mOrderPatternArrays!![0])
        msgKeySearch.setValue(StringUtils.changeList2String(KeySearchBean.selectKeySearchList))
        msgPreviewOrder.onClick { onOrderPattern_Click() }
        msgKeySearch.onClick { onKeySearch_Click() }
    }

    fun onOrderPattern_Click() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.preview_order))
                .setItems(mOrderPatternArrays) { _, index -> msgKeySearch.setDefaultSp(SpConst.OrderPattern, mOrderPatternArrays?.get(index) as String) }
                .show()
    }

    fun onKeySearch_Click() {
        DialogCheckBox(this, getString(R.string.key_search), KeySearchBean.getKeySearchMap(), { mCheckboxMap ->
            KeySearchBean.saveKeySearchMap(mCheckboxMap)
            msgKeySearch.setValue(StringUtils.changeList2String(KeySearchBean.selectKeySearchList))
        }).show()
    }
}
