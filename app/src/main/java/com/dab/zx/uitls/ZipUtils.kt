package com.dab.zx.uitls

import com.dab.zx.config.MyApp.Companion.context
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
     *
     * @param assetsName 资源文件名称
     * @param outputPath 解压后的文件夹路径
     * @param isCover    是否覆盖
     */
    fun unZipFolder(assetsName: String, outputPath: String, isCover: Boolean): Observable<Int> {
        return Observable.create { subscriber ->
            // 检测版本
            val isVersionCode = SpUtils.getInt(outputPath) == SystemUtils.systemVersionCode
            // 检测文件数量(PS:-1是因为存在文件夹)
            val zipCount = getZipSize(PathManager.appDir + assetsName) - 1
            val isZipCount = zipCount == FileUtils.getDirectorySize(outputPath)
            // 版本正确、文件数量正确、非覆写操作->跳过解压过程
            if (isVersionCode && isZipCount && !isCover) {
                subscriber.onComplete()
                return@create
            }
            // 因为Android删除文件会加锁，删除完后重新创建存在Bug，此时需要重命名待删除文件，再执行删除操作
            FileUtils.renameFile(outputPath, "del" + outputPath)
            // 清空资源文件
            FileUtils.deleteDirectory(File("del" + outputPath))
            subscriber.onNext(0)
            try {
                var count = 0
                val unZipPath = File(outputPath).parent + File.separator
                var szName: String
                var zipEntry = ZipEntry("")
                val inZip = ZipInputStream(context.resources.assets.open(assetsName))
                while (inZip.nextEntry.let { zipEntry = it;it != null }) {
                    szName = zipEntry.name
                    if (zipEntry.isDirectory) {
                        szName = szName.substring(0, szName.length - 1)
                        FileUtils.createDirectory(unZipPath + szName)
                    } else {
                        unZipFile(unZipPath + szName, inZip)
                    }
                    if (++count % 20 == 0) {
                        subscriber.onNext(count * 100 / zipCount)
                    }
                }
                inZip.close()
            } catch (e: Exception) {
                subscriber.onError(e)
            }

            subscriber.onComplete()
            // 版本信息存入Sp
            SpUtils.putInt(outputPath, SystemUtils.systemVersionCode)
        }
    }

    /**
     * 解压单个文件
     *
     * @param filePath    解压路径
     * @param inputStream 文件流
     */
    private fun unZipFile(filePath: String, inputStream: ZipInputStream) {
        try {
            var len = 0
            val file = File(filePath)
            if (!file.exists()) {
                file.createNewFile()
                val out = FileOutputStream(file)
                val buffer = ByteArray(2048)
                while (inputStream.read(buffer).let { len = it;it != -1 }) {
                    out.write(buffer, 0, len)
                }
                out.close()
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, e.message.toString())
        }

    }

    /**
     * 获取Zip大小
     *
     * @param path Zip路径
     * @return size
     */
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
