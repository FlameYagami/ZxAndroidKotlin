package com.zx.config

import com.zx.R
import java.util.*

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
    val AbilityDetailMap = LinkedHashMap<String, String>()

    init {
        GuideMap.put("B18", R.drawable.ic_guide_b18)
        GuideMap.put("B19", R.drawable.ic_guide_b19)
        GuideMap.put("B20", R.drawable.ic_guide_b20)
        GuideMap.put("C17", R.drawable.ic_guide_c17)
        GuideMap.put("E07", R.drawable.ic_guide_e07)
        GuideMap.put("E08", R.drawable.ic_guide_e08)

        //        RareMap.put("CVR",R.drawable.img_rare_cvr);
        //        RareMap.put("IGR",R.drawable.img_rare_igr);
        //        RareMap.put("ZX/R",R.drawable.img_rare_zxr);
        //        RareMap.put("DR",R.drawable.img_rare_dr);
        //        RareMap.put("UR",R.drawable.img_rare_ur);
        //        RareMap.put("SR",R.drawable.img_rare_sr);
        //        RareMap.put("R",R.drawable.img_rare_r);
        //        RareMap.put("UC",R.drawable.img_rare_uc);
        //        RareMap.put("C",R.drawable.img_rare_c);
        //        RareMap.put("PR",R.drawable.img_rare_pr);

        SignMap.put("点燃", R.drawable.ic_sign_ig)
        SignMap.put("觉醒之中", R.drawable.ic_sign_el)

        CampMap.put("红", R.drawable.ic_camp_red)
        CampMap.put("蓝", R.drawable.ic_camp_blue)
        CampMap.put("白", R.drawable.ic_camp_white)
        CampMap.put("黑", R.drawable.ic_camp_black)
        CampMap.put("绿", R.drawable.ic_camp_green)
        CampMap.put("无", R.drawable.ic_camp_void)

        KeySearchMap.put("卡名", SQLitConst.ColumnCName)
        KeySearchMap.put("日名", SQLitConst.ColumnJName)
        KeySearchMap.put("种类", SQLitConst.ColumnType)
        KeySearchMap.put("阵营", SQLitConst.ColumnCamp)
        KeySearchMap.put("种族", SQLitConst.ColumnRace)
        KeySearchMap.put("标记", SQLitConst.ColumnSign)
        KeySearchMap.put("费用", SQLitConst.ColumnCost)
        KeySearchMap.put("力量", SQLitConst.ColumnPower)
        KeySearchMap.put("卡编", SQLitConst.ColumnNumber)
        KeySearchMap.put("罕贵", SQLitConst.ColumnRare)
        KeySearchMap.put("画师", SQLitConst.ColumnIllust)
        KeySearchMap.put("能力", SQLitConst.ColumnAbility)

        AbilityTypeMap.put("Lv", "Lv")
        AbilityTypeMap.put("射程", "【常】射程")
        AbilityTypeMap.put("绝界", "【常】")
        AbilityTypeMap.put("起始卡", "【常】起始卡")
        AbilityTypeMap.put("生命恢复", "【常】生命恢复")
        AbilityTypeMap.put("虚空使者", "【常】虚空使者")
        AbilityTypeMap.put("进化原力", "【自】进化原力")
        AbilityTypeMap.put("零点优化", "【※】零点优化")

        AbilityDetailMap.put("资源联动", "")
        AbilityDetailMap.put("方阵联动", "")
        AbilityDetailMap.put("充能联动", "")
        AbilityDetailMap.put("卡片登场", "")
        AbilityDetailMap.put("卡片破坏", "")
        AbilityDetailMap.put("卡片除外", "")
        AbilityDetailMap.put("返回手牌", "")
        AbilityDetailMap.put("返回卡组", "")
        AbilityDetailMap.put("资源放置", "")
        AbilityDetailMap.put("方阵置换", "")
        AbilityDetailMap.put("充能放置", "")
        AbilityDetailMap.put("废弃放置", "")
        AbilityDetailMap.put("重启休眠", "")
        AbilityDetailMap.put("抽卡辅助", "")
        AbilityDetailMap.put("卡组检索", "")
        AbilityDetailMap.put("充能上限", "")
        AbilityDetailMap.put("玩家相关", "")
        AbilityDetailMap.put("费用相关", "")
        AbilityDetailMap.put("力量相关", "")
        AbilityDetailMap.put("种族相关", "")
        AbilityDetailMap.put("伤害相关", "")
        AbilityDetailMap.put("原力相关", "")
        AbilityDetailMap.put("标记相关", "")
        AbilityDetailMap.put("生命相关", "")
        AbilityDetailMap.put("特殊胜利", "")
    }
}
