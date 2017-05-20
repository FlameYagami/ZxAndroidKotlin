package com.zx.ui.advancedsearch

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import butterknife.BindArray
import butterknife.BindView
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
import java.util.*

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class AdvancedSearchActivity : BaseActivity() {
    @BindView(R.id.txt_key)
    internal var txtKey: EditText? = null
    @BindView(R.id.txt_cost)
    internal var txtCost: EditText? = null
    @BindView(R.id.txt_power)
    internal var txtPower: EditText? = null
    @BindView(R.id.cmb_pack)
    internal var cmbPack: Spinner? = null
    @BindView(R.id.cmb_illust)
    internal var cmbIllust: Spinner? = null
    @BindView(R.id.cmb_type)
    internal var cmbType: Spinner? = null
    @BindView(R.id.cmb_camp)
    internal var cmbCamp: Spinner? = null
    @BindView(R.id.cmb_race)
    internal var cmbRace: Spinner? = null
    @BindView(R.id.cmb_sign)
    internal var cmbSign: Spinner? = null
    @BindView(R.id.cmb_rare)
    internal var cmbRare: Spinner? = null
    @BindView(R.id.cmb_restrict)
    internal var cmbRestrict: Spinner? = null
    @BindArray(R.array.camp)
    internal var campArray: Array<String>? = null

    private var fromActivity: String? = null

    override val layoutId: Int
        get() = R.layout.activity_advanced_search

    override fun initViewAndData() {
        ButterKnife.bind(this)
        fromActivity = intent.extras.getString(Activity::class.java.simpleName, "")
        cmbIllust?.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CardUtils.illust)
        cmbPack?.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CardUtils.allPack)
        cmbCamp?.onItemSelectedListener = onCampItemSelectedListener
        AbilityTypeBean.initAbilityTypeMap()
        AbilityDetailBean.initAbilityDetailMap()
    }

    override fun onNavigationClick() {
        onBackPressed()
    }

    @OnClick(R.id.btn_ability_type)
    fun onAbilityType_Click() {
        DialogCheckBox(this, "基础分类", AbilityTypeBean.abilityTypeMap, object :DialogCheckBox.OnClickListener {
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
                ResultActivity.cardBeanList = cardList
                IntentUtils.gotoActivity(this, ResultActivity::class.java)
            }
        }
    }

    val cardBean: CardBean
        get() {
            val Type = cmbType?.selectedItem.toString()
            val Camp = cmbCamp?.selectedItem.toString()
            val Race = cmbRace?.selectedItem.toString()
            val Sign = cmbSign?.selectedItem.toString()
            val Rare = cmbRare?.selectedItem.toString()
            val Pack = cmbPack?.selectedItem.toString()
            val Illust = cmbIllust?.selectedItem.toString()
            val Restrict = cmbRestrict?.selectedItem.toString()
            val Key = txtKey?.text.toString().trim { it <= ' ' }
            val Cost = txtCost?.text.toString().trim { it <= ' ' }
            val Power = txtPower?.text.toString().trim { it <= ' ' }
            val AbilityType = SqlUtils.getAbilityTypeSql(AbilityTypeBean.abilityTypeMap)
            val AbilityDetail = SqlUtils.getAbilityDetailSql(AbilityDetailBean.abilityDetailMap)
            return CardBean(Key, Type, Camp, Race, Sign, Rare, Pack, Illust, Restrict, Cost, Power, AbilityType, AbilityDetail)
        }

    private val onCampItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            cmbRace?.adapter = ArrayAdapter(this@AdvancedSearchActivity, android.R.layout.simple_spinner_item, CardUtils.getPartRace(campArray!![position]))
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }

    @OnClick(R.id.fab_reset)
    fun onReset_Click() {
        cmbType?.setSelection(0)
        cmbCamp?.setSelection(0)
        cmbRace?.setSelection(0)
        cmbSign?.setSelection(0)
        cmbRare?.setSelection(0)
        cmbPack?.setSelection(0)
        cmbIllust?.setSelection(0)
        cmbRestrict?.setSelection(0)
        txtKey?.setText("")
        txtCost?.setText("")
        txtPower?.setText("")
        AbilityTypeBean.initAbilityTypeMap()
        AbilityDetailBean.initAbilityDetailMap()
    }
}
