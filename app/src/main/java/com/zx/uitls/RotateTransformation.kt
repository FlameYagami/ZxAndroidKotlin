package com.zx.uitls

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * Created by 八神火焰 on 2017/1/14.
 */

class RotateTransformation(context: Context, rotateRotationAngle: Float) : BitmapTransformation(context) {

    private var rotateRotationAngle = 0f

    init {
        this.rotateRotationAngle = rotateRotationAngle
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotateRotationAngle)
        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.width, toTransform.height, matrix, true)
    }

    override fun getId(): String {
        return "rotate" + rotateRotationAngle
    }
}