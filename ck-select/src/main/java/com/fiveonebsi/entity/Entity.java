package com.fiveonebsi.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Entity {

    //设备类型
    private String sblx;

    //安装位置代码
    private String azwzdm;

    //安装位置名称
    private String azwzmc;

    //变动分类
    private String bdfl;

    //变动号
    private String bdh;

    //变动类型
    private String bdlx;

    //变动日期
    private Date bdrq;

    //变动原因
    private String bdyy;

//    变电站代码
    private String bdzdm;

//    变电站名称
    private String bdzmc;


//    getId
    private String bdzId;

//    出厂编号
    private String ccbh;

//    出厂日期
    private Date ccrq;

//    持续时间
    private BigDecimal cxsj;

    private String cytj;

    private String dataType;

//    调度单位
    private String dddw;

//    调度单位ID
    private String dddwId;

    private String dlgs;

    private String dlzdt;

    private String dlzjt;

    private Date dtccrq;

    private String dtzzc;

    private String dydj;

    private String eddl;

    private String eventId;

    private String id;

    private String jcdm;

    private String jcmc;

    private Date jcrq;

    private String jcId;

    private String jhtyfl;

    private String jhyq;

    private String jsyydm;

    private String jsyydmId;

    private String jsyymc;

    private String jxfs;

    private Date jyccrq;

    private String jycljs;

    private String jyzzc;

    private String jyzzcId;

    private String ky1;

    private String ky2;

    private String mxxs;

    private String mxxsId;

    private String pdbj;

    private Date qssj;

    private String qybm;

    private String qyjb;

    private String qylx;

    private String remark;

    private String rerunFlag;

    private String rwh;

    private String rwsm;

    private String sbgh;

    private String sbly;

    private String sbxs;

    private String sbxsId;

    private String sbId;

    private String sdbz;

    private String sgdw;

    private String sjdw;

    private String sslx;

    private String szdw;

    private String szdwId;

    private Date tcrq;

    private String tdsbdm;

    private String tdsbdmId;

    private String tdsbmc;

    private Date touyunrq;

    private String tqzk;

    private String tsyy;

    private Date tuiyirq;

    private String tz;

    private String wzxldm;

    private String wzxlmc;

    private String xhgg;

    private String xhggId;

    private String xlcd;

    private String xldm;

    private String xlfds;

    private String xllb;

    private String xlmc;

    private String xlxz;

    private String xlId;

    private String xsdwdm;

    private String xsdwmc;

    private String xsdwId;

    private String yxid;

    private String yxzddl;

    private String yz;

    private String zcd;

    private Date zcrq;

    private String zcsx;

    private String zcsxId;

    private String zcId;

    private String zryydm;

    private String zryydmfId;

    private String zryydmId;

    private String zryyfmc;

    private String zryymc;

    private String zspl;

    private String ztfl;

    private String ztflbm;

    private String ztflmc;

    private BigDecimal zycxsj;

    private BigDecimal zyhbysj;

    private BigDecimal zyqbysj;

    private Date zyqssj;

    private Date zyzzsj;

    private String zzdw;

    private String zzdwId;

    private Date zzsj;

    private List<Yxsj> yxsjList;
}
