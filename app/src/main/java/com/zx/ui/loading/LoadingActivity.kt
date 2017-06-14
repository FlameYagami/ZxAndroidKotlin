package com.zx.ui.loading

import android.Manifest
import android.os.Build
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zx.R
import com.zx.ui.base.BaseExActivity
import com.zx.ui.main.MainActivity
import com.zx.uitls.AppManager
import com.zx.uitls.FileUtils
import com.zx.uitls.LogUtils
import com.zx.uitls.PathManager.databasePath
import com.zx.uitls.PathManager.pictureCache
import com.zx.uitls.PathManager.pictureZipPath
import com.zx.uitls.ZipUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_loading.*
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

/**
 * Created by 八神火焰 on 2016/12/12.
 */

class LoadingActivity : BaseExActivity() {

    companion object {
        private val TAG = LoadingActivity::class.java.simpleName
    }

    override val layoutId: Int
        get() = R.layout.activity_loading

    override fun initViewAndData() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions()
        } else {
            initData()
        }
    }

    /**
     * 请求权限
     */
    private fun requestPermissions() {
        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { granted ->
            if (granted!!) {
                initData()
            } else {
                showSnackBar(view_content, "权限不足,程序将在2s后关闭")
                Observable.interval(2000, TimeUnit.MILLISECONDS).subscribe { AppManager.instance().AppExit(this) }
            }
        }
    }

    /**
     * 初始化数据库
     */
    private fun initData() {
        Observable.just(this).observeOn(Schedulers.io()).subscribe({
            // 拷贝资源文件
            FileUtils.copyAssets("data.db", databasePath, false)
            FileUtils.copyAssets("picture.zip", pictureZipPath, false)
            // 解压资源文件到外部
            UnZipPicture()
        }) { throwable -> LogUtils.e(TAG, throwable.message as String) }
    }

    private fun UnZipPicture() {
        ZipUtils.UnZipFolder("picture.zip", pictureCache).observeOn(AndroidSchedulers.mainThread()).subscribe({ progress ->
            if (-1 != progress) {
                prgLoading.progress = progress
                prgHint.text = String.format("%s/100", progress)
            }
        }, { t -> LogUtils.e(TAG, t.message.toString()) }, {
            startActivity<MainActivity>()
            finish()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

