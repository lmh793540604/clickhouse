package com.fiveonebsi.service;

import com.alibaba.fastjson.JSONObject;
import com.fiveonebsi.entity.Entity;
import com.fiveonebsi.entity.TaskEntity;
import com.fiveonebsi.entity.TaskVo;
import com.fiveonebsi.entity.TjDtjg;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

public interface CKService {

    //结果总条数
    public List<Entity> getreslut();
    public List<TjDtjg> demo(TaskVo taskVo) throws ParseException;
    TaskEntity getById(String taskId);

    String demoSave(TaskVo taskVo);

    List<TaskEntity> page();
}
