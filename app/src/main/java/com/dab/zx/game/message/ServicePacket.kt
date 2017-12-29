package com.dab.zx.game.message

import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer

/**
 * Created by 八神火焰 on 2017/2/16.
 */

class ServicePacket(bytes: ByteArray) {
    private val mDataInputStream: DataInputStream

    init {
        val mInputStream = ByteArrayInputStream(bytes)
        mDataInputStream = DataInputStream(mInputStream)
    }

    fun readCSharpInt(): Int {
        val bytes = ByteArray(4)
        try {
            for (i in 3 downTo 1) {
                bytes[i] = mDataInputStream.read().toByte()
            }
            val buffer = ByteBuffer.wrap(bytes)
            return buffer.int
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return -1
    }

    fun readCSharpShort(): Short {
        val bytes = ByteArray(2)
        try {
            for (i in 2 downTo 1) {
                bytes[i] = mDataInputStream.read().toByte()
            }
            val buffer = ByteBuffer.wrap(bytes)
            return buffer.short
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return -1
    }

    fun readByte(): Byte {
        try {
            return mDataInputStream.readByte()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return -1
    }

    fun readBytes(length: Int): ByteArray {
        val bytes = ByteArray(length)
        for (i in 0..length - 1) {
            bytes[i] = readByte()
        }
        return bytes
    }

    fun readString(length: Int): String {
        try {
            return String(readBytes(length))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return "-1"
    }

    fun readStringToEnd(): String {
        try {
            return String(readBytesToEnd())
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return "-1"
    }

    fun readBytesToEnd(): ByteArray {
        try {
            return readBytes(mDataInputStream.available())
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return byteArrayOf(-1)
    }
}
