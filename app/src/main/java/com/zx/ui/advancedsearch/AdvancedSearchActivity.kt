package com.zx.ui.advancedsearch

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.michaelflisar.rxbus2.RxBus
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
import com.zx.uitls.database.SQLiteUtils
import com.zx.uitls.database.SqlUtils
import com.zx.view.dialog.DialogCheckBox
import kotlinx.android.synthetic.main.activity_advanced_search.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class AdvancedSearchActivity : BaseActivity() {

    companion object {
        var mCampArray: Array<String>? = null
    }

    private var fromActivity: String? = null

    override val layoutId: Int
        get() = R.layout.activity_advanced_search

    override fun initViewAndData() {
        mCampArray = resources.getStringArray(R.array.camp)
        viewAppBar.setNavigationClickListener { onBackPressed() }
        fromActivity = intent.extras.getString(Activity::class.java.simpleName, "")
        cmbIllust.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CardUtils.illust)
        cmbPack.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CardUtils.allPack)
        cmbCamp.onItemSelectedListener = onCampItemSelectedListener
        btnAbilityType.onClick { onAbilityType_Click() }
        btnAbilityDetail.onClick { onAbilityDetail_Click() }
        fabSearch.onClick { onSearch_Click() }
        fabReset.onClick { onReset_Click() }
        AbilityTypeBean.initAbilityTypeMap()
        AbilityDetailBean.initAbilityDetailMap()
    }

    fun onAbilityType_Click() {
        DialogCheckBox(this, "基础分类", AbilityTypeBean.abilityTypeMap, { AbilityTypeBean.abilityTypeMap = it }).show()
    }

    fun onAbilityDetail_Click() {
        DialogCheckBox(this, "扩展分类", AbilityDetailBean.abilityDetailMap, { AbilityDetailBean.abilityDetailMap = it }).show()
    }

    fun onSearch_Click() {
        val cardBean = cardBean
        val querySql = SqlUtils.getQuerySql(cardBean)
        var cardList = SQLiteUtils.getCardList(querySql)
        cardList = RestrictUtils.getRestrictCardList(cardList, cardBean)
        if (cardList.isEmpty()) {
            showToast("没有查询到相关卡牌")
        } else {
            if (fromActivity == DeckEditorActivity::class.java.simpleName) {
                RxBus.get().send(CardListEvent(cardList))
                super.onBackPressed()
            } else if (fromActivity == MainActivity::class.java.simpleName) {
                ResultActivity.cardBeanList = cardList.toMutableList()
                startActivity<ResultActivity>()
            }
        }
    }

    val cardBean: CardBean
        get() {
            val Type = cmbType.selectedItem.toString()
            val Camp = cmbCamp.selectedItem.toString()
            val Race = cmbRace.selectedItem.toString()
            val Sign = cmbSign.selectedItem.toString()
            val Rare = cmbRare.selectedItem.toString()
            val Pack = cmbPack.selectedItem.toString()
            val Illust = cmbIllust.selectedItem.toString()
            val Restrict = cmbRestrict.selectedItem.toString()
            val Key = txtKey.text.toString().trim()
            val Cost = txtCost.text.toString().trim()
            val Power = txtPower.text.toString().trim()
            val AbilityType = SqlUtils.getAbilityTypeSql(AbilityTypeBean.abilityTypeMap)
            val AbilityDetail = SqlUtils.getAbilityDetailSql(AbilityDetailBean.abilityDetailMap)
            return CardBean(Key, Type, Camp, Race, Sign, Rare, Pack, Illust, Restrict, Cost, Power, AbilityType, AbilityDetail)
        }

    private val onCampItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            cmbRace.adapter = ArrayAdapter(this@AdvancedSearchActivity, android.R.layout.simple_spinner_item, CardUtils.getPartRace(mCampArray?.get(position) as String))
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }

    fun onReset_Click() {
        cmbType.setSelection(0)
        cmbCamp.setSelection(0)
        cmbRace.setSelection(0)
        cmbSign.setSelection(0)
        cmbRare.setSelection(0)
        cmbPack.setSelection(0)
        cmbIllust.setSelection(0)
        cmbRestrict.setSelection(0)
        txtKey.setText("")
        txtCost.setText("")
        txtPower.setText("")
        AbilityTypeBean.initAbilityTypeMap()
        AbilityDetailBean.initAbilityDetailMap()
    }
}
