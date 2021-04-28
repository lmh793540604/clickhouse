package com.fiveonebsi.entity;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class TaskEntity {
    private String ids;

    private String tjfl;

    private String sjzzdw;

    private String zhdqhjgjs;

    private String tjsb;

    private String zhdqjssx;

    private String dydj;

    private String rwmc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private String taskId;

    private String tjlx;

    private String xljsff;



    public void getTaskVo(TaskVo  taskVo,TaskEntity taskEntity) {
        taskVo.setDydj(parseStrToList(taskEntity.getDydj()));
        taskVo.setEndTime(taskEntity.getEndTime());
        taskVo.setRwmc(taskEntity.getRwmc());
        taskVo.setParseIds(parseStrToList(taskEntity.getIds()));
        taskVo.setStartTime(taskEntity.getStartTime());
        taskVo.setTjfl(taskEntity.getTjfl());
        taskVo.setXljsff(taskEntity.getXljsff());
        taskVo.setZhdqhjgjs(taskEntity.getZhdqhjgjs());
        taskVo.setZhdqjssx(parseStrToList(taskEntity.getZhdqjssx()));
        taskVo.setTjsb(parseStrToList(taskEntity.getTjsb()));
        taskVo.setTaskId(taskEntity.getTaskId());
    }

    public static List<String> parseStrToList(String paser){
        return Arrays.asList(paser.split(","));
    }
}
