package com.zx.ui.advancedsearch

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import butterknife.BindArray
import butterknife.ButterKnife
import butterknife.OnClick
import com.zx.R
import com.zx.bean.AbilityDetailBean
import com.zx.bean.AbilityTypeBean
import com.zx.bean.CardBean
import com.zx.event.CardListEvent
import com.zx.game.utils.CardUtils
import com.zx.game.utils.RestrictUtils
import com.zx.ui.base.BaseActivity
import com.zx.ui.deckeditor.DeckEditorActivity
import com.zx.ui.main.MainActivity
import com.zx.ui.result.ResultActivity
import com.zx.uitls.IntentUtils
import com.zx.uitls.RxBus
import com.zx.uitls.database.SQLiteUtils
import com.zx.uitls.database.SqlUtils
import com.zx.view.dialog.DialogCheckBox
import com.zx.view.widget.AppBarView
import kotlinx.android.synthetic.main.activity_advanced_search.*
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class AdvancedSearchActivity : BaseActivity() {
    @BindArray(R.array.camp)
    internal var campArray: Array<String>? = null

    private var fromActivity: String? = null

    override val layoutId: Int
        get() = R.layout.activity_advanced_search

    override fun initViewAndData() {
        ButterKnife.bind(this)
        viewAppBar.setNavigationClickListener(object : AppBarView.NavigationClickListener {
            override fun onNavigationClick() {
                onBackPressed()
            }
        })
        fromActivity = intent.extras.getString(Activity::class.java.simpleName, "")
        cmb_illust.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CardUtils.illust)
        cmb_pack.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CardUtils.allPack)
        cmb_camp.onItemSelectedListener = onCampItemSelectedListener
        AbilityTypeBean.initAbilityTypeMap()
        AbilityDetailBean.initAbilityDetailMap()
    }

    @OnClick(R.id.btn_ability_type)
    fun onAbilityType_Click() {
        DialogCheckBox(this, "基础分类", AbilityTypeBean.abilityTypeMap, object : DialogCheckBox.OnClickListener {
            override fun getCheckBoxMap(mCheckboxMap: LinkedHashMap<String, Boolean>) {
                AbilityTypeBean.abilityTypeMap = mCheckboxMap
            }
        }).show()
    }

    @OnClick(R.id.btn_ability_detail)
    fun onAbilityDetail_Click() {
        DialogCheckBox(this, "扩展分类", AbilityDetailBean.abilityDetailMap, object : DialogCheckBox.OnClickListener {
            override fun getCheckBoxMap(mCheckboxMap: LinkedHashMap<String, Boolean>) {
                AbilityDetailBean.abilityDetailMap = mCheckboxMap
            }
        }).show()
    }

    @OnClick(R.id.fab_search)
    fun onSearch_Click() {
        val cardBean = cardBean
        val querySql = SqlUtils.getQuerySql(cardBean)
        var cardList = SQLiteUtils.getCardList(querySql)
        cardList = RestrictUtils.getRestrictCardList(cardList, cardBean)
        if (cardList.isEmpty()) {
            showToast("没有查询到相关卡牌")
        } else {
            if (fromActivity == DeckEditorActivity::class.java.simpleName) {
                RxBus.instance.post(CardListEvent(cardList))
                super.onBackPressed()
            } else if (fromActivity == MainActivity::class.java.simpleName) {
                ResultActivity.cardBeanList = cardList.toMutableList()
                IntentUtils.gotoActivity(this, ResultActivity::class.java)
            }
        }
    }

    val cardBean: CardBean
        get() {
            val Type = cmb_type.selectedItem.toString()
            val Camp = cmb_camp.selectedItem.toString()
            val Race = cmb_race.selectedItem.toString()
            val Sign = cmb_sign.selectedItem.toString()
            val Rare = cmb_rare.selectedItem.toString()
            val Pack = cmb_pack.selectedItem.toString()
            val Illust = cmb_illust.selectedItem.toString()
            val Restrict = cmb_restrict.selectedItem.toString()
            val Key = txt_key.text.toString().trim { it <= ' ' }
            val Cost = txt_cost.text.toString().trim { it <= ' ' }
            val Power = txt_power.text.toString().trim { it <= ' ' }
            val AbilityType = SqlUtils.getAbilityTypeSql(AbilityTypeBean.abilityTypeMap)
            val AbilityDetail = SqlUtils.getAbilityDetailSql(AbilityDetailBean.abilityDetailMap)
            return CardBean(Key, Type, Camp, Race, Sign, Rare, Pack, Illust, Restrict, Cost, Power, AbilityType, AbilityDetail)
        }

    private val onCampItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            cmb_race.adapter = ArrayAdapter(this@AdvancedSearchActivity, android.R.layout.simple_spinner_item, CardUtils.getPartRace(campArray!![position]))
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }

    @OnClick(R.id.fab_reset)
    fun onReset_Click() {
        cmb_type.setSelection(0)
        cmb_camp.setSelection(0)
        cmb_race.setSelection(0)
        cmb_sign.setSelection(0)
        cmb_rare.setSelection(0)
        cmb_pack.setSelection(0)
        cmb_illust.setSelection(0)
        cmb_restrict.setSelection(0)
        txt_key.setText("")
        txt_cost.setText("")
        txt_power.setText("")
        AbilityTypeBean.initAbilityTypeMap()
        AbilityDetailBean.initAbilityDetailMap()
    }
}
