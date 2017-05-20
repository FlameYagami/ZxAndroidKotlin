package com.zx.ui.advanced

import android.support.v7.app.AlertDialog
import butterknife.BindArray
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R
import com.zx.bean.KeySearchBean
import com.zx.config.SpConst
import com.zx.ui.base.BaseActivity
import com.zx.uitls.StringUtils
import com.zx.view.dialog.DialogCheckBox
import com.zx.view.widget.MessageView
import java.util.*

/**
 * Created by 八神火焰 on 2017/1/5.
 */

class AdvancedActivity : BaseActivity() {
    @BindArray(R.array.order)
    internal var mOrderPatternArrays: Array<String>? = null
    @BindView(R.id.msg_preview_order)
    internal var viewOrderPattern: MessageView? = null
    @BindView(R.id.msg_key_search)
    internal var viewKeySearch: MessageView? = null

    override val layoutId: Int
        get() = R.layout.activity_advanced

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewOrderPattern?.setDefaultSp(SpConst.OrderPattern, mOrderPatternArrays!![0])
        viewKeySearch?.setValue(StringUtils.changeList2String(KeySearchBean.selectKeySearchList))
    }

    override fun onNavigationClick() {
        onBackPressed()
    }

    @OnClick(R.id.msg_preview_order)
    fun onOrderPattern_Click() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.preview_order))
                .setItems(mOrderPatternArrays) { _, index -> viewOrderPattern?.setDefaultSp(SpConst.OrderPattern, mOrderPatternArrays!![index]) }
                .show()
    }

    @OnClick(R.id.msg_key_search)
    fun onKeySearch_Click() {
        DialogCheckBox(this, getString(R.string.key_search), KeySearchBean.getKeySearchMap(), object : DialogCheckBox.OnClickListener {
            override fun getCheckBoxMap(mCheckboxMap: LinkedHashMap<String, Boolean>) {
                KeySearchBean.saveKeySearchMap(mCheckboxMap)
                viewKeySearch?.setValue(StringUtils.changeList2String(KeySearchBean.selectKeySearchList))
            }
        }).show()
    }
}
