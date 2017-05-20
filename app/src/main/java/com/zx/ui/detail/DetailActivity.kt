package com.zx.ui.detail

import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.BindViews
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.zx.R
import com.zx.bean.CardBean
import com.zx.game.utils.CardUtils
import com.zx.ui.base.BaseActivity
import com.zx.view.banner.BannerImageLoader

/**
 * Created by 八神火焰 on 2016/12/13.
 */

class DetailActivity : BaseActivity() {

    @BindView(R.id.banner)
    internal var banner: Banner? = null
    @BindViews(R.id.img_camp0, R.id.img_camp1, R.id.img_camp2, R.id.img_camp3, R.id.img_camp4)
    internal var imgCampList: Array<ImageView>? = null
    @BindView(R.id.tv_number)
    internal var tvNumber: TextView? = null
    @BindView(R.id.tv_rare)
    internal var tvRare: TextView? = null
    @BindView(R.id.tv_race)
    internal var tvRace: TextView? = null
    @BindView(R.id.tv_type)
    internal var tvType: TextView? = null
    @BindView(R.id.tv_cname)
    internal var tvCname: TextView? = null
    @BindView(R.id.tv_jname)
    internal var tvJname: TextView? = null
    @BindView(R.id.tv_power)
    internal var tvPower: TextView? = null
    @BindView(R.id.tv_cost)
    internal var tvCost: TextView? = null
    @BindView(R.id.img_sign)
    internal var imgSign: ImageView? = null
    @BindView(R.id.tv_ability)
    internal var tvAbility: TextView? = null
    @BindView(R.id.tv_lines)
    internal var tvLines: TextView? = null
    @BindView(R.id.tv_faq)
    internal var tvFaq: TextView? = null

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun initViewAndData() {
        ButterKnife.bind(this)
        setCardBean()
    }

    override fun onNavigationClick() {
        onBackPressed()
    }

    private fun setCardBean() {
        tvNumber?.text = cardBean?.number
        tvRace?.text = cardBean?.race
        tvType?.text = cardBean?.type
        tvCname?.text = cardBean?.cName
        tvJname?.text = cardBean?.jName
        tvPower?.text = cardBean?.power
        tvCost?.text = cardBean?.cost
        tvAbility?.text = cardBean?.ability
        tvLines?.text = cardBean?.lines
        tvFaq?.text = cardBean?.faq
        tvRare?.text = cardBean?.rare
        //        int rareResId = CardUtils.getRareResIdList(cardBean.getRare());
        //        if (-1 == rareResId) {
        //            imgSign.setImageBitmap(null);
        //        } else {
        //            Glide.with(this).load(rareResId).error(null).into(imgRare);
        //        }

        banner?.setBannerStyle(BannerConfig.NUM_INDICATOR)
        banner?.setImageLoader(BannerImageLoader())
        banner?.setImages(CardUtils.getImagePathList(cardBean?.image!!))
        banner?.start()

        val campResIdList = CardUtils.getCampResIdList(cardBean?.camp!!)
        val signResId = CardUtils.getSignResId(cardBean?.sign!!)
        if (-1 == signResId) {
            imgSign?.setImageBitmap(null)
        } else {
            Glide.with(this).load(signResId).error(null).into(imgSign!!)
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
