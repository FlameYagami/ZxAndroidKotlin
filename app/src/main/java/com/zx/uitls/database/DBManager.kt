package com.zx.uitls.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.zx.config.MyApp.Companion.context
import com.zx.uitls.LogUtils
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by 八神火焰 on 2017/1/5.
 */

/**
 * 数据库管理工具
 */
internal class DBManager {

    /**
     * 数据库打开计数
     */
    private val mOpenCounter = AtomicInteger()

    /**
     * 数据库对象
     */
    private var sqLiteDatabase: SQLiteDatabase? = null

    /**
     * 获取数据库操作对象接口

     * @return mInstance
     */
    fun getSQLiteHelper(): SQLiteHelper {
        return sqLiteHelper as SQLiteHelper
    }

    /**
     * 打开数据库

     * @return 数据库对象
     */
    @Synchronized fun openDatabase(): SQLiteDatabase {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            sqLiteDatabase = sqLiteHelper?.writableDatabase
            LogUtils.e(TAG, "openDatabase->" + mOpenCounter.toInt())
        }
        return sqLiteDatabase as SQLiteDatabase
    }

    /**
     * 关闭数据库
     */
    @Synchronized fun closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            sqLiteDatabase?.close()
            LogUtils.e(TAG, "closeDatabase->" + mOpenCounter.toInt())
        }
    }

    companion object {
        private val TAG = DBManager::class.java.simpleName

        /**
         * DatabaseManager接口
         */
        private var mInstance: DBManager? = null

        /**
         * 数据库操作对象
         */
        private var sqLiteHelper: SQLiteHelper? = null

        /**
         * 获取DatabaseManager接口

         * @return mInstance
         */
        @Synchronized fun getInstance(): DBManager {
            if (null == mInstance) {
                mInstance = DBManager()
            }
            if (null == sqLiteHelper) {
                sqLiteHelper = SQLiteHelper(context as Context, "data.db", 1)
            }
            return mInstance as DBManager
        }

        /**
         * 释放DatabaseManager接口
         */
        @Synchronized fun releaseInstance() {
            if (mInstance != null) {
                getInstance().closeDatabase()
                mInstance = null
            }
        }

        /**
         * 删除数据库

         * @param context      上下文
         * *
         * @param dataBaseName 数据库名称
         * *
         * @return 返回是否删除成功
         */
        fun deleteDatabase(context: Context, dataBaseName: String): Boolean {
            return context.deleteDatabase(dataBaseName)
        }
    }
}