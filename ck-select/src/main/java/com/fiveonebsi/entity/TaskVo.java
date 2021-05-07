package com.fiveonebsi.entity;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author System
 * @since 2019-12-16
 */
@Data
public class TaskVo {
    private Map<String, String> ids;

    private String tjfl;

    private String xljsff;

    private String sjzzdw;

    private String zhdqhjgjs;

    private List<String> tjsb;

    private List<String> zhdqjssx;

    private List<String> dydj;

    private String rwmc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private String taskId;

    private String tjlx;

    private List<String> dwjb;

    private List<String> itemId;

    private List<String> sjdwId;

    private List<String> zzdwId;

    private List<String> sgdwId;

    private List<String> jsyyId;

    private List<String> zryyId;

    private List<String> sbbjId;

    private String qswd;

    private String sjzt;

    private String byqjsff;

    private List<String> sbnx;

    private List<String> tdsj;

    private Integer tdcs;

    private List<String> parseIds;

    private Integer pageSize;

    private Integer pageNum;

    public void getTaskEntity(TaskVo  taskVo,TaskEntity taskEntity) {
        taskEntity.setDydj(StringUtils.join(taskVo.getDydj(),","));
        taskEntity.setEndTime(taskVo.getEndTime());
        taskEntity.setRwmc(taskVo.getRwmc());
        taskEntity.setIds(StringUtils.join(taskVo.getParseIds(),","));
        taskEntity.setStartTime(taskVo.getStartTime());
        taskEntity.setTjfl(taskVo.getTjfl());
        taskEntity.setXljsff(taskVo.getXljsff());
        taskEntity.setZhdqhjgjs(taskVo.getZhdqhjgjs());
        taskEntity.setZhdqjssx(StringUtils.join(taskVo.getZhdqjssx(),","));
        taskEntity.setTjsb(StringUtils.join(taskVo.getTjsb(),","));
        taskEntity.setTaskId(IdUtil.fastUUID());
        taskEntity.setSjzzdw(taskVo.getSjzzdw());
    }
}
