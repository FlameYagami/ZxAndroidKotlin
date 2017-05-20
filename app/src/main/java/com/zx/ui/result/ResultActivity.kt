package com.zx.ui.result

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import com.zx.R
import com.zx.bean.CardBean
import com.zx.ui.base.BaseActivity

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class ResultActivity : BaseActivity() {
    @BindView(R.id.rv_result)
    internal var rvResult: RecyclerView? = null
    @BindString(R.string.result_about_title)
    internal var title: String? = null

    internal var resultAdapter: ResultAdapter = null!!

    override val layoutId: Int
        get() = R.layout.activity_result

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar?.setTitleText(title!! + String.format("：%s", cardBeanList?.size))
        resultAdapter = ResultAdapter(this)
        rvResult?.layoutManager = LinearLayoutManager(this)
        rvResult?.adapter = resultAdapter
        resultAdapter.updateData(cardBeanList as List<CardBean>)


//        resultAdapter.setOnItemClickListener { view, data, position ->
//            DetailActivity.cardBean = cardBeanList!![position]
//            IntentUtils.gotoActivity(this, DetailActivity::class.java)
//        }
    }

    override fun onNavigationClick() {
        onBackPressed()
    }

    companion object {
        var cardBeanList: List<CardBean>? = null
    }
}
