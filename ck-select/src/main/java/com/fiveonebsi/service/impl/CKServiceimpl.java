package com.fiveonebsi.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.fiveonebsi.Utils.BigdecimalUtil;
import com.fiveonebsi.Utils.ParentIdUtil;
import com.fiveonebsi.dao.Testdao;
import com.fiveonebsi.entity.*;
import com.fiveonebsi.service.CKService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/*
*
*
*/
@Service
public class CKServiceimpl implements CKService {

    @Autowired
    private Testdao testdao;

    @Override
    public List<Entity> getreslut() {

        return testdao.all_count();
    }

    @Override
    public List<TjDtjg> demo(TaskVo taskVo) throws ParseException {
        // 拆分计算
        List<Entity> demo = testdao.demo(taskVo.getParseIds(),taskVo.getStartTime(),taskVo.getEndTime(),null,null,null,taskVo.getTjsb(),taskVo.getDydj(),null,taskVo.getPageSize(),taskVo.getPageNum());
        List tjDtjgs=new ArrayList<>();
        Map<String, List<Entity>> collect = demo.stream().collect(Collectors.groupingBy(Entity::getId));
        Set<String> strings = collect.keySet();
//        String sa="(";
//        StringBuilder sb=new StringBuilder(sa);
//        StringBuilder sb1=new StringBuilder(sa);
        strings.forEach(s->{
            List<Entity> entities = collect.get(s);
            if(entities!=null&&entities.size()>0){
                Entity entity = entities.get(0);
                Date date = Optional.ofNullable(entity.getTcrq()).orElse(entity.getTuiyirq());
                TjDtjg tjDtjg= assemblyJg(entities, taskVo.getTjlx(), entity.getZcrq(), date, taskVo.getStartTime(), taskVo.getEndTime());
                tjDtjg.setAzwzdm(entity.getAzwzdm());
                tjDtjg.setAzwzmc(entity.getAzwzmc());
                tjDtjg.setJcdm(entity.getJcdm());
                tjDtjg.setJcmc(entity.getJcmc());
                tjDtjg.setXsdwdm(entity.getXsdwdm());
                tjDtjg.setXsdwmc(entity.getXsdwmc());
                tjDtjg.setBdzdm(entity.getBdzdm());
                tjDtjg.setBdzmc(entity.getBdzmc());
                tjDtjg.setDydj(entity.getDydj());
//        dtjg.setXlcd("");
//        dtjg.setZcxz("");
//        dtjg.setZcdw("");
                tjDtjg.setTyrq(entity.getTouyunrq());
                tjDtjg.setSjzzdwmc(entity.getZzdw());
//                tjDtjg.setSjzzdwlx(getQyLx(entity.getZzdwId()));
                tjDtjg.setRwId(taskVo.getTaskId());
                tjDtjg.setSbxs(entity.getSbxs());
                tjDtjg.setXhgg(entity.getXhgg());
                tjDtjg.setSbId(entity.getSbId());
                tjDtjg.setQssj(taskVo.getStartTime());
                tjDtjg.setZzsj(taskVo.getEndTime());
                tjDtjg.setSblb(entity.getSblx());
                if ((Constant.JKXL.getValue().equals(entity.getSblx())||Constant.DLXL.equals(entity.getSblx()))&&"0".equals(taskVo.getXljsff())) {
                    BigDecimal ratio = BigDecimal.valueOf(entity.getXlcd()==null?entity.getXlcd():0)
                            .divide(BigDecimal.valueOf(100),9, RoundingMode.HALF_UP);
                    //只改设备台年，不改统计期间小时
                    tjDtjg.setSbtns(ratio.multiply(tjDtjg.getSbtns()));
                }
                tjDtjgs.add(tjDtjg);

//                if(Strings.isNotBlank(entities.get(0).getId())){
//                    sb.append("'"+(entities.get(0).getId())+"',");
//                }
//                sb1.append(("'"+(entities.get(0).getId())+"',"));
            }
        });
//        TEST test=new TEST();
//        String s = sb.toString();
//        String[] split = s.split(",");
//        System.out.println(split.length);
//        test.setBdzmc(s);
//        test.setAzwzdm(sb1.toString());
//        tjDtjgs.add(test);
        return tjDtjgs;
    }

    @Override
    public TaskEntity getById(String taskId) {
        return testdao.getById(taskId);
    }

    @Override
    public String demoSave(TaskVo taskVo) {
        List<String> validIds = ParentIdUtil.getValidIds(taskVo.getIds());
        taskVo.setParseIds(validIds);
        taskVo.setTaskId(IdUtil.fastUUID());
        TaskEntity taskEntity = new TaskEntity();
        taskVo.getTaskEntity(taskVo, taskEntity);
        testdao.save(taskEntity);
        return taskEntity.getTaskId();
    }

    @Override
    public List<TaskEntity> page() {
        return testdao.page();
    }

    private TjDtjg assemblyJg(List<Entity> device,String tjlx, Date zcrq, Date t,Date tjqssj,Date tjzzsj) {
        long tjqjH = 0;
        int i1 = DateUtil.compare(zcrq, tjqssj);
        int i2 = DateUtil.compare(t, tjzzsj);
        String sfzyx = "0";
        //统计时间计算
        if (i1 >= 0) {
            if (t == null || i2 > 0) {
                tjqjH = DateUtil.between(zcrq, tjzzsj, DateUnit.MINUTE);
                sfzyx = "1";
            } else if (i2 < 0) {
                tjqjH = DateUtil.between(zcrq, t, DateUnit.MINUTE);
            }
        } else {
            if (t == null || i2 > 0) {
                tjqjH = DateUtil.between(tjqssj, tjzzsj, DateUnit.MINUTE);
                sfzyx = "1";
            } else if (i2 < 0) {
                tjqjH = DateUtil.between(tjqssj, t, DateUnit.MINUTE);
            }
        }

        //调度停运备用小时数
        long ddtybyxss = 0;
        //调度停运备用次数
        int ddtybycs = 0;

        //受累停运备用小时数
        long sltybyxss = 0;
        //受累停运备用次数
        int sltybycs = 0;

        //作业前备用小时数
        long zyqbyxss = 0;
        //作业前备用次数
        int zyqbycs = 0;

        //作业后备用小时数
        long zyhbyxss = 0;
        //作业后备用次数
        int zyhbycs = 0;

        //大修停运小时数
        long dxtyxss = 0;
        //大修停运次数
        int dxcs = 0;

        //小修停运小时数
        long xxtyxss = 0;
        //小修次数
        int xxcs = 0;

        //试验停运小时数
        long sytyxss = 0;
        //试验次数
        int sycs = 0;

        //清扫停运小时数
        long qstyxss = 0;
        //清扫次数
        int qscs = 0;

        //改造施工停运小时数
        long gzsgtyxss = 0;
        //改造施工次数
        int gzsgcs = 0;

        //第一类非计划停运小时数
        long fjhtyxss1 = 0;
        //第一类非计划停运次
        int fjhtycs1 = 0;

        //第二类非计划停运小时数
        long fjhtyxss2 = 0;
        //第二类非计划停运次
        int fjhtycs2 = 0;

        //第三类非计划停运小时数
        long fjhtyxss3 = 0;
        //第三类非计划停运次
        int fjhtycs3 = 0;

        //第四类非计划停运小时数
        long fjhtyxss4 = 0;
        //第四类非计划停运次
        int fjhtycs4 = 0;

        int chcgcs = 0;

        String jhyq = "";
        String sbgh = "";

        // 将运行事件中间结果数据累加
        for (Entity entity : device) {
            if(entity.getQssj()==null||entity.getZzsj()==null||"LO".equals(entity.getZtfl())||tjzzsj.compareTo(entity.getQssj())<1||tjqssj.compareTo(entity.getZzsj())>0||tjzzsj.compareTo(entity.getZzsj())==0){
                continue;
            }
            String ztfl = entity.getZtfl();
            // 单条运行事件取中间数据
            Map<String, Long> p = getParams(entity, tjqssj,tjzzsj);
            zyqbyxss += p.get("zyqbyxss");
            zyqbycs += p.get("zyqbycs");
            zyhbyxss += p.get("zyhbyxss");
            zyhbycs += p.get("zyhbycs");
            jhyq = entity.getJhyq();
            sbgh = entity.getSbgh();
            //带电作业"LO",调度停运备用"DR",受累停运备用"PR",大修停运"PO1",小修停运"PO2"
            // ,实验停运"TO",清扫停运"CO",改造施工停运"RCO",第一类非计划停运"UO1",第二类非计划停运"UO2"
            // ,第三类非计划停运"UO3",第四类非计划停运"UO4"
            if ("DR".equals(ztfl)) {
                ddtybyxss += p.get("zyxss");
                ddtybycs += p.get("zycs");
            } else if ("PR".equals(ztfl)) {
                sltybyxss += p.get("zyxss");
                sltybycs += p.get("zycs");
            } else if ("PO1".equals(ztfl)) {
                dxtyxss += p.get("zyxss");
                dxcs += p.get("zycs");
            } else if ("PO2".equals(ztfl)) {
                xxtyxss += p.get("zyxss");
                xxcs += p.get("zycs");
            } else if ("TO".equals(ztfl)) {
                sytyxss += p.get("zyxss");
                sycs += p.get("zycs");
            } else if ("CO".equals(ztfl)) {
                qstyxss += p.get("zyxss");
                qscs += p.get("zycs");
            } else if ("RCO".equals(ztfl)) {
                gzsgtyxss += p.get("zyxss");
                gzsgcs += p.get("zycs");
            } else if ("UO1".equals(ztfl)) {
                fjhtyxss1 += p.get("zyxss");
                fjhtycs1 += p.get("zycs");
                chcgcs += p.get("chcgcs") != null ? p.get("chcgcs") : 0;
            } else if ("UO2".equals(ztfl)) {
                fjhtyxss2 += p.get("zyxss");
                fjhtycs2 += p.get("zycs");
            } else if ("UO3".equals(ztfl)) {
                fjhtyxss3 += p.get("zyxss");
                fjhtycs3 += p.get("zycs");
            } else if ("UO4".equals(ztfl)) {
                fjhtyxss4 += p.get("zyxss");
                fjhtycs4 += p.get("zycs");
            }

        }

        TjDtjg tjDtjg = new TjDtjg();
        //大修停运小时、次数
        tjDtjg.setDxtyxs(BigdecimalUtil.divide(dxtyxss, 60, 9));
        tjDtjg.setDxcs(dxcs);

        //小修停运小时、次数
        tjDtjg.setXxtyxs(BigdecimalUtil.divide(xxtyxss, 60, 9));
        tjDtjg.setXxcs(xxcs);

        //实验停运小时、次数
        tjDtjg.setSytyxs(BigdecimalUtil.divide(sytyxss, 60, 9));
        tjDtjg.setSycs(sycs);

        //清扫停运小时次数
        tjDtjg.setQstyxs(BigdecimalUtil.divide(qstyxss, 60, 9));
        tjDtjg.setQscs(qscs);

        //改造施工停运小时、次数
        tjDtjg.setGzsgtyxs(BigdecimalUtil.divide(gzsgtyxss, 60, 9));
        tjDtjg.setGzsgcs(gzsgcs);

        //调度停运小时、次数
        tjDtjg.setDdtybyxs(BigdecimalUtil.divide(ddtybyxss, 60, 9));
        tjDtjg.setDdtybycs(ddtybycs);

        //第一类非计划停运小时、次数
        tjDtjg.setDylfjhtyxs(BigdecimalUtil.divide(fjhtyxss1, 60, 9));
        tjDtjg.setDylfjhtycs(fjhtycs1);

        //第二类非计划停运小时、次数
        tjDtjg.setDelfjhtyxs(BigdecimalUtil.divide(fjhtyxss2, 60, 9));
        tjDtjg.setDelfjhtycs(fjhtycs2);

        //第三类非计划停运小时、次数
        tjDtjg.setDslfjhtyxs(BigdecimalUtil.divide(fjhtyxss3, 60, 9));
        tjDtjg.setDslfjhtycs(fjhtycs3);

        //第四类非计划停运小时、次数
        tjDtjg.setDsilfjhtyxs(BigdecimalUtil.divide(fjhtyxss4, 60, 9));
        tjDtjg.setDsilfjhtycs(fjhtycs4);

        // 受累停运备用小时/次数     作业前备用小时/次数+作业后备用小时/次数+受累停运小时/次数
        tjDtjg.setSltybyxs(BigdecimalUtil.divide(zyqbyxss + zyhbyxss + sltybyxss, 60, 9));
        tjDtjg.setSltybycs(zyqbycs + zyhbycs + sltybycs);

        //作业前备用小时/次数
        tjDtjg.setZyqsltybyxs(BigdecimalUtil.divide(zyqbyxss, 60, 9));
        tjDtjg.setZyqsltybycs(zyqbycs);

        //作业后备用小时/次数
        tjDtjg.setZyhsltybyxs(BigdecimalUtil.divide(zyhbyxss, 60, 9));
        tjDtjg.setZyhsltybycs(zyhbycs);

        //计划停运小时/次数    大修停运小时/次数 + 小修停运小时/次数 + 实验停运小时/次数 +清扫停运小时/次数 + 改造施工小时/次数
        tjDtjg.setJhtyxs(BigdecimalUtil.divide(dxtyxss + xxtyxss + sytyxss + qstyxss + gzsgtyxss, 60, 9));
        tjDtjg.setJhtycs(dxcs + xxcs + sycs + qscs + gzsgcs);

        //非计划停运小时/次数    第一类非计划停运小时/次数 +第二类非计划停运小时/次数
        // + 第三类非计划停运小时/次数 +第四类非计划停运小时/次数
        tjDtjg.setFjhtyxs(BigdecimalUtil.divide(fjhtyxss1 + fjhtyxss2 + fjhtyxss3 + fjhtyxss4, 60, 9));
        tjDtjg.setFjhsgcs(fjhtycs1 + fjhtycs2 + fjhtycs3 + fjhtycs4);

        //非计划停运小时/次数    第一类非计划停运小时/次数 +第二类非计划停运小时/次数
        tjDtjg.setQptyxs(BigdecimalUtil.divide(fjhtyxss1 + fjhtyxss2, 60, 9));
        tjDtjg.setQptycs(fjhtycs1 + fjhtycs2);

        //备用小时/次数   调度停运小时/次数 + 作业前备用小时/次数+作业后备用小时/次数+受累停运小时/次数
        tjDtjg.setByxs(BigdecimalUtil.divide(ddtybyxss + zyqbyxss + zyhbyxss + sltybyxss, 60, 9));
        tjDtjg.setBycs(ddtybycs + zyqbycs + zyhbycs + sltybycs);

        //不可用小时 大修停运小时+小修停运小时+实验停运小时+清扫停运小时+改造施工小时+ 第一类非计划停运小时+第二类非计划停运小时
        //+第三类非计划停运小时+第四类非计划停运小时
        long bykxs = fjhtyxss1 + fjhtyxss2 + fjhtyxss3 + fjhtyxss4 + dxtyxss + xxtyxss + sytyxss + qstyxss + gzsgtyxss;
        tjDtjg.setBkyxs(BigdecimalUtil.divide(bykxs, 60, 9));

        //统计期间
        tjDtjg.setTjqjxs(BigdecimalUtil.divide(tjqjH, 60, 9));

        //可用小时  统计期间 - 不可用小时
        tjDtjg.setKyxs(BigdecimalUtil.divide(tjqjH - bykxs, 60, 9));
        //运行小时  可用小时 - 受累停运小时
        tjDtjg.setYxxs(BigdecimalUtil.divide((tjqjH - bykxs) - (ddtybyxss + zyqbyxss + zyhbyxss + sltybyxss), 60, 9));

        //设备台年数     统计期间设备有效运行天数/365天
        tjDtjg.setSbtns(BigdecimalUtil.divide(tjqjH, 60 * 24 * 365, 9));


        //计划延期   （1是，0否）
        tjDtjg.setJhyq(jhyq);


        //设备更换   （1是，0否）
        tjDtjg.setSbgh(sbgh);


        //暴露率  运行小时/可用小时 * 100%
        if ((tjqjH - bykxs) != 0) {
            BigDecimal bbl = BigdecimalUtil.divide((tjqjH - bykxs) - (ddtybyxss + zyqbyxss + zyhbyxss + sltybyxss), tjqjH - bykxs, 9);
            tjDtjg.setBll(bbl.multiply(new BigDecimal("100")));
        }else{
            tjDtjg.setBll(BigDecimal.ZERO);
        }

        if (tjqjH != 0) {
            //计划停运率  计划停运次数/设备台年数
            BigDecimal tn = BigdecimalUtil.divide(tjqjH, 60 * 24 * 365, 9);
            BigDecimal cs1 = new BigDecimal(String.valueOf(dxcs + xxcs + sycs + qscs + gzsgcs));
            tjDtjg.setJhtyl(cs1.divide(tn, 9, RoundingMode.HALF_UP));

            //非计划停运率 非计划停运次数/设备台年数
            BigDecimal cs2 = new BigDecimal(String.valueOf(fjhtycs1 + fjhtycs2 + fjhtycs3 + fjhtycs4));
            tjDtjg.setFjhtyl(cs2.divide(tn, 9, RoundingMode.HALF_UP));

            //强迫停运率  强迫停运次数/设备台年数
            BigDecimal cs3 = new BigDecimal(String.valueOf(fjhtycs1 + fjhtycs2));
            tjDtjg.setQptyl(cs3.divide(tn, 9, RoundingMode.HALF_UP));

            // 可用系数     可用小时/统计期间小时 * 100%
            BigDecimal kyxishu = BigdecimalUtil.divide(tjqjH - bykxs, tjqjH, 9);
            tjDtjg.setKyxishu(kyxishu.multiply(new BigDecimal("100")));

            //运行系数    运行小时/统计期间小时 * 100%
            BigDecimal yxxishu = BigdecimalUtil.divide((tjqjH - bykxs) - (ddtybyxss + zyqbyxss + zyhbyxss + sltybyxss), tjqjH, 9);
            tjDtjg.setYxxishu(yxxishu.multiply(new BigDecimal("100")));

            //计划停运系数 计划停运小时/统计期间小时 *100%
            BigDecimal jhtyxishu = BigdecimalUtil.divide(dxtyxss + xxtyxss + sytyxss + qstyxss + gzsgtyxss, tjqjH, 9);
            tjDtjg.setJhtyxishu(jhtyxishu.multiply(new BigDecimal("100")));

            //非计划停运系数  非计划停运小时/统计期间小时 * 100%
            BigDecimal fjhtyxsishu = BigdecimalUtil.divide(fjhtyxss1 + fjhtyxss2 + fjhtyxss3 + fjhtyxss4, tjqjH, 9);
            tjDtjg.setFjhtyxishu(fjhtyxsishu.multiply(new BigDecimal("100")));

            //强迫停运系数   强迫停运小时/统计期间小时 * 100%
            BigDecimal qptyxishu = BigdecimalUtil.divide(fjhtyxss1 + fjhtyxss2, tjqjH, 9);
            tjDtjg.setQptyxishu(qptyxishu.multiply(new BigDecimal("100")));
        }else{
            tjDtjg.setJhtyl(BigDecimal.ZERO);
            tjDtjg.setFjhtyl(BigDecimal.ZERO);
            tjDtjg.setQptyl(BigDecimal.ZERO);
            tjDtjg.setKyxishu(BigDecimal.ZERO);
            tjDtjg.setYxxishu(BigDecimal.ZERO);
            tjDtjg.setJhtyxishu(BigDecimal.ZERO);
            tjDtjg.setFjhtyxishu(BigDecimal.ZERO);
            tjDtjg.setQptyxishu(BigDecimal.ZERO);
        }


        //连续可用小时 可用小时/(计划停运次数+非计划停运次数)
        long cs4 = (dxcs + xxcs + sycs + qscs + gzsgcs) + (fjhtycs1 + fjhtycs2 + fjhtycs3 + fjhtycs4);
        tjDtjg.setLxkyxs(BigdecimalUtil.divide(tjqjH - bykxs, (cs4 == 0 ? 1 : cs4) * 60, 9));
        tjDtjg.setSfzyx(sfzyx);
        tjDtjg.setDylftycs(chcgcs);
        if(ZbfxEnum.cftd.getValue().equals(tjlx) && CollectionUtil.isNotEmpty(device)){
            tjDtjg.setCftdcs(device.size());
        }else{
            tjDtjg.setCftdcs(0);
        }
        return tjDtjg;
    }

    /**
     * @FunctionName:
     * @Description: 单条运行事件取中间数据
     * @Param:
     * @Return:
     */
    private Map<String, Long> getParams(Entity device, Date tjqssj,Date tjzzsj) {
        Map<String, Long> r = new HashMap<>();
        // 统计起始时间
        // 运行事件起始时间
        Date qssj = device.getQssj();
        // 运行事件作业起始时间
        Date zyqssj = device.getZyqssj();
        // 运行事件作业终止时间
        Date zyzzsj = device.getZyzzsj();
        // 运行事件终止时间
        Date zzsj = device.getZzsj();
        // 作业前停运备用小时
        long zyqbyxss = 0;
        // 作业前停运备用次数
        long zyqbycs = 0;
        // 作业后停运备用小时
        long zyhbyxss = 0;
        // 作业后停运备用次数
        long zyhbycs = 0;
        // 作业小时
        long zyxss = 0;
        // 作业次数
        long zycs = 0;
        String ztfl = device.getZtfl();
        if ("LO".equals(ztfl)) {

        } else if ("DR".equals(ztfl) || "PR".equals(ztfl)) {
            if (DateUtil.compare(qssj, tjqssj) >= 0 && DateUtil.compare(zzsj, tjzzsj) <= 0) {
                // 起始时间晚于统计起始事件 终止事件早于统计终止事件
                zyxss = DateUtil.between(qssj, zzsj, DateUnit.MINUTE);
            } else if (DateUtil.compare(qssj, tjqssj) >= 0 && DateUtil.compare(zzsj, tjzzsj) > 0) {
                // 起始事件晚于统计起始时间 终止时间晚于统计终止时间
                zyxss = DateUtil.between(qssj, tjzzsj, DateUnit.MINUTE);
            } else if (DateUtil.compare(qssj, tjqssj) < 0 && DateUtil.compare(zzsj, tjzzsj) <= 0) {
                // 起始事件早于统计起始时间 终止时间早于统计终止时间
                zyxss = DateUtil.between(tjqssj, zzsj, DateUnit.MINUTE);
            } else {
                // 起始时间早于统计起始事件 终止事件晚于统计终止事件
                zyxss = DateUtil.between(tjqssj, tjzzsj, DateUnit.MINUTE);
            }
            zycs = zyxss == 0 ? 0 : 1;
        } else if ("UO1".equals(ztfl) || "UO2".equals(ztfl)) {
            if (DateUtil.compare(qssj, tjqssj) >= 0 && DateUtil.compare(zzsj, tjzzsj) <= 0) {
                // 起始时间大于等于统计起始时间  终止时间小于等于统计终止时间

                zyhbyxss = DateUtil.between(zyzzsj, zzsj, DateUnit.MINUTE);
                zyxss = DateUtil.between(qssj, zyzzsj, DateUnit.MINUTE);
                zyhbycs = (zyhbyxss == 0 ? 0 : 1);
                zycs = (zyxss == 0 ? 0 : 1);

            } else if (DateUtil.compare(qssj, tjqssj) >= 0 &&
                    DateUtil.compare(zzsj, tjzzsj) > 0) {
                // 起始时间大于统计起始时间  终止时间大于统计终止时间

                if (DateUtil.compare(tjzzsj, zyzzsj) > 0) {
                    // 统计终止时间 > 作业终止时间

                    zyhbyxss = DateUtil.between(zyzzsj, tjzzsj, DateUnit.MINUTE);
                    zyxss = DateUtil.between(qssj, zyzzsj, DateUnit.MINUTE);
                    zyhbycs = zyhbyxss == 0 ? 0 : 1;
                    zycs = zyxss == 0 ? 0 : 1;
                } else {
                    // 统计终止时间 <= 作业终止时间
                    zyxss = DateUtil.between(qssj, tjzzsj, DateUnit.MINUTE);
                    zycs = zyxss == 0 ? 0 : 1;
                }

            } else if (DateUtil.compare(qssj, tjqssj) < 0 &&
                    DateUtil.compare(zzsj, tjzzsj) <= 0) {
                // 起始时间小于统计起始时间  终止时间小于等于统计终止时间

                if (DateUtil.compare(tjqssj, zyzzsj) < 0) {
                    // 统计起始时间 < 作业终止时间

                    zyhbyxss = DateUtil.between(zyzzsj, zyzzsj, DateUnit.MINUTE);
                    zyxss = DateUtil.between(tjqssj, zyzzsj, DateUnit.MINUTE);
                    zyhbycs = zyhbyxss == 0 ? 0 : 1;
                    zycs = zyxss == 0 ? 0 : 1;
                } else {
                    //统计起始时间 >= 作业终止时间

                    zyhbyxss = DateUtil.between(tjqssj, zzsj, DateUnit.MINUTE);
                    zyhbycs = 1;
                }
            } else {
                //起始时间 < 统计起始时间，终止时间 > 统计终止时间

                if (DateUtil.compare(tjqssj, zyzzsj) < 0) {
                    // 统计起始时间 < 作业起始时间

                    if (DateUtil.compare(tjzzsj, zyzzsj) <= 0) {
                        //统计终止时间 <= 作业终止时间
                        zyxss = DateUtil.between(tjqssj, tjzzsj, DateUnit.MINUTE);
                        zycs = zyxss == 0 ? 0 : 1;
                    } else {
                        // 统计终止时间 > 作业终止时间

                        zyhbyxss = DateUtil.between(zyzzsj, tjzzsj, DateUnit.MINUTE);
                        zyxss = DateUtil.between(tjqssj, zyzzsj, DateUnit.MINUTE);
                        zyhbycs = zyhbyxss == 0 ? 0 : 1;
                        zycs = zyxss == 0 ? 0 : 1;
                    }

                } else {
                    // 统计起始时间 >= 作业终止时间

                    zyhbyxss = DateUtil.between(tjqssj, tjzzsj, DateUnit.MINUTE);
                    zyhbycs = zyhbyxss == 0 ? 0 : 1;
                }
            }

            if ("UO1".equals(ztfl) && DateUtil.compare(qssj, zyzzsj) == 0 &&
                    DateUtil.compare(qssj, tjqssj) >= 0) {
                long chcgcs = 1;
                r.put("chcgcs", chcgcs);
                zyxss=0;
                zycs=0;
            }

        } else {
            if (DateUtil.compare(qssj, tjqssj) >= 0 && DateUtil.compare(zzsj, tjzzsj) <= 0) {
                // 起始时间大于等于统计起始时间  终止时间小于等于统计终止时间
                zyqbyxss = DateUtil.between(qssj, zyqssj, DateUnit.MINUTE);
                zyhbyxss = DateUtil.between(zyzzsj, zzsj, DateUnit.MINUTE);
                zyxss = DateUtil.between(zyqssj, zyzzsj, DateUnit.MINUTE);
                zyqbycs = zyqbyxss == 0 ? 0 : 1;
                zyhbycs = zyhbyxss == 0 ? 0 : 1;
                zycs = zyxss == 0 ? 0 : 1;

            } else if (DateUtil.compare(qssj, tjqssj) >= 0 &&
                    DateUtil.compare(zzsj, tjzzsj) > 0) {
                // 起始时间大于统计起始时间  终止时间大于统计终止时间

                if (DateUtil.compare(tjzzsj, zyzzsj) > 0) {
                    // 统计终止时间 > 作业终止时间

                    zyqbyxss = DateUtil.between(qssj, zyqssj, DateUnit.MINUTE);
                    zyhbyxss = DateUtil.between(zyzzsj, tjzzsj, DateUnit.MINUTE);
                    zyxss = DateUtil.between(zyqssj, zyzzsj, DateUnit.MINUTE);
                    zyqbycs = zyqbyxss == 0 ? 0 : 1;
                    zyhbycs = zyhbyxss == 0 ? 0 : 1;
                    zycs = zyxss == 0 ? 0 : 1;
                } else if (DateUtil.compare(tjzzsj, zyzzsj) <= 0 && DateUtil.compare(tjzzsj, zyqssj) > 0) {
                    //统计终止时间 <= 作业终止时间，统计终止时间 > 作业起始时间

                    zyqbyxss = DateUtil.between(qssj, zyqssj, DateUnit.MINUTE);
                    zyxss = DateUtil.between(zyqssj, tjzzsj, DateUnit.MINUTE);
                    zyqbycs = zyqbyxss == 0 ? 0 : 1;
                    zycs = zyxss == 0 ? 0 : 1;
                } else {
                    // 统计终止时间 <= 作业起始时间

                    zyqbyxss = DateUtil.between(qssj, tjzzsj, DateUnit.MINUTE);
                    zyqbycs = zyqbyxss == 0 ? 0 : 1;
                }

            } else if (DateUtil.compare(qssj, tjqssj) < 0 &&
                    DateUtil.compare(zzsj, tjzzsj) <= 0) {
                // 起始时间小于统计起始时间  终止时间小于等于统计终止时间

                if (DateUtil.compare(tjqssj, zyqssj) < 0) {
                    // 统计起始时间 < 作业起始时间

                    zyqbyxss = DateUtil.between(tjqssj, zyqssj, DateUnit.MINUTE);
                    zyhbyxss = DateUtil.between(zyzzsj, zzsj, DateUnit.MINUTE);
                    zyxss = DateUtil.between(zyqssj, zyzzsj, DateUnit.MINUTE);
                    zyqbycs = zyqbyxss == 0 ? 0 : 1;
                    zyhbycs = zyhbyxss == 0 ? 0 : 1;
                    zycs = zyxss == 0 ? 0 : 1;
                } else if (DateUtil.compare(tjqssj, zyzzsj) <= 0 && DateUtil.compare(tjqssj, zyqssj) > 0) {
                    //统计起始时间 >=作业起始时间，统计起始时间 < 作业终止时间

                    zyhbyxss = DateUtil.between(zyzzsj, zzsj, DateUnit.MINUTE);
                    zyxss = DateUtil.between(tjqssj, zyzzsj, DateUnit.MINUTE);
                    zyhbycs = zyhbyxss == 0 ? 0 : 1;
                    zycs = zyxss == 0 ? 0 : 1;
                } else {
                    //统计起始时间 >= 作业终止时间

                    zyhbyxss = DateUtil.between(tjqssj, zzsj, DateUnit.MINUTE);
                    zyhbycs = zyhbyxss == 0 ? 0 : 1;
                }
            } else {
                //起始时间 < 统计起始时间，终止时间 > 统计终止时间

                if (DateUtil.compare(tjqssj, zyqssj) < 0) {
                    // 统计起始时间 < 作业起始时间

                    if (DateUtil.compare(tjzzsj, zyqssj) <= 0) {
                        //统计终止时间 <= 作业起始时间

                        zyqbyxss = DateUtil.between(tjqssj, tjzzsj, DateUnit.MINUTE);
                        zyqbycs = zyqbyxss == 0 ? 0 : 1;
                    } else if (DateUtil.compare(tjzzsj, zyqssj) > 0 && DateUtil.compare(tjzzsj, zyzzsj) < 0) {
                        //统计终止时间 > 作业起始时间，统计终止时间 < 作业终止时间

                        zyqbyxss = DateUtil.between(tjqssj, zyqssj, DateUnit.MINUTE);
                        zyxss = DateUtil.between(zyqssj, tjzzsj, DateUnit.MINUTE);
                        zyqbycs = zyqbyxss == 0 ? 0 : 1;
                        zycs = zyxss == 0 ? 0 : 1;
                    } else {
                        // 统计终止时间>= 作业终止时间

                        zyqbyxss = DateUtil.between(tjqssj, zyqssj, DateUnit.MINUTE);
                        zyhbyxss = DateUtil.between(zyzzsj, tjzzsj, DateUnit.MINUTE);
                        zyxss = DateUtil.between(zyqssj, zyzzsj, DateUnit.MINUTE);
                        zyqbycs = zyqbyxss == 0 ? 0 : 1;
                        zyhbycs = zyhbyxss == 0 ? 0 : 1;
                        zycs = zyxss == 0 ? 0 : 1;
                    }

                } else if (DateUtil.compare(tjqssj, zyqssj) >= 0 && DateUtil.compare(tjqssj, zyzzsj) < 0) {
                    // 统计起始时间 >= 作业起始时间，统计起始时间 < 作业终止时间
                    if (DateUtil.compare(tjzzsj, zyzzsj) <= 0) {
                        //统计终止时间 <= 作业终止时间
                        zyxss = DateUtil.between(tjqssj, tjzzsj, DateUnit.MINUTE);
                        zycs = zyxss == 0 ? 0 : 1;
                    } else {
                        //统计终止时间 > 作业终止时间
                        zyhbyxss += DateUtil.between(zyzzsj, tjzzsj, DateUnit.MINUTE);
                        zyxss = DateUtil.between(tjqssj, zyzzsj, DateUnit.MINUTE);
                        zyhbycs = zyhbyxss == 0 ? 0 : 1;
                        zycs = zyxss == 0 ? 0 : 1;
                    }

                } else {
                    // 统计起始时间 >= 作业终止时间

                    zyhbyxss = DateUtil.between(tjqssj, tjzzsj, DateUnit.MINUTE);
                    zyhbycs = zyhbyxss == 0 ? 0 : 1;
                }
            }
        }
        r.put("zyqbyxss", zyqbyxss);
        r.put("zyqbycs", zyqbycs);
        r.put("zyhbyxss", zyhbyxss);
        r.put("zyhbycs", zyhbycs);
        r.put("zyxss", zyxss);
        r.put("zycs", zycs);
        return r;
    }


}
