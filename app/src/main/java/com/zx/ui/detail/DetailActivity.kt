package com.zx.ui.detail

import android.widget.ImageView
import butterknife.BindViews
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.youth.banner.BannerConfig
import com.zx.R
import com.zx.bean.CardBean
import com.zx.game.utils.CardUtils
import com.zx.ui.base.BaseActivity
import com.zx.view.banner.BannerImageLoader
import com.zx.view.widget.AppBarView
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by 八神火焰 on 2016/12/13.
 */

class DetailActivity : BaseActivity() {

    @BindViews(R.id.img_camp0, R.id.img_camp1, R.id.img_camp2, R.id.img_camp3, R.id.img_camp4)
    internal var imgCampList: Array<ImageView>? = null

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar.setNavigationClickListener(object : AppBarView.NavigationClickListener {
            override fun onNavigationClick() {
                onBackPressed()
            }
        })
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
        //        int rareResId = CardUtils.getRareResIdList(cardBean.getRare());
        //        if (-1 == rareResId) {
        //            imgSign.setImageBitmap(null);
        //        } else {
        //            Glide.with(this).load(rareResId).error(null).into(imgRare);
        //        }

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
                imgCampList!![i].setImageBitmap(null)
            } else {
                Glide.with(this).load(campResIdList[i]).error(null).into(imgCampList!![i])
            }
        }
    }

    companion object {

        var cardBean: CardBean? = null
    }
}
