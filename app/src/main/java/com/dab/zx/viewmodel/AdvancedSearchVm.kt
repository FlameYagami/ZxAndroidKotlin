package com.dab.zx.viewmodel

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.dab.zx.R
import com.dab.zx.bean.AbilityDetailBean
import com.dab.zx.bean.AbilityTypeBean
import com.dab.zx.bean.CardBean
import com.dab.zx.game.utils.CardUtils
import com.dab.zx.game.utils.RestrictUtils
import com.dab.zx.model.AdvancedSearchModel
import com.dab.zx.uc.dialog.DialogCheckBox
import com.dab.zx.uitls.database.SQLiteUtils
import com.dab.zx.uitls.database.SqlUtils
import com.dab.zx.view.search.AdvancedSearchActivity

/**
 * Created by 八神火焰 on 2018/1/18.
 */
class AdvancedSearchVm(private var activity: Activity) {

    lateinit var searchModel: AdvancedSearchModel
    var illustAdapter: ArrayAdapter<String>  = ArrayAdapter(activity, android.R.layout.simple_spinner_item, CardUtils.illust)
    var packAdapter: ArrayAdapter<String>  = ArrayAdapter(activity, android.R.layout.simple_spinner_item, CardUtils.allPack)

    var mCampArray: Array<String> = activity.resources.getStringArray(R.array.camp)
//    var fromActivity: String = activity.intent.extras.getString(Activity::class.java.simpleName, "")

    init {
        AbilityTypeBean.initAbilityTypeMap()
        AbilityDetailBean.initAbilityDetailMap()
//        cmbCamp.onItemSelectedListener = onCampItemSelectedListener
    }

    var onReset = View.OnClickListener {
        searchModel.type.set("")
        searchModel.camp.set("")
        searchModel.race.set("")
        searchModel.sign.set("")
        searchModel.rare.set("")
        searchModel.pack.set("")
        searchModel.illust.set("")
        searchModel.restrict.set("")
        searchModel.key.set("")
        searchModel.cost.set("")
        searchModel.power.set("")
        AbilityTypeBean.initAbilityTypeMap()
        AbilityDetailBean.initAbilityDetailMap()
    }

    var onSearch = View.OnClickListener {
        val cardBean = cardBean
        val querySql = SqlUtils.getQuerySql(cardBean)
        var cardList = SQLiteUtils.getCardList(querySql)
        cardList = RestrictUtils.getRestrictCardList(cardList, cardBean)
        if (cardList.isEmpty()) {
            (activity as AdvancedSearchActivity).showToast("没有查询到相关卡牌")
        } else {
//            if (fromActivity == DeckEditorActivity::class.java.simpleName) {
//                RxBus.get().send(CardListEvent(cardList))
//                (activity as AdvancedSearchActivity).onBackPressed()
//            } else if (fromActivity == MainActivity::class.java.simpleName) {
//                CardPreviewActivity.cardModels = cardModels
//                activity.startActivity<CardPreviewActivity>()
//            }
        }
    }

    var onAbilityType = View.OnClickListener {
        DialogCheckBox(activity, "基础分类", AbilityTypeBean.abilityTypeMap, { AbilityTypeBean.abilityTypeMap = it }).show()
    }

    var onAbilityDetail = View.OnClickListener {
        DialogCheckBox(activity, "扩展分类", AbilityDetailBean.abilityDetailMap, { AbilityDetailBean.abilityDetailMap = it }).show()
    }

    private val onCampItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            cmbRace.adapter = ArrayAdapter(this@AdvancedSearchActivity, android.R.layout.simple_spinner_item, CardUtils.getPartRace(mCampArray?.get(position) as String))
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }

    private val cardBean: CardBean
        get() {
            val type = searchModel.type.get()
            val camp = searchModel.camp.get()
            val race = searchModel.race.get()
            val sign = searchModel.sign.get()
            val rare = searchModel.rare.get()
            val pack = searchModel.pack.get()
            val illust = searchModel.illust.get()
            val restrict = searchModel.restrict.get()
            val key = searchModel.key.get()
            val cost = searchModel.cost.get()
            val power = searchModel.power.get()
            val abilityType = SqlUtils.getAbilityTypeSql(AbilityTypeBean.abilityTypeMap)
            val abilityDetail = SqlUtils.getAbilityDetailSql(AbilityDetailBean.abilityDetailMap)
            return CardBean(key, type, camp, race, sign, rare, pack, illust, restrict, cost, power, abilityType, abilityDetail)
        }

}