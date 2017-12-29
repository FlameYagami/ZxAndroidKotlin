package com.dab.zx.uitls

import android.util.Log
import com.dab.zx.config.MyApp.Companion.context
import io.reactivex.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader

/**
 * Created by 八神火焰 on 2016/12/12.
 */

object FileUtils {
    private val TAG = FileUtils::class.java.simpleName

    /**
     * 创建文件夹

     * @param outputPath 输出路径
     * *                   return 是否成功创建文件夹
     */
    fun createDirectory(outputPath: String): Boolean {
        val file = File(outputPath)
        if (!file.exists()) {
            val isCreated = File(outputPath).mkdirs()
            LogUtils.i(TAG, "createDirectory->$outputPath:$isCreated")
            return isCreated
        }
        return true
    }

    /**
     * 获取文件下的文件总数

     * @param path 路径
     */
    fun getFileSize(path: String): Int {
        val file = File(path)
        if (!file.exists()) {
            return 0
        }
        return file.listFiles().size
    }

    /**
     * 拷贝资源文件到指定目录

     * @param resId      资源文件Id
     * *
     * @param parentPath 父级路径
     * *
     * @param childPath  子级路径
     * *
     * @param isCover    是否覆写
     */
    fun copyRaw(resId: Int, parentPath: String, childPath: String, isCover: Boolean) {
        createDirectory(parentPath)
        val file = File(childPath)

        try {
            val inputStream = context.resources.openRawResource(resId)
            // 条件同时满足 压缩文件存在、压缩文件大小未变、不进行覆盖操作
            if (file.exists() && SpUtils.getInt(childPath) == inputStream.available() && !isCover) {
                inputStream.close()
                return
            }
            // 压缩文件存在执行删除操作
            if (file.exists()) {
                file.delete()
            }
            // 重新创建压缩文件
            val available = inputStream.available()
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(2048)
            var bytesRead = -1
            while (inputStream!!.read(buffer).let { bytesRead = it;it != -1 }) {
                outputStream.write(buffer, 0, bytesRead)
            }
            inputStream.close()
            outputStream.close()
            SpUtils.putInt(childPath, available)
            LogUtils.e(TAG, "copyRaw->Succeed")
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.e(TAG, "copyRaw->Failed:" + childPath)
        }

    }

    /**
     * 拷贝资源文件到指定目录
     *
     * @param fileName 资源文件名称
     * @param outPath  输出路径
     * @param isCover  是否覆写
     */
    fun copyAssets(fileName: String, outPath: String, isCover: Boolean): Observable<Int> {
        return Observable.create { subscriber ->
            createDirectory(File(outPath).parent)
            val file = File(outPath)
            // 文件存在、版本正确、非覆写操作->跳过拷贝过程
            if (file.exists() && SpUtils.getInt(outPath) == SystemUtils.systemVersionCode && !isCover) {
                subscriber.onComplete()
                return@create
            }
            // 压缩文件存在执行删除操作
            if (file.exists()) {
                val isDelete = file.delete()
                LogUtils.d(TAG, "resourcesDelete->$fileName:$isDelete")
            }
            subscriber.onNext(0)
            try {
                val inputStream = context.resources.assets.open(fileName)
                // 重新创建压缩文件
                val available = inputStream.available().toLong()
                val isCreate = file.createNewFile()
                LogUtils.d(TAG, "resourcesCreate->$fileName:$isCreate")

                // 拷贝文件
                var copyBytes = 0 // 已拷贝长度
                var copyPercent = 0f // 已拷贝十分比,取整范围(0-10)
                val out = FileOutputStream(file)
                val buffer = ByteArray(2048)
                var bytesRead = -1
                while (inputStream.read(buffer).let { bytesRead = it;it != -1 }) {
                    out.write(buffer, 0, bytesRead)
                    copyBytes += bytesRead
                    val tempCopyPercent = (copyBytes.toFloat() * 10 / available.toFloat()).toInt().toFloat() // 将拷贝百分比扩大10倍,取整范围(0-10)
                    if (tempCopyPercent > copyPercent) {
                        copyPercent = tempCopyPercent
                        subscriber.onNext(copyPercent.toInt() * 10) // 将拷贝十分比扩大10倍,,取整范围(0-100)
                    }
                }
                out.close()
                inputStream.close()
                LogUtils.i(TAG, "copyAssets->$fileName:true")
            } catch (e: Exception) {
                subscriber.onError(e)
                LogUtils.e(TAG, "copyAssets->$fileName:false")
            }

            subscriber.onComplete()
            // 版本信息存入Sp
            SpUtils.putInt(outPath, SystemUtils.systemVersionCode)
        }
    }

    /**
     * 获取资源文件内容

     * @param fileName 资源文件名称
     */
    fun getAssetsContent(fileName: String): String {
        var content = ""
        try {
            val inputStream = context.resources.assets.open(fileName)
            val inputStreamReader = InputStreamReader(inputStream, "utf-8")
            val input = CharArray(inputStream.available())
            inputStreamReader.read(input)
            content = String(input)
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.e(TAG, e.message.toString())
        }
        return content
    }


    /**
     * 获取文件名称（不包含扩展名）

     * @param path 文件路径
     * *
     * @return 文件名称
     */
    fun getFileName(path: String): String {
        val file = File(path)
        if (file.exists()) {
            val fileName = file.name
            return fileName.substring(0, fileName.lastIndexOf("."))
        }
        return ""
    }

    /**
     * 获取文件夹下所有顶级文件路径

     * @param path 文件路径
     * *
     * @return 文件路径集合
     */
    fun getFilePathList(path: String): List<String> {
        createDirectory(path)

        return File(path).listFiles().map { it.absolutePath }
    }

    /**
     * 获取文件夹下所有顶级文件名称（包含扩展名）

     * @param path 文件路径
     * *
     * @return 文件名称集合
     */
    fun getFileNameExList(path: String): List<String> {
        createDirectory(path)

        return File(path).listFiles().map { it.name }
    }

    /**
     * 获取文本内容

     * @param path 文件路径
     * *
     * @return 文本内容
     */
    fun getFileContent(path: String): String {
        val file = File(path)
        var content = ""
        try {
            content = file.readText()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return content
    }

    /**
     * 拷贝文件

     * @param fileNamePath 旧文件路径
     * *
     * @param newFilePath  新文件路径
     * *
     * @return true|false
     */
    fun copyFile(fileNamePath: String, newFilePath: String): Boolean {
        try {
            val file = File(fileNamePath)
            val newFile = File(newFilePath)
            file.copyTo(newFile)
            return true
        } catch (ex: Exception) {
            return false
        }

    }

    /**
     * 重命名文件

     * @param fileNamePath   原始文件路径
     * *
     * @param renameFilePath 熊文件路径
     * *
     * @return 操作结果
     */
    fun renameFile(fileNamePath: String, renameFilePath: String): Boolean {
        val file = File(fileNamePath)
        val renameFile = File(renameFilePath)
        return !renameFile.exists() && file.renameTo(renameFile)
    }

    /**
     * 删除文件

     * @param filePath 文件路径
     * *
     * @return 操作结果
     */
    fun deleteFile(filePath: String): Boolean {
        val file = File(filePath)
        return file.exists() && file.delete()
    }

    /**
     * 将内容写进文件

     * @param content  文本
     * *
     * @param filePath 文件路径
     */
    fun writeFile(content: String, filePath: String): Boolean {
        try {
            val file = File(filePath)
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
            }
            file.writeText(content)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "writeFile->" + e.message)
            return false
        }

    }

    /**
     * 获取文件下的文件总数
     *
     * @param path 路径
     */
    fun getDirectorySize(path: String): Int {
        val file = File(path)
        return if (!file.exists()) {
            0
        } else file.listFiles()!!.size
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param file 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    fun deleteDirectory(file: File) {
        if (file.isFile) {
            file.delete()
            return
        }

        if (file.isDirectory) {
            val childFiles = file.listFiles()
            if (childFiles == null || childFiles.size == 0) {
                file.delete()
                return
            }

            for (childFile in childFiles) {
                deleteDirectory(childFile)
            }
            file.delete()
        }
    }
}
