package com.zx.game.message

import java.io.ByteArrayOutputStream
import java.io.UnsupportedEncodingException

/**
 * Created by 八神火焰 on 2017/2/16.
 */

class ClientPacket {
    private val mByteArrayOutputStream: ByteArrayOutputStream

    init {
        mByteArrayOutputStream = ByteArrayOutputStream()
        mByteArrayOutputStream.write(4.toByte().toInt())
    }

    fun write(string: String) {
        try {
            val bytes = string.toByteArray(charset("UTF-8"))
            write(bytes)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }

    fun write(b: Byte) {
        mByteArrayOutputStream.write(b.toInt())
    }

    fun write(bytes: ByteArray) {
        for (i in bytes.indices) {
            mByteArrayOutputStream.write(bytes[i].toInt())
        }
    }

    val bytes: ByteArray
        get() = mByteArrayOutputStream.toByteArray()
}
