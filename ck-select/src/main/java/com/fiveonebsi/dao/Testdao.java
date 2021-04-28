package com.fiveonebsi.dao;

import com.fiveonebsi.entity.Entity;
import com.fiveonebsi.entity.TaskEntity;
import com.fiveonebsi.entity.TaskVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface Testdao {

    //测试查询
    public List<Entity> all_count();
    public List<Entity> demo(@Param("ids") List<String> ids, @Param("tjqssj") Date tjqssj, @Param("tjzzsj") Date tjzzsj, @Param("sjzzdw") String sjzzdw, @Param("xljsfs") String xljsfs, @Param("zhdqhjgjs") String zhdqhjgjs, @Param("sblx") List<String> sblx, @Param("dydjs") List<String> dydjs, @Param("zhdqjssx") List<String> zhdqjssx);

    void save(TaskEntity taskEntity);

    TaskEntity getById(String taskId);

    List<TaskEntity> page();

}
