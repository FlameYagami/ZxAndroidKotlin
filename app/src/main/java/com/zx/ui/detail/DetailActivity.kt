package com.zx.ui.detail

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.BannerConfig
import com.zx.R
import com.zx.bean.CardBean
import com.zx.game.utils.CardUtils
import com.zx.ui.base.BaseActivity
import com.zx.view.banner.BannerImageLoader
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by 八神火焰 on 2016/12/13.
 */

class DetailActivity : BaseActivity() {

    companion object {
        internal var mImgCampList: Array<ImageView>? = null
        var cardBean: CardBean? = null
    }

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun initViewAndData() {
        mImgCampList = arrayOf(imgCamp0, imgCamp1, imgCamp2, imgCamp3, imgCamp4)
        viewAppBar.setNavigationClickListener { onBackPressed() }
        setCardBean()
    }

    private fun setCardBean() {
        tvNumber.text = cardBean?.number
        tvRace.text = cardBean?.race
        tvType.text = cardBean?.type
        tvCname.text = cardBean?.cName
        tvJname.text = cardBean?.jName
        tvPower.text = cardBean?.power
        tvCost.text = cardBean?.cost
        tvAbility.text = cardBean?.ability
        tvLines.text = cardBean?.lines
        tvFaq.text = cardBean?.faq
        tvRare.text = cardBean?.rare
        bannerPack.setBannerStyle(BannerConfig.NUM_INDICATOR)
        bannerPack.setImageLoader(BannerImageLoader())
        bannerPack.setImages(CardUtils.getImagePathList(cardBean?.image!!))
        bannerPack.start()

        val campResIdList = CardUtils.getCampResIdList(cardBean?.camp!!)
        val signResId = CardUtils.getSignResId(cardBean?.sign!!)
        if (-1 == signResId) {
            imgSign.setImageBitmap(null)
        } else {
            Glide.with(this).load(signResId).error(null).into(imgSign)
        }
        for (i in campResIdList.indices) {
            if (-1 == campResIdList[i]) {
                mImgCampList!![i].setImageBitmap(null)
            } else {
                Glide.with(this).load(campResIdList[i]).error(null).into(mImgCampList!![i])
            }
        }
    }
}
