package com.fiveonebsi.entity;

/**
 * @ClassName: ZbtjEnum
 * @Description: TODO
 * @author: guoxiaobao
 * @date: 2020年10月22日 9:31
 */
public enum ZbfxEnum {

    /**
    *      任务表（tj_task）中统计类型（tjlx）
    */

    single("单台指标统计","0"),
    multiple("综合指标统计","1"),
    sjdw("设计单位指标分析","2"),
    zzdw("制造单位指标分析","3"),
    sgdw("施工单位指标分析","4"),
    ssxs("设施型式指标分析","5"),
    xhgg("型号规格指标分析","6"),
    zryy("责任原因指标分析","7"),
    sbbj("设备部件指标分析","8"),
    jsyy("技术原因指标分析","9"),
    sjsb("设计单位和设备因素指标分析","10"),
    zzsb("制造单位和设备因素指标分析","11"),
    sgsb("施工单位和设备因素指标分析","12"),
    zhqs("综合指标变化趋势分析","13"),
    sbbky("设备不可用分析","14"),
    sbnx("设备年限分析","15"),
    sjhz("数据汇总","16"),
    cftd("重复停电分析","17");



    private String name;
    private String value;
    private ZbfxEnum(String name, String value){
        this.name=name;
        this.value=value;
    }

    public String getValue(){
        return this.value;
    }
    public String getName(){
        return this.name;
    }
}
