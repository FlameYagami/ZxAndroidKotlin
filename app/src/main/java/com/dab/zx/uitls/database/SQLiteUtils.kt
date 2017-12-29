package com.dab.zx.uitls.database

import android.text.TextUtils
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.config.MyApp.Companion.context
import com.dab.zx.config.SQLitConst
import com.dab.zx.game.utils.RestrictUtils
import com.dab.zx.uitls.LogUtils
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
                val md5 = result.getString(result.getColumnIndex(SQLitConst.ColumnMd5)) ?: ""
                val type = result.getString(result.getColumnIndex(SQLitConst.ColumnType)) ?: ""
                val camp = result.getString(result.getColumnIndex(SQLitConst.ColumnCamp)) ?: ""
                val race = result.getString(result.getColumnIndex(SQLitConst.ColumnRace)) ?: ""
                val sign = result.getString(result.getColumnIndex(SQLitConst.ColumnSign)) ?: ""
                val rare = result.getString(result.getColumnIndex(SQLitConst.ColumnRare)) ?: ""
                val pack = result.getString(result.getColumnIndex(SQLitConst.ColumnPack)) ?: ""
                val cname = result.getString(result.getColumnIndex(SQLitConst.ColumnCName)) ?: ""
                val jname = result.getString(result.getColumnIndex(SQLitConst.ColumnJName)) ?: ""
                val illust = result.getString(result.getColumnIndex(SQLitConst.ColumnIllust)) ?: ""
                val number = result.getString(result.getColumnIndex(SQLitConst.ColumnNumber)) ?: ""
                var cost = result.getString(result.getColumnIndex(SQLitConst.ColumnCost)) ?: ""
                var power = result.getString(result.getColumnIndex(SQLitConst.ColumnPower)) ?: ""
                val ability = result.getString(result.getColumnIndex(SQLitConst.ColumnAbility)) ?: ""
                val lines = result.getString(result.getColumnIndex(SQLitConst.ColumnLines)) ?: ""
                val image = result.getString(result.getColumnIndex(SQLitConst.ColumnImage)) ?: ""
                val restrict = (RestrictUtils.getRestrictList().firstOrNull { it.md5.equals(md5) }?.restrict ?: 4).toString()
                cost = if (TextUtils.isEmpty(cost) || cost == context.getString(R.string.cost_not_applicable)) "-" else cost
                power = if (TextUtils.isEmpty(power) || power == context.getString(R.string.power_not_applicable)) "-" else power
                val cardBean = CardBean(md5, type, race, camp, sign, rare, pack, restrict, cname, jname, illust, number, cost, power, ability, lines, image)
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
