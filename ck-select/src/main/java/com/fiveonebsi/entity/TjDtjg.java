package com.fiveonebsi.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 单台指标统计结果-宽表(TjDtjg)实体类
 *
 * @author yangjun
 * @since 2020-09-12 15:33:15
 */
@Data
public class TjDtjg {
    /**
     * 任务ID
     */
    private String rwId;
    /**
     * 设备类别
     */
    private String sblb;
    /**
     * 局厂代码
     */
    private String jcdm;
    /**
     * 局厂名称
     */
    private String jcmc;
    /**
     * 下属单位代码
     */
    private String xsdwdm;
    /**
     * 下属单位名称
     */
    private String xsdwmc;
    /**
     * 变电站代码
     */
    private String bdzdm;
    /**
     * 变电站名称
     */
    private String bdzmc;

    /**
     * 安装位置代码，组合电器的
     */
    private String azwzdm;

    /**
     * 安装位置名称，组合电器的
     */
    private String azwzmc;
    /**
     * 电压等级
     */
    private String dydj;
    /**
     * 线路长度/设备容量
     */
    private String xlcd;
    /**
     * 资产性质
     */
    private String zcxz;
    /**
     * 资产单位
     */
    private String zcdw;
    /**
     * 投运日期
     */
    private Date tyrq;
    /**
     * 施工单位名称
     */
    private String sgdwmc;
    /**
     * 设计制造单位名称
     */
    private String sjzzdwmc;
    /**
     * 设计制造单位类型
     */
    private String sjzzdwlx;
    /**
     * 单位代码
     */
    private String dwdm;
    /**
     * 电网单位
     */
    private String dwdw;
    /**
     * 调度单位
     */
    private String dddw;
    /**
     * 设备ID
     */
    private String sbId;
    /**
     * 设备型式
     */
    private String sbxs;
    /**
     * 型号规格
     */
    private String xhgg;
    /**
     * 起始时间
     */
    private Date qssj;
    /**
     * 终止时间
     */
    private Date zzsj;
    /**
     * 统计期间小时
     */
    private BigDecimal tjqjxs;
    /**
     * 设备台年数
     */
    private BigDecimal sbtns;
    /**
     * 可用小时
     */
    private BigDecimal kyxs;
    /**
     * 运行小时
     */
    private BigDecimal yxxs;
    /**
     * 备用小时
     */
    private BigDecimal byxs;
    /**
     * 调度停运备用小时
     */
    private BigDecimal ddtybyxs;
    /**
     * 作业前受累停运备用小时
     */
    private BigDecimal zyqsltybyxs;
    /**
     * 作业后受累停运备用小时
     */
    private BigDecimal zyhsltybyxs;
    /**
     * 受累停运备用小时
     */
    private BigDecimal sltybyxs;
    /**
     * 不可用小时
     */
    private BigDecimal bkyxs;
    /**
     * 计划停运小时
     */
    private BigDecimal jhtyxs;
    /**
     * 大修停运小时
     */
    private BigDecimal dxtyxs;
    /**
     * 小修停运小时
     */
    private BigDecimal xxtyxs;
    /**
     * 试验停运小时
     */
    private BigDecimal sytyxs;
    /**
     * 清扫停运小时
     */
    private BigDecimal qstyxs;
    /**
     * 改造施工停运小时
     */
    private BigDecimal gzsgtyxs;
    /**
     * 非计划停运小时
     */
    private BigDecimal fjhtyxs;
    /**
     * 第一类非计划停运小时
     */
    private BigDecimal dylfjhtyxs;
    /**
     * 第二类非计划停运小时
     */
    private BigDecimal delfjhtyxs;
    /**
     * 第三类非计划停运小时
     */
    private BigDecimal dslfjhtyxs;
    /**
     * 第四类非计划停运小时
     */
    private BigDecimal dsilfjhtyxs;
    /**
     * 强迫停运小时
     */
    private BigDecimal qptyxs;
    /**
     * 备用次数
     */
    private Integer bycs;
    /**
     * 调度停运备用次数
     */
    private Integer ddtybycs;
    /**
     * 作业前受累停运备用次数
     */
    private Integer zyqsltybycs;
    /**
     * 作业后受累停运备用次数
     */
    private Integer zyhsltybycs;
    /**
     * 受累停运备用次数
     */
    private Integer sltybycs;
    /**
     * 计划停运次数
     */
    private Integer jhtycs;
    /**
     * 大修次数
     */
    private Integer dxcs;
    /**
     * 小修次数
     */
    private Integer xxcs;
    /**
     * 试验次数
     */
    private Integer sycs;
    /**
     * 清扫次数
     */
    private Integer qscs;
    /**
     * 改造施工次数
     */
    private Integer gzsgcs;
    /**
     * 非计划停运次数
     */
    private Integer fjhsgcs;
    /**
     * 第一类非计划停运次数
     */
    private Integer dylfjhtycs;
    /**
     * 第一类非停运(重合成功)次数
     */
    private Integer dylftycs;
    /**
     * 第二类非计划停运次数
     */
    private Integer delfjhtycs;
    /**
     * 第三类非计划停运次数
     */
    private Integer dslfjhtycs;
    /**
     * 第四类非计划停运次数
     */
    private Integer dsilfjhtycs;
    /**
     * 强迫停运次数
     */
    private Integer qptycs;
    /**
     * 计划停运系数
     */
    private BigDecimal jhtyxishu;
    /**
     * 非计划停运系数
     */
    private BigDecimal fjhtyxishu;
    /**
     * 强迫停运系数
     */
    private BigDecimal qptyxishu;
    /**
     * 可用系数
     */
    private BigDecimal kyxishu;
    /**
     * 运行系数
     */
    private BigDecimal yxxishu;
    /**
     * 计划停运率
     */
    private BigDecimal jhtyl;
    /**
     * 非计划停运率
     */
    private BigDecimal fjhtyl;
    /**
     * 强迫停运率
     */
    private BigDecimal qptyl;
    /**
     * 暴露率
     */
    private BigDecimal bll;
    /**
     * 连续可用小时
     */
    private BigDecimal lxkyxs;

    /**
     * 是否在运行
     */

    private String sfzyx;

    /**
     * 额定容量
     */

    private BigDecimal edrl;

    /**
     * 间隔安装位置代码
     */
    private String jgazwzdm;

    /**
     * 间隔安装位置名称
     */
    private String jgazwzmc;

    /**
     * 元件安装位置代码
     */
    private String yjazwzdm;

    /**
     * 元件安装位置名称
     */
    private String yjazwzmc;

    /**
     * 组合电器和间隔计算,0:按元件加权,1:按事件合并，本字段来自于TASK表
     */
    private String zhdqhjgjs;

    /**
     * 组合电器ID
     */
    private String zhdqId;

    /**
     * 组合电器间隔ID
     */
    private String zhdqjgId;
    /**
     * 重复停电次数
     */
    private Integer cftdcs;

    // 非计划停运作业持续时间<5h的非计划停运次数
    private int fjhtycs_0_5;

    // 非计划停运作业持续时间在5-100h之间的非计划停运次数
    private int fjhtycs_5_100;

    // 非计划停运作业持续时间在100-500h之间的非计划停运次数
    private int fjhtycs_100_500;

    // 非计划停运作业持续时间在500-800h之间的非计划停运次数
    private int fjhtycs_500_800;

    // 非计划停运作业持续时间>=800h的非计划停运次数
    private int fjhtycs_800;

    /**
     * 所属综合结果ID，本单台结果属于哪一个综合结果。本字段只有在综合指标统计的组合电器或者间隔才使用。
     */
    private String zhjgId;

    /**
     * 所属多台结果ID，本单台结果属于哪一个多台结果。本字段只有在综合指标统计的组合电器或者间隔才使用。
     */
    private String duotaijgId;

    private String jhyq;

    private String sbgh;

    /**
     * 设备百台年数
     */
    private BigDecimal sbbtns;

    private String lx;

    private String eventId;
}