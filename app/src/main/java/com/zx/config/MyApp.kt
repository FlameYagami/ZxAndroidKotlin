package com.zx.config

import android.app.Application
import android.content.Context
import com.liulishuo.filedownloader.FileDownloader
import com.zx.bean.UpdateBean
import com.zx.uitls.PathManager

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class MyApp : Application() {
    companion object {
        var context: Context? = null
        var mUpdateBean: UpdateBean? = null
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        PathManager.init(applicationContext)
        FileDownloader.init(applicationContext)
    }
}
