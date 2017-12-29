package com.dab.zx.game.service

import com.dab.zx.game.message.ServicePacket
import com.dab.zx.uitls.Md5Utils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

/**
 * Created by 八神火焰 on 2017/2/25.
 */

class ClientInputCache(private val mOnReadListener: ClientInputCache.OnReadListener) {

    private var mByteBuffer: ByteBuffer? = null
    private var mReadSubscriber: ReadSubscriber? = null

    init {
        if (null == mReadSubscriber) {
            mReadSubscriber = ReadSubscriber()
        }
    }

    interface OnReadListener {
        fun read(bytes: ByteArray)
    }

    fun onDestroy() {
        if (null != mReadSubscriber) {
            mReadSubscriber?.releaseSubscriber()
            mReadSubscriber = null
        }
    }

    fun write(bytes: ByteArray) {
        var bytes = bytes
        val remainingBytes: ByteArray? = remaining
        if (null != remainingBytes) {
            bytes = Md5Utils.combineByte(remainingBytes, bytes)
        }
        mByteBuffer = ByteBuffer.wrap(bytes)
    }

    private val remaining: ByteArray?
        get() {
            if (null != mByteBuffer && mByteBuffer?.hasRemaining() as Boolean) {
                val bytes = ByteArray(mByteBuffer?.remaining() as Int)
                mByteBuffer?.get(bytes, 0, mByteBuffer?.remaining() as Int)
                return bytes
            }
            return null
        }

    @Synchronized private fun read(): ByteArray? {
        val length = readLength()
        if (-1 != length && length + 4 <= mByteBuffer?.remaining() as Int) {
            val bytes = ByteArray(length)
            mByteBuffer?.get(bytes, 0, 4)
            mByteBuffer?.get(bytes, 0, length)
            return bytes
        }
        return null
    }

    private fun readLength(): Int {
        if (null != mByteBuffer && 4 <= mByteBuffer?.remaining() as Int) {
            val bytes = ByteArray(4)
            val position = mByteBuffer?.array()?.size?.minus(mByteBuffer?.remaining() as Int)
            System.arraycopy(mByteBuffer?.array(), position as Int, bytes, 0, 4)
            return ServicePacket(bytes).readCSharpInt()
        }
        return -1
    }

    private inner class ReadSubscriber internal constructor() {
        internal var subscription: Disposable? = null

        init {
            subscription = Observable.interval(500, TimeUnit.MILLISECONDS).subscribe {
                val readBytes: ByteArray? = read()
                if (null != (readBytes)) {
                    mOnReadListener.read(readBytes)
                }
            }
        }

        internal fun releaseSubscriber() {
            subscription?.dispose()
            subscription = null
        }
    }

    companion object {
        private val TAG = ClientInputCache::class.java.simpleName
    }
}
