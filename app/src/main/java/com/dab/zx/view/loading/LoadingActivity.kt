package com.dab.zx.view.loading

import android.Manifest
import android.os.Build
import com.dab.zx.R
import com.dab.zx.uitls.AppManager
import com.dab.zx.uitls.FileUtils
import com.dab.zx.uitls.PathManager.databasePath
import com.dab.zx.uitls.PathManager.restrictPath
import com.dab.zx.uitls.rxjava.ObservableEx
import com.dab.zx.uitls.rxjava.ResourcesSubscriber
import com.dab.zx.view.base.BaseExActivity
import com.dab.zx.view.main.MainActivity
import com.dab.zx.view.setting.AboutActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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

    private var isRepair = false

    override val layoutId: Int
        get() = R.layout.activity_loading

    override fun initViewAndData() {
        // 资源修复判定
        val bundle = intent.extras
        if (null != bundle) {
            isRepair = bundle.getBoolean(AboutActivity::class.java.simpleName)
        }
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
            if (granted) {
                initData()
            } else {
                showSnackBar(view_content, "权限不足,程序将在2s后关闭")
                Observable.interval(2000, TimeUnit.MILLISECONDS).subscribe { AppManager.instance().AppExit(this) }
            }
        }
    }

    /**
     * 初始化资源
     */
    private fun initData() {
        val ob0 = ObservableEx(FileUtils.copyAssets("data.db", databasePath, isRepair), getString(R.string.database_is_copying))
        val ob1 = ObservableEx(FileUtils.copyAssets("restrict", restrictPath, isRepair), getString(R.string.restrict_is_copying))
//        val ob2 = ObservableEx(FileUtils.copyAssets("picture.zip", pictureZipPath, isRepair), getString(R.string.image_is_copying))
//        val ob3 = ObservableEx(ZipUtils.unZipFolder("picture.zip", pictureDir, isRepair), getString(R.string.image_is_unzipping))
        ResourcesSubscriber(ob0, ob1).apply(prgLoading, prgHint)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ObservableEx> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(observableEx: ObservableEx) {

                    }

                    override fun onError(e: Throwable) {
                        e.message?.let { showToast(it) }
                    }

                    override fun onComplete() {
                        startActivity<MainActivity>()
                        finish()
                    }
                })
    }
}

