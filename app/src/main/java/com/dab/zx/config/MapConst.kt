package com.dab.zx.config

import com.dab.zx.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by 八神火焰 on 2016/12/13.
 */

object MapConst {
    val GuideMap = HashMap<String, Int>()
    val SignMap = HashMap<String, Int>()
    val CampMap = HashMap<String, Int>()
    val RareMap = HashMap<String, Int>()
    val KeySearchMap = LinkedHashMap<String, String>()
    val AbilityTypeMap = LinkedHashMap<String, String>()
    val AbilityDetailList = ArrayList<String>()

    init {
        GuideMap.put("B18", R.drawable.ic_guide_b18)
        GuideMap.put("B19", R.drawable.ic_guide_b19)
        GuideMap.put("B20", R.drawable.ic_guide_b20)
        GuideMap.put("C17", R.drawable.ic_guide_c17)
        GuideMap.put("E07", R.drawable.ic_guide_e07)
        GuideMap.put("E08", R.drawable.ic_guide_e08)

        SignMap.put("点燃", R.drawable.ic_sign_ig)
        SignMap.put("觉醒之中", R.drawable.ic_sign_el)

        CampMap.put("红", R.drawable.ic_camp_red)
        CampMap.put("蓝", R.drawable.ic_camp_blue)
        CampMap.put("白", R.drawable.ic_camp_white)
        CampMap.put("黑", R.drawable.ic_camp_black)
        CampMap.put("绿", R.drawable.ic_camp_green)
        CampMap.put("无", R.drawable.ic_camp_void)

        KeySearchMap.put("卡名", SQLitConst.ColumnCName)
        KeySearchMap.put("卡编", SQLitConst.ColumnNumber)
        KeySearchMap.put("画师", SQLitConst.ColumnIllust)
        KeySearchMap.put("能力", SQLitConst.ColumnAbility)

        AbilityTypeMap.put("Lv", "Lv")
        AbilityTypeMap.put("射程", "【常】射程")
        AbilityTypeMap.put("绝界", "【常】绝界")
        AbilityTypeMap.put("起始卡", "【常】起始卡")
        AbilityTypeMap.put("生命恢复", "【常】生命恢复")
        AbilityTypeMap.put("虚空使者", "【常】虚空使者")
        AbilityTypeMap.put("进化原力", "【自】进化原力")
        AbilityTypeMap.put("零点优化", "【※】零点优化")

        AbilityDetailList.add("资源联动")
        AbilityDetailList.add("方阵联动")
        AbilityDetailList.add("充能联动")
        AbilityDetailList.add("卡片登场")
        AbilityDetailList.add("卡片破坏")
        AbilityDetailList.add("卡片除外")
        AbilityDetailList.add("返回手牌")
        AbilityDetailList.add("返回卡组")
        AbilityDetailList.add("资源放置")
        AbilityDetailList.add("方阵置换")
        AbilityDetailList.add("充能放置")
        AbilityDetailList.add("废弃放置")
        AbilityDetailList.add("重启休眠")
        AbilityDetailList.add("抽卡辅助")
        AbilityDetailList.add("卡组检索")
        AbilityDetailList.add("充能上限")
        AbilityDetailList.add("玩家相关")
        AbilityDetailList.add("费用相关")
        AbilityDetailList.add("力量相关")
        AbilityDetailList.add("种族相关")
        AbilityDetailList.add("伤害相关")
        AbilityDetailList.add("原力相关")
        AbilityDetailList.add("标记相关")
        AbilityDetailList.add("生命相关")
        AbilityDetailList.add("特殊胜利")
    }
}
