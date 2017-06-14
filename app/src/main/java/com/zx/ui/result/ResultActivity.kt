package com.zx.ui.result

import android.support.v7.widget.LinearLayoutManager
import com.zx.R
import com.zx.bean.CardBean
import com.zx.ui.base.BaseActivity
import com.zx.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.startActivity

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class ResultActivity : BaseActivity() {
    companion object {
        var cardBeanList: MutableList<CardBean>? = null
    }

    override val layoutId: Int
        get() = R.layout.activity_result

    override fun initViewAndData() {
        viewAppBar.setNavigationClickListener { onBackPressed() }
        viewAppBar.setTitleText(getString(R.string.result_about_title) + String.format("：%s", cardBeanList?.size))
        val resultAdapter = ResultAdapter(this)
        rvResult.layoutManager = LinearLayoutManager(this)
        rvResult.adapter = resultAdapter
        resultAdapter.updateData(cardBeanList as List<CardBean>)
        resultAdapter.setOnItemClickListener { _, _, position ->
            DetailActivity.cardBean = cardBeanList!![position]
            startActivity<DetailActivity>()
        }
    }
}
