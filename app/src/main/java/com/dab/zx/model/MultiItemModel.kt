package com.dab.zx.model

/**
 * Created by 八神火焰 on 2018/1/3.
 */
open class MultiItemModel<R, T> {

    private lateinit var header: Header<R>
    private lateinit var subitems: List<Subitem<T>>

    fun MultiItemModel(header: R, itemList: List<T>) {
        this.header = Header(header)
        this.subitems = itemList.map { it -> Subitem(it) }.toList()
    }

    fun MultiItemModel(header: Header<R>, subitemList: List<Subitem<T>>) {
        this.header = header
        this.subitems = subitemList
    }

    fun getHeader(): Header<R> {
        return header
    }

    fun getSubitems(): List<Subitem<T>> {
        return subitems
    }

    class Header<out R>(val data: R) {
        var isExpanded: Boolean = false

        init {
            isExpanded = true
        }
    }

    class Subitem<out T>(val data: T)
}