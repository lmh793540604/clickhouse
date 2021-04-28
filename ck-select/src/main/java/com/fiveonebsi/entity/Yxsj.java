package com.fiveonebsi.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: Yxsj
 * @Description: 运行事件
 * @author: guoxiaobao
 * @date: 2020年09月01日 16:32
 */
@Data
public class Yxsj  {
    private String xsdwmc;

    private String xsdwdm;

    private String jcmc;

    private String jcdm;

    private String bdzdm;

    private String bdzmc;

    private String wzxldm;

    private String wzxlmc;

    private String ztflbm;

    private String ztflmc;

    private String tdsbdm;

    private String tdsbmc;

    private String jsyydm;

    private String jsyymc;

    private String zryydm;

    private String zryymc;

    private String zryyfmc;

    private String zhdqmc;

    private String zhdqdm;

    private String jgazwzmc;

    private String jgazwzdm;

    private String yjazwzmc;

    private String yjazwzdm;


    private String azwzdm;
    private String azwzmc;

    /**
     * 审批状态
     */
    private String dataType;


    //下属单位id
    private String xsdwId;

    //设施类型
    private String sslx;

    //停电设备零部件id
    private String tdsbdmId;

    //停电设备主设备id
    private String tdsbdmfId;

    //停电设备系统或设备id
    private String tdsbdmsId;

    //停电设备子系统或部件id
    private String tdsbdmtId;

    //技术原因代码
    private String jsyydmId;

    //责任原因代码
    private String zryydmId;

    //主责任原因代码
    private String zryydmfId;

    //电压等级
    private String dydj;

    //起始时间
    private Date qssj;

    //作业起始时间
    private Date zyqssj;

    //作业终止时间
    private Date zyzzsj;

    //终止时间
    private Date zzsj;

    //作业前备用时间
    private Double zyqbysj;

    //作业后备用时间
    private Double zyhbysj;

    //作业持续时间
    private Double zycxsj;

    private Double cxsj;

    //天气状况
    private String tqzk;

    //特殊分析编码
    private String tsyy;

    //计划停运分类名称
    private String jhtyfl;


    //计划延期
    private String jhyq;

    //设备更换
    private String sbgh;

    //任务号
    private String rwh;

    //任务说明
    private String rwsm;

    //参与统计
    private String cytj;

    //备注
    private String remark;

    //事件id
    private String eventId;

    //跨月
    private String ky1;

    //跨月
    private String ky2;

    //同步状态
    private String syncStatus;

    //注册id
    private String zcId;

    private String bdzdmId;

    private String cqby;

    private String jcId;

    private String sbId;

    private String ztfl;

    private String zhdqId;

    private String yjlx;

    private String yjId;
}
