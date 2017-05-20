package com.zx.config

/**
 * Created by 八神火焰 on 2016/12/13.
 */

interface SQLitConst {
    companion object {
        val TableName = "TableCard"
        val ColumnMd5 = "Md5"
        val ColumnType = "type"
        val ColumnCamp = "camp"
        val ColumnRace = "Race"
        val ColumnSign = "Sign"
        val ColumnRare = "Rare"
        val ColumnPack = "Pack"
        val ColumnCName = "CName"
        val ColumnJName = "JName"
        val ColumnIllust = "Illust"
        val ColumnNumber = "Number"
        val ColumnCost = "Cost"
        val ColumnPower = "Power"
        val ColumnLines = "Lines"
        val ColumnFaq = "Faq"
        val ColumnImage = "Image"
        val ColumnAbility = "Ability"
        val ColumnAbilityDetail = "AbilityDetail" // 存放Json数据的字段
    }
}
