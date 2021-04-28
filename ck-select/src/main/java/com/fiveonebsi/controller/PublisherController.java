package com.fiveonebsi.controller;


import com.fiveonebsi.entity.TaskEntity;
import com.fiveonebsi.entity.TaskVo;
import com.fiveonebsi.entity.TjDtjg;
import com.fiveonebsi.entity.ZbfxEnum;
import com.fiveonebsi.service.CKService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/*
*
*  Desc: 发布接口的Controller
* */
@RestController
public class PublisherController{
    @Autowired
    CKService ckService;

    @RequestMapping(value="/res")
    @ResponseBody
    public Object res(){

        return ckService.getreslut();
    }

    @PostMapping(value="/demo")
    @ResponseBody
    public Object demo(@RequestBody TaskVo taskVo) throws ParseException {
//        t.setRwmc(vo.getRwmc());
//        t.setTjfl(vo.getTjfl());
//        t.setTjqssj(vo.getStartTime());
//        t.setTjzzsj(vo.getEndTime());
//        t.setXljsff(vo.getXljsff());
//        t.setSjzzdw(vo.getSjzzdw());
//        t.setZhdqhjgjs(vo.getZhdqhjgjs());
//        t.setDydj(dydj.substring(1, dydj.length() - 1));
//        t.setTjsb(tjsb.substring(1, tjsb.length() - 1));
//        t.setDwjb(dwjb.substring(1, dwjb.length() - 1));
//        t.setCreateId(EntityUtil.getCurrentUserId());
//        if (CollectionUtil.isNotEmpty(vo.getZhdqjssx())) {
//            String zhdqjssx = vo.getZhdqjssx().toString().replace(" ", "");
//            t.setZhdqjssx(zhdqjssx.substring(1, zhdqjssx.length() - 1));
//        }
//        t.setRwzt("0");
//        t.setTjlx(ZbfxEnum.multiple.getValue());
        return ckService.demoSave(taskVo);
    }

    @GetMapping("/getById")
    @ResponseBody
    public List<TjDtjg> getById(String taskId,Integer pageSize,Integer pageCurrent) throws ParseException {
        TaskEntity byId = ckService.getById(taskId);
        TaskVo taskVo = new TaskVo();
        byId.getTaskVo(taskVo,byId);
        PageHelper.startPage(pageCurrent,pageSize);
        return ckService.demo(taskVo);
    }

    @GetMapping("/page")
    @ResponseBody
    public List<TaskEntity> page(Integer pageSize,Integer pageCurrent) throws ParseException {
        PageHelper.startPage(pageCurrent,pageSize);
        return ckService.page();
    }

}
