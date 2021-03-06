package com.dab.zx.bean

import android.text.TextUtils
import com.dab.zx.config.MapConst
import com.dab.zx.config.SpConst
import com.dab.zx.uitls.JsonUtils
import com.dab.zx.uitls.SpUtils
import java.util.*

/**
 * Created by 八神火焰 on 2017/1/12.
 */

object KeySearchBean {
    private val keySearchMap = LinkedHashMap<String, Boolean>()

    init {
        for (key in MapConst.KeySearchMap.keys) {
            keySearchMap.put(key, true)
        }
    }

    private fun initKeySearchMap() {
        var keySearchJson = SpUtils.getString(SpConst.KeySearch)
        // Json异常处理
        if (TextUtils.isEmpty(keySearchJson)) {
            keySearchJson = JsonUtils.serializer(ArrayList<String>())
        }
        // 解析保存的关键字查询范围
        val keySearchList = JsonUtils.deserializerArray(keySearchJson, Array<String>::class.java)!!
        for (key in keySearchMap.keys) {
            for (keySearch in keySearchList) {
                if (key == keySearch) {
                    keySearchMap.put(key, true)
                    break
                }
            }
        }
    }

    val selectKeySearchList: List<String>
        get() {
            initKeySearchMap()
            return keySearchMap.filter { it.value }.map { it.key }
        }

    fun getKeySearchMap(): LinkedHashMap<String, Boolean> {
        initKeySearchMap()
        return keySearchMap
    }

    fun saveKeySearchMap(keySearchMap: LinkedHashMap<String, Boolean>) {
        val keySearchJson = JsonUtils.serializer(keySearchMap.filter { it.value }.map { it.key })
        SpUtils.putString(SpConst.KeySearch, keySearchJson)
    }
}
