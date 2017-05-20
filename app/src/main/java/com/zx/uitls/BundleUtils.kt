package com.zx.uitls

import android.os.Bundle

object BundleUtils {
    /**
     * 返回一个Boolean类型的Bundle

     * @param key   键
     * *
     * @param value 值
     * *
     * @return Bundle
     */
    fun putBoolean(key: String, value: Boolean): Bundle {
        val bundle = Bundle()
        bundle.putBoolean(key, value)
        return bundle
    }

    /**
     * 返回一个Int类型的Bundle

     * @param key   键
     * *
     * @param value 值
     * *
     * @return Bundle
     */
    fun putInt(key: String, value: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt(key, value)
        return bundle
    }

    /**
     * 返回一个String类型的Bundle

     * @param key   键
     * *
     * @param value 值
     * *
     * @return Bundle
     */
    fun putString(key: String, value: String): Bundle {
        val bundle = Bundle()
        bundle.putString(key, value)
        return bundle
    }

    fun putByteArray(key: String, value: ByteArray): Bundle {
        val bundle = Bundle()
        bundle.putByteArray(key, value)
        return bundle
    }
}
