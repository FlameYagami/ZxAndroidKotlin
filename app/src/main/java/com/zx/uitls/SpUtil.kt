package com.zx.uitls

import android.content.Context
import android.content.SharedPreferences
import com.zx.config.MyApp

class SpUtil private constructor() {

    private val sp: SharedPreferences

    init {
        sp = MyApp.context!!.getSharedPreferences(CONFIG, Context.MODE_PRIVATE)
    }

    /**
     * 存储一个float数据类型
     */
    fun putFloat(key: String, value: Float) {
        sp.edit().putFloat(key, value).apply()
    }

    /**
     * 获取一个float数据类型
     */
    fun getFloat(key: String): Float {
        return sp.getFloat(key, 0f)
    }

    /**
     * 存储一个boolean数据类型
     */
    fun putBoolean(key: String, value: Boolean) {
        sp.edit().putString(key, value.toString()).apply()
    }

    /**
     * 获取一个boolean数据类型
     */
    fun getBoolean(key: String): Boolean {
        return sp.getString(key, "") == true.toString()
    }

    /**
     * 存储一个String数据类型
     */
    fun putString(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    /**
     * 获取一个String数据类型
     */
    fun getString(key: String): String {
        return sp.getString(key, "")
    }

    /**
     * 存储一个Int数据类型
     */
    fun putInt(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
    }

    /**
     * 存储一个Long数据类型
     */
    fun putLong(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    /**
     * 获取一个Int数据类型
     */
    fun getInt(key: String): Int {
        return sp.getInt(key, -1)
    }

    /**
     * 获取一个Int数据类型
     */
    fun getLong(key: String): Long {
        return sp.getLong(key, -1)
    }

    companion object {
        val IsNotFirst = "IsNotFirst"

        private var mInstance: SpUtil? = null

        private val CONFIG = "CONFIG"

        val instance: SpUtil
            get() {
                if (null == mInstance) {
                    mInstance = SpUtil()
                }
                return mInstance!!
            }
    }

}
