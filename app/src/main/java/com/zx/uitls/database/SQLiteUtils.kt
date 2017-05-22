package com.zx.uitls.database

import android.text.TextUtils
import com.zx.bean.CardBean
import com.zx.config.SQLitConst
import com.zx.game.utils.RestrictUtils
import com.zx.uitls.LogUtils
import java.lang.Exception
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
        try {
            while (result.moveToNext()) {
                val Md5 = result.getString(result.getColumnIndex(SQLitConst.ColumnMd5)).toString()
                val Type = result.getString(result.getColumnIndex(SQLitConst.ColumnType)).toString()
                val Camp = result.getString(result.getColumnIndex(SQLitConst.ColumnCamp)).toString()
                val Race = result.getString(result.getColumnIndex(SQLitConst.ColumnRace)).toString()
                val Sign = result.getString(result.getColumnIndex(SQLitConst.ColumnSign)).toString()
                val Rare = result.getString(result.getColumnIndex(SQLitConst.ColumnRare)).toString()
                val Pack = result.getString(result.getColumnIndex(SQLitConst.ColumnPack)).toString()
                val CName = result.getString(result.getColumnIndex(SQLitConst.ColumnCName)).toString()
                val JName = result.getString(result.getColumnIndex(SQLitConst.ColumnJName)).toString()
                val Illust = result.getString(result.getColumnIndex(SQLitConst.ColumnIllust)).toString()
                val Number = result.getString(result.getColumnIndex(SQLitConst.ColumnNumber)).toString()
                var Cost = result.getString(result.getColumnIndex(SQLitConst.ColumnCost)).toString()
                var Power = result.getString(result.getColumnIndex(SQLitConst.ColumnPower)).toString()
                val Ability = result.getString(result.getColumnIndex(SQLitConst.ColumnAbility)).toString()
                val Lines = result.getString(result.getColumnIndex(SQLitConst.ColumnLines)).toString()
                val Faq = result.getString(result.getColumnIndex(SQLitConst.ColumnFaq)).toString()
                val Image = result.getString(result.getColumnIndex(SQLitConst.ColumnImage)).toString()
                val Restrict = (RestrictUtils.getRestrictList().firstOrNull { bean -> bean.md5 == Md5 }?.restrict ?: 4).toString()
                Cost = if (TextUtils.isEmpty(Cost) || Cost == "0") "-" else Cost
                Power = if (TextUtils.isEmpty(Power) || Power == "0") "-" else Power
                val cardBean = CardBean(Md5, Type, Race, Camp, Sign, Rare, Pack, Restrict, CName, JName, Illust, Number, Cost, Power, Ability, Lines, Faq, Image)
                deviceList.add(cardBean)
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, e.message.toString())
        }
        result.close()
        DBManager.getInstance().closeDatabase()
        return deviceList
    }
}
