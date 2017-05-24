package com.zx.ui.result

import android.support.v7.widget.LinearLayoutManager
import com.zx.R
import com.zx.bean.CardBean
import com.zx.ui.base.BaseActivity
import com.zx.ui.detail.DetailActivity
import com.zx.uitls.IntentUtils
import kotlinx.android.synthetic.main.activity_result.*

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class ResultActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_result

    override fun initViewAndData() {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        viewAppBar.setTitleText(getString(R.string.result_about_title) + String.format("：%s", cardBeanList?.size))
        val resultAdapter = ResultAdapter(this)
        rv_result.layoutManager = LinearLayoutManager(this)
        rv_result.adapter = resultAdapter
        resultAdapter.updateData(cardBeanList as List<CardBean>)
        resultAdapter.setOnItemClickListener { _, _, position ->
            DetailActivity.cardBean = cardBeanList!![position]
            IntentUtils.gotoActivity(this@ResultActivity, DetailActivity::class.java)
        }
    }

    companion object {
        var cardBeanList: MutableList<CardBean>? = null
    }
}
