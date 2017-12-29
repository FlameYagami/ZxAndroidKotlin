package com.dab.zx.uitls.database

import android.text.TextUtils
import com.dab.zx.R
import com.dab.zx.bean.CardBean
import com.dab.zx.bean.KeySearchBean
import com.dab.zx.config.MapConst
import com.dab.zx.config.MyApp.Companion.context
import com.dab.zx.config.SQLitConst
import com.dab.zx.config.SpConst
import com.dab.zx.uitls.LogUtils
import com.dab.zx.uitls.SpUtils
import java.util.*


/**
 * Created by 八神火焰 on 2016/12/13.
 */

class SqlUtils : SQLitConst {
    companion object {
        private val TAG = SqlUtils::class.java.simpleName

        fun getKeyQuerySql(key: String): String {
            val builder = StringBuilder()
            builder.append(headerSql)// 基础查询语句
            builder.append(SqlUtils.getAllKeySql(key)) // 关键字
            builder.append(footerSql) // 排序
            return builder.toString() // 完整的查询语句
        }

        fun getPackQuerySql(key: String): String {
            val builder = StringBuilder()
            builder.append(headerSql)// 基础查询语句
            builder.append(SqlUtils.getSimilarSql(key, SQLitConst.ColumnPack)) // 关键字
            builder.append(footerSql) // 排序
            return builder.toString() // 完整的查询语句
        }

        fun getQuerySql(cardBean: CardBean): String {
            val builder = StringBuilder()
            builder.append(headerSql)// 基础查询语句
            builder.append(SqlUtils.getAllKeySql(cardBean.key)) // 关键字
            builder.append(SqlUtils.getAccurateSql(cardBean.type, SQLitConst.ColumnType)) // 种类
            builder.append(SqlUtils.getSimilarSql(cardBean.camp, SQLitConst.ColumnCamp)) // 阵营
            builder.append(SqlUtils.getAccurateSql(cardBean.race, SQLitConst.ColumnRace)) // 种族
            builder.append(SqlUtils.getAccurateSql(cardBean.sign, SQLitConst.ColumnSign)) // 标记
            builder.append(SqlUtils.getAccurateSql(cardBean.rare, SQLitConst.ColumnRare)) // 罕贵
            builder.append(SqlUtils.getAccurateSql(cardBean.illust, SQLitConst.ColumnIllust)) // 画师
            builder.append(SqlUtils.getPackSql(cardBean.pack, SQLitConst.ColumnPack)) // 卡包
            builder.append(SqlUtils.getIntervalSql(cardBean.cost, SQLitConst.ColumnCost)) // 费用
            builder.append(SqlUtils.getIntervalSql(cardBean.power, SQLitConst.ColumnPower)) // 力量
            builder.append(cardBean.abilityType) //  能力类型
            builder.append(cardBean.abilityDetail) // 详细能力
            builder.append(footerSql) // 排序
            LogUtils.i(TAG, builder.toString())
            return builder.toString() // 完整的查询语句
        }

        val queryAllSql: String
            get() = "SELECT * FROM ${SQLitConst.TableName} ORDER BY " + SQLitConst.ColumnNumber + " ASC"

        /**
         * 获取头部查询语句

         * @return sql
         */
        private val headerSql: String
            get() = "SELECT * FROM ${SQLitConst.TableName} WHERE 1=1"

        /**
         * 获取尾部查询语句

         * @return sql
         */
        private val footerSql: String
            get() = if (SpUtils.getString(SpConst.OrderPattern) == context.getString(R.string.number)) orderNumberSql else orderValueSql

        /**
         * 获取卡编排序方式查询语句

         * @return sql
         */
        private val orderNumberSql: String
            get() = " ORDER BY ${SQLitConst.ColumnNumber} ASC"

        /**
         * 获取数值排序方式查询语句

         * @return sql
         */
        private val orderValueSql: String
            get() = " ORDER BY ${SQLitConst.ColumnCamp} DESC, ${SQLitConst.ColumnType} DESC, ${SQLitConst.ColumnCName} DESC," +
                    " ${SQLitConst.ColumnRace} ASC, ${SQLitConst.ColumnCost} DESC, ${SQLitConst.ColumnPower} DESC"

        /**
         * 获取精确查询语句

         * @param value  查询的值
         * *
         * @param column 数据库字段
         * *
         * @return sql
         */
        private fun getAccurateSql(value: String, column: String): String {
            return if (context.getString(R.string.not_applicable) != value) " AND $column='$value'" else ""
        }

        /**
         * 获取模糊查询语句

         * @param column 数据库字段
         * *
         * @param value  查询的值
         * *
         * @return sql
         */
        fun getSimilarSql(value: String, column: String): String {
            return if (TextUtils.isEmpty(value) || context.getString(R.string.not_applicable) == value) "" else " AND $column LIKE '%$value%'"
        }

        /**
         * 获取数值查询语句（适用范围:费用、力量）

         * @param value  查询的值
         * *
         * @param column 数据库字段
         * *
         * @return sql
         */
        private fun getIntervalSql(value: String, column: String): String {
            return if (!TextUtils.isEmpty(value))
                if (value.contains(context.getString(R.string.hyphen) as String))
                    " AND $column>=${value.split("-").toTypedArray()[0]} AND $column<=${value.split("-").toTypedArray()[1]}"
                else
                    " AND $column=$value"
            else
                ""
        }

        /**
         * 获取卡包查询语句

         * @param value  查询的值
         * *
         * @param column 数据库字段
         * *
         * @return sql
         */
        private fun getPackSql(value: String, column: String): String {
            if (context.getString(R.string.not_applicable) == value) {
                return ""
            }
            return " AND $column LIKE '%${if (value.contains(context.getString(R.string.series) as String)) value.substring(0, 1) else value}%'"
        }

        /**
         * 获取卡片能力类型语句

         * @param checkboxMap checkbox对应的Map
         * *
         * @return sql
         */
        fun getAbilityTypeSql(checkboxMap: LinkedHashMap<String, Boolean>): String {
            val sql = StringBuilder()
            val tempKeyList = checkboxMap.filter { it.value }.map { it.key }.toList()
            for (tempKey in tempKeyList) {
                for ((key, value) in MapConst.AbilityTypeMap) {
                    if (key == tempKey) {
                        sql.append(" AND ${SQLitConst.ColumnAbility} LIKE '%$value%'")
                        break
                    }
                }
            }
            return sql.toString()
        }

        /**
         * 获取卡片能力分类语句

         * @param checkboxMap checkbox对应的Map
         * *
         * @return sql
         */
        fun getAbilityDetailSql(checkboxMap: LinkedHashMap<String, Boolean>): String {
            val sql = StringBuilder()
            checkboxMap.filter { it.value }.map { it.key }.forEach {
                sql.append(" AND ${SQLitConst.ColumnAbilityDetail} LIKE '%\"$it\":1%'")
            }
            return sql.toString()
        }

        /**
         * 获取关键字段取值

         * @param value 查询的值
         * *
         * @return sql
         */
        private fun getAllKeySql(value: String): String {
            if (TextUtils.isEmpty(value)) {
                return ""
            }
            val tempValue = StringBuilder()
            val keyList = value.split(" ") // 以空格分割关键字
            for (key in keyList) {
                tempValue.append(" AND ( JName LIKE '%$key%' ${getPartKeySql(key)})")
            }
            return tempValue.toString()
        }

        private fun getPartKeySql(value: String): String {
            val tempValue = StringBuilder()
            KeySearchBean.selectKeySearchList
                    .map { MapConst.KeySearchMap[it] }
                    .forEach { tempValue.append(" OR $it LIKE '%$value%'") }
            return tempValue.toString()
        }
    }
}
