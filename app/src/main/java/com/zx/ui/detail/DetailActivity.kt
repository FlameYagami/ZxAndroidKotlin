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
        mImgCampList = arrayOf(img_camp0, img_camp1, img_camp2, img_camp3, img_camp4)
        viewAppBar.setNavigationClickListener { onBackPressed() }
        setCardBean()
    }

    private fun setCardBean() {
        tv_number.text = cardBean?.number
        tv_race.text = cardBean?.race
        tv_type.text = cardBean?.type
        tv_cname.text = cardBean?.cName
        tv_jname.text = cardBean?.jName
        tv_power.text = cardBean?.power
        tv_cost.text = cardBean?.cost
        tv_ability.text = cardBean?.ability
        tv_lines.text = cardBean?.lines
        tv_faq.text = cardBean?.faq
        tv_rare.text = cardBean?.rare
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        banner.setImageLoader(BannerImageLoader())
        banner.setImages(CardUtils.getImagePathList(cardBean?.image!!))
        banner.start()

        val campResIdList = CardUtils.getCampResIdList(cardBean?.camp!!)
        val signResId = CardUtils.getSignResId(cardBean?.sign!!)
        if (-1 == signResId) {
            img_sign.setImageBitmap(null)
        } else {
            Glide.with(this).load(signResId).error(null).into(img_sign)
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
