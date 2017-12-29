package com.dab.zx.uitls

import android.content.Context
import com.dab.zx.config.MyApp

class SpUtils private constructor() {

    val sp by lazy { MyApp.context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE) }

    companion object {

        private var mInstance: SpUtils? = null

        private val CONFIG = "CONFIG"

        private val instance: SpUtils
            get() {
                if (null == mInstance) {
                    mInstance = SpUtils()
                }
                return mInstance!!
            }

        /**
         * 存储一个float数据类型
         */
        fun putFloat(key: String, value: Float) {
            instance.sp.edit().putFloat(key, value).apply()
        }

        /**
         * 获取一个float数据类型
         */
        fun getFloat(key: String): Float {
            return instance.sp.getFloat(key, 0f)
        }

        /**
         * 存储一个boolean数据类型
         */
        fun putBoolean(key: String, value: Boolean) {
            instance.sp.edit().putString(key, value.toString()).apply()
        }

        /**
         * 获取一个boolean数据类型
         */
        fun getBoolean(key: String): Boolean {
            return instance.sp.getString(key, "") == true.toString()
        }

        /**
         * 存储一个String数据类型
         */
        fun putString(key: String, value: String) {
            instance.sp.edit().putString(key, value).apply()
        }

        /**
         * 获取一个String数据类型
         */
        fun getString(key: String): String {
            return instance.sp.getString(key, "")
        }

        /**
         * 存储一个Int数据类型
         */
        fun putInt(key: String, value: Int) {
            instance.sp.edit().putInt(key, value).apply()
        }

        /**
         * 存储一个Long数据类型
         */
        fun putLong(key: String, value: Long) {
            instance.sp.edit().putLong(key, value).apply()
        }

        /**
         * 获取一个Int数据类型
         */
        fun getInt(key: String): Int {
            return instance.sp.getInt(key, -1)
        }

        /**
         * 获取一个Int数据类型
         */
        fun getLong(key: String): Long {
            return instance.sp.getLong(key, -1)
        }
    }

}
