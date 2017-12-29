package com.dab.zx.uitls

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by 时空管理局 on 2016/7/12.
 */
object JsonUtils {
    private val TAG = JsonUtils::class.java.simpleName

    /**
     * 序列化

     * @param obj 对象
     * *
     * @return Json
     */
    fun serializer(obj: Any): String {
        val gson = GsonBuilder().create()
        Log.e(TAG, gson.toJson(obj))
        return gson.toJson(obj)
    }

    /**
     * 反序列化对象

     * @param jsonData Json
     * *
     * @return 对象
     */
    fun <T> deserializer(jsonData: String, classType: Class<T>): T? {
        try {
            val gson = Gson()
            val result = gson.fromJson(jsonData, classType)
            return result
        } catch (e: Exception) {
            LogUtils.e(TAG, "deserializer->error")
            return null
        }

    }

    /**
     * 反序列化数组对象

     * @param jsonData Json
     * *
     * @return 数组集合
     */
    fun <T> deserializerArray(jsonData: String, clazz: Class<Array<T>>): List<T>? {
        try {
            val gson = Gson()
            val array = gson.fromJson(jsonData, clazz)
            return Arrays.asList(*array)
        } catch (e: Exception) {
            LogUtils.e(TAG, "deserializerArray->error")
            return null
        }

    }

    /**
     * 反序列化集合对象

     * @param jsonData Json
     * *
     * @return 对象集合
     */
    fun <T> deserializerList(jsonData: String, classType: Class<T>): List<T>? {
        val arrayList = ArrayList<T>()
        try {
            val type = object : TypeToken<ArrayList<JsonObject>>() {

            }.type
            val jsonObjects = Gson().fromJson<List<JsonObject>>(jsonData, type)
            jsonObjects.mapTo(arrayList) { Gson().fromJson(it, classType) }
        } catch (e: Exception) {
            LogUtils.e(TAG, "deserializerList->error")
            return null
        }

        return arrayList
    }
}
