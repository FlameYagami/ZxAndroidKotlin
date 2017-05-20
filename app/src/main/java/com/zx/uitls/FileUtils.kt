package com.zx.uitls

import android.util.Log
import com.zx.config.MyApp.Companion.context
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
            val inputStream = context?.resources?.openRawResource(resId)
            // 条件同时满足 压缩文件存在、压缩文件大小未变、不进行覆盖操作
            if (file.exists() && SpUtil.instances.getInt(childPath) == inputStream?.available() && !isCover) {
                inputStream.close()
                return
            }
            // 压缩文件存在执行删除操作
            if (file.exists()) {
                file.delete()
            }
            // 重新创建压缩文件
            val available = inputStream?.available()
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(2048)
            var bytesRead: Int
            while (true) {
                bytesRead = inputStream?.read(buffer) as Int
                if (bytesRead == -1) break
                outputStream.write(buffer, 0, bytesRead)
            }
            inputStream.close()
            outputStream.close()
            SpUtil.instances.putInt(childPath, available as Int)
            LogUtils.e(TAG, "copyRaw->Succeed")
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.e(TAG, "copyRaw->Failed:" + childPath)
        }

    }

    /**
     * 拷贝资源文件到指定目录

     * @param fileName 资源文件名称
     * *
     * @param outPath  输出路径
     * *
     * @param isCover  是否覆写
     */
    fun copyAssets(fileName: String, outPath: String, isCover: Boolean) {
        createDirectory(File(outPath).parent)
        val file = File(outPath)

        try {
            val inputStream = context?.resources?.assets?.open(fileName)
            // 条件同时满足 压缩文件存在、压缩文件大小未变、不进行覆盖操作
            if (file.exists() && SpUtil.instances.getInt(outPath) == inputStream?.available() && !isCover) {
                inputStream.close()
                return
            }
            // 压缩文件存在执行删除操作
            if (file.exists()) {
                file.delete()
            }
            // 重新创建压缩文件
            val available = inputStream?.available()
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(2048)
            while (true) {
                val bytesRead = inputStream?.read(buffer)
                if (bytesRead == -1) break
                outputStream.write(buffer, 0, bytesRead as Int)
            }
            inputStream?.close()
            outputStream.close()
            SpUtil.instances.putInt(outPath, available as Int)
            LogUtils.i(TAG, "copyRaw->Succeed")
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.e(TAG, "copyRaw->Failed:" + outPath)
        }

    }

    /**
     * 获取资源文件内容

     * @param fileName 资源文件名称
     */
    fun getAssetsContent(fileName: String): String {
        var content = ""
        try {
            val inputStream = context?.resources?.assets?.open(fileName)
            val inputStreamReader = InputStreamReader(inputStream, "utf-8")
            val input = CharArray(inputStream?.available() as Int)
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
}
