package com.zx.ui.loading

import android.Manifest
import android.os.Build
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.tbruyelle.rxpermissions.RxPermissions
import com.zx.R
import com.zx.ui.base.BaseExActivity
import com.zx.ui.main.MainActivity
import com.zx.uitls.*
import com.zx.uitls.PathManager.databasePath
import com.zx.uitls.PathManager.pictureCache
import com.zx.uitls.PathManager.pictureZipPath
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by 八神火焰 on 2016/12/12.
 */

class LoadingActivity : BaseExActivity() {

    @BindView(R.id.view_content)
    internal var viewContent: RelativeLayout? = null
    @BindView(R.id.prg_loading)
    internal var prgLoading: ProgressBar? = null
    @BindView(R.id.prg_hint)
    internal var prgHint: TextView? = null
    @BindView(R.id.img_bg_loading)
    internal var imgBg: ImageView? = null

    override val layoutId: Int
        get() = R.layout.activity_loading

    override fun initViewAndData() {
        ButterKnife.bind(this)

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions()
        } else {
            initData()
        }
    }

    override fun onNavigationClick() {
    }

    /**
     * 请求权限
     */
    private fun requestPermissions() {
        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { granted ->
            if (granted!!) {
                initData()
            } else {
                showSnackBar(viewContent!!, "权限不足,程序将在2s后关闭")
                Observable.interval(2000, TimeUnit.MILLISECONDS).subscribe { along -> AppManager.getInstances().AppExit(this) }
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
        ZipUtils.UnZipFolder("picture.zip", pictureCache).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Subscriber<Int>() {
            override fun onCompleted() {
                IntentUtils.gotoActivityAndFinish(this@LoadingActivity, MainActivity::class.java)
            }

            override fun onError(e: Throwable) {
                LogUtils.e(TAG, e.message as String)
            }

            override fun onNext(progress: Int) {
                if (-1 != progress) {
                    prgLoading?.progress = progress
                    prgHint?.text = String.format("%s/100", progress)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        private val TAG = LoadingActivity::class.java.simpleName
    }
}
