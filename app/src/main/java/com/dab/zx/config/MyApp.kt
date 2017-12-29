package com.dab.zx.config

import android.annotation.SuppressLint
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.dab.zx.bean.UpdateBean
import com.dab.zx.uitls.PathManager

/**
 * Created by 八神火焰 on 2016/12/10.
 */

class MyApp : MultiDexApplication() {

    companion object {
        var TAG: String = MyApp::class.java.simpleName

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var mUpdateBean: UpdateBean
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = base
        MultiDex.install(this)
        PathManager.init(this)
    }
}
