package com.zx.ui.versus

import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.zx.R
import com.zx.bean.HandBean
import com.zx.ui.base.BaseExActivity
import java.util.*

/**
 * Created by 八神火焰 on 2017/1/13.
 */

class VersusActivity : BaseExActivity() {

    @BindView(R.id.rv_red_hand)
    internal var rvRedHand: RecyclerView? = null

    private val mGreenHandAdapter = HandAdapter(this)
    private val mGreenResourceAdapter = ResourceAdapter(this)
    private val mGreenHandBean = ArrayList<HandBean>()

    override val layoutId: Int
        get() = R.layout.activity_duel

    override fun initViewAndData() {
        ButterKnife.bind(this)

//        val numberExList = DeckUtils.deckPreviewList.get(0).numberExList
//        mGreenHandBean.addAll(stream<String>(numberExList).select({ numberEx -> HandBean(DuelBean(numberEx, pictureCache + File.separator + numberEx + getString(R.string.image_extension))) }).toList())
//        rvRedHand?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        rvRedHand?.adapter = mGreenHandAdapter
//        mGreenHandAdapter.setOnItemClickListener { view, data, position -> MyPopup(this, R.layout.popupwindow_hand) }
//        mGreenHandAdapter.updateData(mGreenHandBean)
    }

    override fun onNavigationClick() {
        onBackPressed()
    }
}
