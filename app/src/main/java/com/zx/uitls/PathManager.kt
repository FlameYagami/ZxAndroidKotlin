package com.zx.uitls

import android.content.Context
import android.os.Environment

import com.zx.R

import java.io.File

/**
 * Created by 八神火焰 on 2017/3/6.
 */

object PathManager {
    var appCache: String = ""
    var pictureCache: String = ""
    var pictureZipPath: String = ""
    var databasePath: String = ""
    var deckPath: String = ""
    var downloadPath: String = ""
    var banlistPath: String = ""

    fun init(context: Context) {
        appCache = Environment.getExternalStorageDirectory().absolutePath + File.separator + context.getString(R.string.app_name) + File.separator
        pictureCache = appCache + context.getString(R.string.picture)
        pictureZipPath = pictureCache + context.getString(R.string.zip_extension)
        databasePath = "data/data/" + context.packageName + "/databases/data.db"
        deckPath = appCache + "deck/"
        downloadPath = appCache + "download/"
        banlistPath = appCache + "banlist"
    }
}
