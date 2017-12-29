package com.dab.zx.uitls.database

/**
 * Created by 八神火焰 on 2017/1/9.
 */

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dab.zx.uitls.LogUtils
import java.util.*

/**
 * 数据库操作类.
 */
internal class SQLiteHelper
/**
 * 构造方法.

 * @param context       上下文环境.
 * *
 * @param name          数据库名称.
 * *
 * @param version       数据库版本号.
 */
(context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    private val TAG = SQLiteHelper::class.java.simpleName

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {

    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {

    }

    /**
     * 向数据库的指定表中插入数据.

     * @param db      数据库对象.
     * *
     * @param table   表名.
     * *
     * @param columns 字段名.
     * *
     * @param values  数据值.
     * *
     * @return 若传入的字段名与插入值的长度不等则返回false, 否则执行成功则返回true.
     */
    fun insert(db: SQLiteDatabase, table: String, columns: Array<String>, values: Array<String>): Boolean {
        if (columns.size != values.size)
        // 判断传入的字段名数量与插入数据的数量是否相等
        {
            LogUtils.e(TAG, "columns != values")
            return false
        }
        val result: Long // 添加语句影响的行数
        try {
            val contentValues = ContentValues() // 将插入值与对应字段放入ContentValues实例中
            for (i in columns.indices) {
                contentValues.put(columns[i], values[i])
            }
            result = db.insert(table, null, contentValues) // 执行插入操作
        } catch (e: Exception) {
            LogUtils.e(TAG, "insert->" + e.message)
            return false
        }

        return -1 != result.toInt()
    }


    /**
     * 删除数据库的指定表中的指定数据.

     * @param db          数据库对象.
     * *
     * @param table       表名.
     * *
     * @param conditions  条件字段.
     * *
     * @param whereValues 条件值.
     */
    fun delete(db: SQLiteDatabase, table: String, conditions: String, whereValues: Array<String>): Boolean {
        val result: Int // 删除语句影响的行数
        try {
            result = db.delete(table, conditions, whereValues) //执行删除操作
        } catch (e: Exception) {
            LogUtils.e(TAG, "delete->" + e.message)
            return false
        }

        return -1 != result
    }


    /**
     * 修改数据库的指定表中的指定数据.

     * @param table       表名.
     * *
     * @param titles      字段名.
     * *
     * @param values      数据值.
     * *
     * @param conditions  条件字段.
     * *
     * @param whereValues 条件值.
     * *
     * @return 若传入的字段名与插入值的长度不等则返回false, 否则执行成功则返回true.
     */
    fun update(db: SQLiteDatabase, table: String, titles: Array<String>, values: Array<String>, conditions: String, whereValues: Array<String>): Boolean {
        if (titles.size != values.size) {
            LogUtils.e(TAG, "columns != values")
            return false
        }
        val result: Int // 更新语句影响的行数
        try {
            // 将插入值与对应字段放入ContentValues实例中
            val contentValues = ContentValues()
            for (i in titles.indices) {
                contentValues.put(titles[i], values[i])
            }
            result = db.update(table, contentValues, conditions, whereValues) // 执行修改操作
        } catch (e: Exception) {
            LogUtils.e(TAG, "update->" + e.message)
            return false
        }

        return -1 != result
    }

    /**
     * 删除数据库的指定表中的所有数据.

     * @param db    数据库对象
     * *
     * @param table 表名
     */
    fun clear(db: SQLiteDatabase, table: String): Boolean {
        try {
            db.execSQL("delete from " + table)
        } catch (e: Exception) {
            LogUtils.e(TAG, "clear->" + e.message)
            return false
        }

        return true
    }

    /**
     * 查询数据库的指定表中的指定数据.

     * @param table         表名.
     * *
     * @param columns       查询字段.
     * *
     * @param selection     条件字段.
     * *
     * @param selectionArgs 条件值.
     * *
     * @param groupBy       分组名称.
     * *
     * @param having        分组条件.与groupBy配合使用.
     * *
     * @param orderBy       排序字段.
     * *
     * @param limit         分页.
     * *
     * @return 查询结果游标
     */
    fun getCursor(db: SQLiteDatabase, table: String, columns: Array<String>, selection: String, selectionArgs: Array<String>, groupBy: String, having: String, orderBy: String, limit: String): Cursor {
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit) // 执行查询操作.
    }

    /**
     * 查询数据库的指定表中的指定数据.

     * @param sql 查询语句
     * *
     * @return 查询结果游标
     */
    fun getCursor(db: SQLiteDatabase, sql: String): Cursor {
        return db.rawQuery(sql, null) // 执行查询操作.
    }

    /**
     * 查询数据库的指定表中的指定数据.

     * @param table         表名.
     * *
     * @param columns       查询字段.
     * *
     * @param selection     条件字段.
     * *
     * @param selectionArgs 条件值.
     * *
     * @param groupBy       分组名称.
     * *
     * @param having        分组条件.与groupBy配合使用.
     * *
     * @param orderBy       排序字段.
     * *
     * @param limit         分页.
     * *
     * @return 查询的数据.
     */
    fun select(db: SQLiteDatabase, table: String, columns: Array<String>, selection: String, selectionArgs: Array<String>, groupBy: String,
               having: String, orderBy: String, limit: String): ArrayList<HashMap<String, String>> {
        val cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit) // 执行查询操作.
        val count = cursor.columnCount
        val list = ArrayList<HashMap<String, String>>()
        while (cursor.moveToNext()) {
            val map = HashMap<String, String>()
            for (i in 0..count - 1) {
                map.put(cursor.getColumnName(i), cursor.getString(i))
            }
            list.add(map)
        }
        cursor.close()
        return list
    }

    /**
     * 事务处理,调用OperationTransaction接口中的executeTransaction的方法根据返回判断事务是否执行成功.
     * 若事务执行成功则进行数据提交,否则进行滚回操作.

     * @param ot 操作数据库事务对象.
     * *
     * @return 若事务执行成功则返回true, 否则滚回返回false.
     */
    fun transaction(db: SQLiteDatabase, ot: OnTransaction): Boolean {
        db.beginTransaction() // 开始事务.
        val isSuccess = ot.executeTransaction(db)
        if (isSuccess) {
            db.setTransactionSuccessful() // 设置事务处理成功,不设置会自动回滚不提交.
        }
        db.endTransaction() // 事务结束.
        return isSuccess
    }

    /**
     * 事务操作接口.
     * 提供数据库事务操作接口.
     */
    internal interface OnTransaction {

        /**
         * 执行事务.

         * @param db 数据库操作对象.
         * *
         * @return 若事务执行完成并成功返回true, 若失败则返回false.
         */
        fun executeTransaction(db: SQLiteDatabase): Boolean

    }
}