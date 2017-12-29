package com.dab.zx.uitls

import android.text.TextUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * Created by 八神火焰 on 2017/2/14.
 */

object Md5Utils {
    fun removeMd5(bytes: ByteArray): ByteArray {
        val checkBytes = ByteArray(bytes.size - 2)
        System.arraycopy(bytes, 0, checkBytes, 0, bytes.size - 2)
        return checkBytes
    }

    /**
     * 获取一个Byte数组的Md5值

     * @param bytes 计算的Byte数组
     * *
     * @return 返回计算结果的前4位字符串
     */
    fun calculate(bytes: ByteArray): ByteArray {
        val md5 = ByteArray(2)
        try {
            val temp = MessageDigest.getInstance("MD5").digest(bytes)
            System.arraycopy(temp, 0, md5, 0, 2)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return md5
    }

    /**
     * 检查Byte数组的Md5是否正确

     * @param bytes 检查的Byte数组
     * *
     * @return true|false
     */
    fun check(bytes: ByteArray): Boolean {
        val checkBytes = ByteArray(bytes.size - 2)
        val md5Bytes = ByteArray(2)
        System.arraycopy(bytes, 0, checkBytes, 0, bytes.size - 2)
        System.arraycopy(bytes, bytes.size - 2, md5Bytes, 0, 2)
        return TextUtils.equals(Arrays.toString(calculate(checkBytes)), Arrays.toString(md5Bytes))
    }

    /**
     * 合并两个Byte数组为一个新的Byte数组

     * @param bytes1 第一个Byte数组
     * *
     * @param bytes2 第二个Byte数组
     * *
     * @return 合并后的Byte数组
     */
    fun combineByte(bytes1: ByteArray, bytes2: ByteArray): ByteArray {
        val finalBytes = ByteArray(bytes1.size + bytes2.size)
        System.arraycopy(bytes1, 0, finalBytes, 0, bytes1.size)
        System.arraycopy(bytes2, 0, finalBytes, bytes1.size, bytes2.size)
        return finalBytes
    }
}
