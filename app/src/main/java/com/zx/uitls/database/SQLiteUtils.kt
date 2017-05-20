package com.zx.uitls.database

import android.text.TextUtils
import com.zx.bean.CardBean
import com.zx.config.SQLitConst
import com.zx.game.utils.RestrictUtils
import java.util.*

/**
 * Created by Administrator on 2016/6/30.
 */
object SQLiteUtils : SQLitConst {
    private val TAG = SQLiteUtils::class.java.simpleName

    private var allCardList: List<CardBean> = ArrayList()

    /**
     * 返回设备数据集合

     * @return DeviceList
     */
    fun getAllCardList(): List<CardBean> {
        if (allCardList.isEmpty()) {
            allCardList = getCardList(SqlUtils.queryAllSql)
        }
        return allCardList
    }

    /**
     * 返回设备数据集合

     * @return DeviceList
     */
    fun getCardList(sql: String): List<CardBean> {
        val helper = DBManager.getInstance().getSQLiteHelper()
        val deviceList = ArrayList<CardBean>()
        val result = helper.getCursor(DBManager.getInstance().openDatabase(), sql)
        while (result.moveToNext()) {
            val Md5 = result.getString(result.getColumnIndex(SQLitConst.ColumnMd5))
            val Type = result.getString(result.getColumnIndex(SQLitConst.ColumnType))
            val Camp = result.getString(result.getColumnIndex(SQLitConst.ColumnCamp))
            val Race = result.getString(result.getColumnIndex(SQLitConst.ColumnRace))
            val Sign = result.getString(result.getColumnIndex(SQLitConst.ColumnSign))
            val Rare = result.getString(result.getColumnIndex(SQLitConst.ColumnRare))
            val Pack = result.getString(result.getColumnIndex(SQLitConst.ColumnPack))
            val CName = result.getString(result.getColumnIndex(SQLitConst.ColumnCName))
            val JName = result.getString(result.getColumnIndex(SQLitConst.ColumnJName))
            val Illust = result.getString(result.getColumnIndex(SQLitConst.ColumnIllust))
            val Number = result.getString(result.getColumnIndex(SQLitConst.ColumnNumber))
            var Cost = result.getString(result.getColumnIndex(SQLitConst.ColumnCost))
            var Power = result.getString(result.getColumnIndex(SQLitConst.ColumnPower))
            val Ability = result.getString(result.getColumnIndex(SQLitConst.ColumnAbility))
            val Lines = result.getString(result.getColumnIndex(SQLitConst.ColumnLines))
            val Faq = result.getString(result.getColumnIndex(SQLitConst.ColumnFaq))
            val Image = result.getString(result.getColumnIndex(SQLitConst.ColumnImage))
            val Restrict = (RestrictUtils.getRestrictList().firstOrNull { bean -> bean.md5 == Md5 }?.restrict ?: 4).toString()
            Cost = if (TextUtils.isEmpty(Cost) || Cost == "0") "-" else Cost
            Power = if (TextUtils.isEmpty(Power) || Power == "0") "-" else Power
            val cardBean = CardBean(Md5, Type, Race, Camp, Sign, Rare, Pack, Restrict, CName, JName, Illust, Number, Cost, Power, Ability, Lines, Faq, Image)
            deviceList.add(cardBean)
        }
        result.close()
        DBManager.getInstance().closeDatabase()
        return deviceList
    }
}
