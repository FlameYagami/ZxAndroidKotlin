package com.zx.uitls

import com.zx.R
import com.zx.config.MyApp.Companion.context
import io.reactivex.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

/**
 * Created by 八神火焰 on 2016/12/12.
 */

object ZipUtils {
    private val TAG = ZipUtils::class.java.simpleName

    /**
     * 解压文件到指定路径

     * @param assetsName    资源文件名称
     * *
     * @param directoryPath 解压后的文件夹路径
     */
    fun UnZipFolder(assetsName: String, directoryPath: String): Observable<Int> {
        return Observable.create<Int> { subscriber ->
            val zipCount = getZipSize(directoryPath + context?.getString(R.string.zip_extension)) - 1 // PS:-1是因为存在文件夹
            if (zipCount == FileUtils.getFileSize(directoryPath)) {
                subscriber.onComplete()
            } else {
                var len: Int
                var count = 0
                val unZipPath = File(directoryPath).parent + File.separator
                var szName: String
                var zipEntry: ZipEntry
                var inZip: ZipInputStream? = null
                try {
                    subscriber.onNext(0)
                    inZip = ZipInputStream(context?.resources?.assets?.open(assetsName))
                    while (true) {
                        zipEntry = inZip.nextEntry
                        if (zipEntry == null) break
                        szName = zipEntry.name
                        if (zipEntry.isDirectory) {
                            szName = szName.substring(0, szName.length - 1)
                            FileUtils.createDirectory(unZipPath + szName)
                        } else {
                            val file = File(unZipPath + szName)
                            if (!file.exists()) {
                                file.createNewFile()
                                val out = FileOutputStream(file)
                                val buffer = ByteArray(2048)
                                while (true) {
                                    len = inZip.read(buffer)
                                    if (len == -1) break
                                    out.write(buffer, 0, len)
                                }
                                out.close()
                            }
                        }
                        if (++count % 20 == 0) {
                            subscriber.onNext(count * 100 / zipCount)
                        }
                    }
                    subscriber.onComplete()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                try {
                    assert(inZip != null)
                    inZip?.close()
                } catch (e: Exception) {
                    subscriber.onError(e)
                    e.printStackTrace()
                }

            }
        }
    }

    private fun getZipSize(path: String): Int {
        var zipSize = 0
        try {
            val zipFile = ZipFile(File(path))
            zipSize = zipFile.size()
            zipFile.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return zipSize
    }
}
