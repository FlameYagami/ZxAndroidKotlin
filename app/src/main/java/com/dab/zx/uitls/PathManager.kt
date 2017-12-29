package com.dab.zx.uitls

import android.content.Context
import android.os.Environment
import com.dab.zx.R
import com.dab.zx.config.MyApp.Companion.context
import java.io.File

/**
 * Created by 八神火焰 on 2017/3/6.
 */

object PathManager {
    lateinit var appDir: String
    lateinit var deckDir: String
    lateinit var downloadDir: String
    lateinit var pictureDir: String

    lateinit var pictureZipPath: String
    lateinit var restrictPath: String

    fun init(context: Context) {
        appDir = Environment.getExternalStorageDirectory().absolutePath + File.separator + context.getString(R.string.app_name) + File.separator
        deckDir = appDir + "deck/"
        downloadDir = appDir + "download/"
        pictureDir = appDir + "picture/"

        pictureZipPath = appDir + "picture.zip"
        restrictPath = appDir + "restrict"
    }

    var databasePath: String = ""
        get() {
            return if (SystemUtils.isInstallOnSDCard()) {
                context.filesDir.parent + "/databases/data.db"
            } else {
                "data/data/" + context.packageName + "/databases/data.db"
            }
        }
}
