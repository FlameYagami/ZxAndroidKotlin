package com.dab.zx.viewmodel

import android.app.Activity
import android.databinding.ObservableField
import android.view.View
import com.dab.zx.bean.CardBean
import com.dab.zx.config.MapConst
import com.dab.zx.game.utils.RestrictUtils
import com.dab.zx.uitls.DisplayUtils
import com.dab.zx.uitls.database.SQLiteUtils
import com.dab.zx.uitls.database.SqlUtils
import com.dab.zx.view.deck.DeckPreviewActivity
import com.dab.zx.view.main.MainActivity
import com.dab.zx.view.search.AdvancedSearchActivity
import com.dab.zx.view.search.CardPreviewActivity
import com.dab.zx.view.setting.SettingActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity

/**
 * Created by 八神火焰 on 2017/12/22.
 */
class MainVm {

    companion object {
        private var TAG: String = MainVm::class.java.simpleName
    }

    val key: ObservableField<String> = ObservableField()

    init {
        Observable.just(this).observeOn(Schedulers.io()).subscribe {
            RestrictUtils.getRestrictList()
            SQLiteUtils.getAllCardList()
        }
    }

    /**
     * 关键字搜索
     */
    var onSearch = View.OnClickListener { view: View ->
        DisplayUtils.hideKeyboard(view.context)
        val querySql = SqlUtils.getKeyQuerySql(key.get())
        val cardList = SQLiteUtils.getCardList(querySql)
        if (cardList.isEmpty()) {
//            mainActivity.showSnackBar(mainActivity.getString(R.string.card_not_found))
        } else {
            view.context.startActivity<CardPreviewActivity>(CardBean::class.java.simpleName to cardList)
        }
    }

    /**
     * 卡包查询
     */
    fun onPackQueryClick(position: Int, view: View) {
        val pack = MapConst.GuideMap.entries.map { it.key }[position - 1]
        val querySql = SqlUtils.getPackQuerySql(pack)
        val cardList = SQLiteUtils.getCardList(querySql).toMutableList()
        view.context.startActivity<CardPreviewActivity>(CardBean::class.java.simpleName to cardList)
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