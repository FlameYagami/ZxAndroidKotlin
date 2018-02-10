package com.dab.zx.viewmodel

import android.app.Activity
import android.databinding.ObservableField
import android.view.View
import com.dab.zx.R
import com.dab.zx.config.MapConst
import com.dab.zx.game.utils.RestrictUtils
import com.dab.zx.model.BannerModel
import com.dab.zx.uitls.DisplayUtils
import com.dab.zx.uitls.database.SQLiteUtils
import com.dab.zx.uitls.database.SqlUtils
import com.dab.zx.view.deck.DeckPreviewActivity
import com.dab.zx.view.main.MainActivity
import com.dab.zx.view.search.AdvancedSearchActivity
import com.dab.zx.view.search.CardPreviewActivity
import com.dab.zx.view.setting.SettingActivity
import com.youth.banner.listener.OnBannerClickListener
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity

/**
 * Created by 八神火焰 on 2017/12/22.
 */
class MainVm(private var activity: Activity) {

    val key: ObservableField<String> = ObservableField("")
    var bannerModel: BannerModel = BannerModel()

    init {
        bannerModel.enableScale = true
        bannerModel.images = MapConst.GuideMap.entries.map { it.value }
        Observable.just(this).observeOn(Schedulers.io()).subscribe {
            RestrictUtils.getRestrictList()
            SQLiteUtils.getAllCardList()
        }
    }

    /**
     * 卡包查询
     */
    var onPackClick = OnBannerClickListener { position ->
        val pack = MapConst.GuideMap.entries.map { it.key }[position - 1]
        val querySql = SqlUtils.getPackQuerySql(pack)
        val cardModels = SQLiteUtils.getCardList(querySql).toMutableList()
        CardPreviewActivity.cardModels = cardModels
        activity.startActivity<CardPreviewActivity>()
    }

    /**
     * 关键字搜索
     */
    var onSearch = View.OnClickListener { view: View ->
        DisplayUtils.hideKeyboard(activity)
        val querySql = SqlUtils.getKeyQuerySql(key.get())
        val cardModels = SQLiteUtils.getCardList(querySql)
        if (cardModels.isEmpty()) {
            (activity as MainActivity).showSnackBar(view.context.getString(R.string.card_not_found))
        } else {
            CardPreviewActivity.cardModels = cardModels
            view.context.startActivity<CardPreviewActivity>()
        }
    }

    /**
     * 高级查询
     */
    var onAdvancedSearch = View.OnClickListener {
        it.context.startActivity<AdvancedSearchActivity>(Activity::class.java.simpleName to MainActivity::class.java.simpleName)
    }

    /**
     * 卡组预览
     */
    var onDeckPreview = View.OnClickListener {
        it.context.startActivity<DeckPreviewActivity>()
    }

    /**
     * 设置
     */
    var onSetting = View.OnClickListener {
        it.context.startActivity<SettingActivity>()
    }
}