package com.zx.ui.result

import android.support.v7.widget.LinearLayoutManager
import butterknife.BindString
import butterknife.ButterKnife
import com.zx.R
import com.zx.bean.CardBean
import com.zx.ui.base.BaseActivity
import com.zx.ui.detail.DetailActivity
import com.zx.uitls.IntentUtils
import com.zx.view.widget.AppBarView
import kotlinx.android.synthetic.main.activity_result.*

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class ResultActivity : BaseActivity() {
    @BindString(R.string.result_about_title)
    internal var title: String? = null

    override val layoutId: Int
        get() = R.layout.activity_result

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar.setNavigationClickListener(object : AppBarView.NavigationClickListener {
            override fun onNavigationClick() {
                onBackPressed()
            }
        })
        viewAppBar.setTitleText(title + String.format("：%s", cardBeanList?.size))
        val resultAdapter = ResultAdapter(this)
        rv_result.layoutManager = LinearLayoutManager(this)
        rv_result.adapter = resultAdapter
        resultAdapter.updateData(cardBeanList as List<CardBean>)
        resultAdapter.setOnItemClickListener { view, data, position ->
            DetailActivity.cardBean = cardBeanList!![position]
            IntentUtils.gotoActivity(this@ResultActivity, DetailActivity::class.java)
        }
    }

    companion object {
        var cardBeanList: MutableList<CardBean>? = null
    }
}
