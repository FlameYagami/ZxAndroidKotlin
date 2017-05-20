package com.zx.uitls

/**
 * Created by 八神火焰 on 2017/1/12.
 */

object StringUtils {
    fun changeList2String(list: List<*>): String {
        val builder = StringBuilder()
        for (str in list) {
            builder.append(str)
            builder.append("|")
        }
        val string = builder.toString()
        return string.substring(0, string.length - 1)
    }

    /**
     * 将string字符串中的小写字母改为大写

     * @param string 小写字符串
     * *
     * @return 大写字符串
     */
    fun toUpperCase(string: String): String {
        val tempCharArray = string.toCharArray()
        tempCharArray.indices
                .filter { tempCharArray[it] in 'a'..'z' }
                .forEach { tempCharArray[it] = Character.toUpperCase(tempCharArray[it]) }
        return String(tempCharArray)
    }
}
