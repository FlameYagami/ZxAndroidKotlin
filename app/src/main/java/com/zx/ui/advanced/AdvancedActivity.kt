package com.zx.ui.advanced

import android.support.v7.app.AlertDialog
import butterknife.BindArray
import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R
import com.zx.bean.KeySearchBean
import com.zx.config.SpConst
import com.zx.ui.base.BaseActivity
import com.zx.uitls.StringUtils
import com.zx.view.dialog.DialogCheckBox
import com.zx.view.widget.AppBarView
import kotlinx.android.synthetic.main.activity_advanced.*
import java.util.*

/**
 * Created by 八神火焰 on 2017/1/5.
 */

class AdvancedActivity : BaseActivity() {
    @BindArray(R.array.order)
    internal var mOrderPatternArrays: Array<String>? = null

    override val layoutId: Int
        get() = R.layout.activity_advanced

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar.setNavigationClickListener(object : AppBarView.NavigationClickListener {
            override fun onNavigationClick() {
                onBackPressed()
            }
        })
        msg_preview_order?.setDefaultSp(SpConst.OrderPattern, mOrderPatternArrays!![0])
        msg_key_search.setValue(StringUtils.changeList2String(KeySearchBean.selectKeySearchList))
    }

    @OnClick(R.id.msg_preview_order)
    fun onOrderPattern_Click() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.preview_order))
                .setItems(mOrderPatternArrays) { _, index -> msg_key_search.setDefaultSp(SpConst.OrderPattern, mOrderPatternArrays!![index]) }
                .show()
    }

    @OnClick(R.id.msg_key_search)
    fun onKeySearch_Click() {
        DialogCheckBox(this, getString(R.string.key_search), KeySearchBean.getKeySearchMap(), object : DialogCheckBox.OnClickListener {
            override fun getCheckBoxMap(mCheckboxMap: LinkedHashMap<String, Boolean>) {
                KeySearchBean.saveKeySearchMap(mCheckboxMap)
                msg_key_search.setValue(StringUtils.changeList2String(KeySearchBean.selectKeySearchList))
            }
        }).show()
    }
}
