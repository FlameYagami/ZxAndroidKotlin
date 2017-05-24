package com.zx.bean

import android.text.TextUtils
import com.zx.config.MapConst
import com.zx.config.SpConst
import com.zx.uitls.JsonUtils
import com.zx.uitls.SpUtil
import java.util.*

/**
 * Created by 八神火焰 on 2017/1/12.
 */

object KeySearchBean {
    private val keySearchMap = LinkedHashMap<String, Boolean>()

    init {
        for ((key) in MapConst.KeySearchMap) {
            keySearchMap.put(key, true)
        }
    }

    private fun initKeySearchMap() {
        var keySearchJson = SpUtil.instance.getString(SpConst.KeySearch)
        // Json异常处理
        if (TextUtils.isEmpty(keySearchJson)) {
            keySearchJson = JsonUtils.serializer(ArrayList<String>())
        }
        // 解析保存的关键字查询范围
        val keySearchList = JsonUtils.deserializerArray(keySearchJson, Array<String>::class.java)!!
        for ((key) in keySearchMap) {
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
        val keySearchJson = JsonUtils.serializer(KeySearchBean.keySearchMap.filter { it.value }.map { it.key })
        SpUtil.instance.putString(SpConst.KeySearch, keySearchJson)
    }
}
