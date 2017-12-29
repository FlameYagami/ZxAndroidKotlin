package com.dab.zx.config

/**
 * Created by 八神火焰 on 2016/12/13.
 */

interface SQLitConst {
    companion object {
        val TableName = "TableCard"
        val ColumnMd5 = "md5"
        val ColumnType = "type"
        val ColumnCamp = "camp"
        val ColumnRace = "race"
        val ColumnSign = "sign"
        val ColumnRare = "rare"
        val ColumnPack = "pack"
        val ColumnCName = "cname"
        val ColumnJName = "jname"
        val ColumnIllust = "illust"
        val ColumnNumber = "number"
        val ColumnCost = "cost"
        val ColumnPower = "power"
        val ColumnLines = "lines"
        val ColumnImage = "image"
        val ColumnAbility = "ability"
        val ColumnAbilityDetail = "abilitydetail" // 存放Json数据的字段
    }
}
