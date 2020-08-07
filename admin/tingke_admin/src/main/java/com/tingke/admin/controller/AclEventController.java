package com.tingke.admin.controller;


import com.tingke.admin.entity.AclEvent;
import com.tingke.admin.model.R;
import com.tingke.admin.service.AclEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhx
 * @since 2020-05-19
 */
@Api(value="后台管理“事件”管理接口",description = "后台管理“事件”管理，增删改查")
@RestController
@RequestMapping("/admin/acl-event")
@CrossOrigin
public class AclEventController {

    @Autowired
    private AclEventService aclEventService;

    @ApiOperation("添加事件")
    @GetMapping("/addEvent/{event}")
    @ApiImplicitParam(name="event",value = "内容",required=true,paramType="path")
    public R addEvent(@PathVariable("event")String content){
        R responseResult = aclEventService.addEvent(content);

        return responseResult;
    }

    @ApiOperation("修改事件")
    @PostMapping("/editEvent")
    public R editEvent(@RequestBody AclEvent aclEvent){
        R responseResult = aclEventService.editEvent(aclEvent);

        return responseResult;
    }

    @ApiOperation("完成事件")
    @GetMapping("/completeEvent/{id}")
    @ApiImplicitParam(name="id",value = "主键",required=true,paramType="path")
    public R completeEvent(@PathVariable("id")String id){
        R responseResult = aclEventService.completeEvent(id);

        return responseResult;
    }

    @ApiOperation("遍历返回事件")
    @PostMapping("/selectEvent")
    public R selectEvent(){
        R responseResult = aclEventService.selectEvent();

        return responseResult;
    }

    @ApiOperation("通过id查询")
    @PostMapping("/selectOneEvent/{id}")
    @ApiImplicitParam(name="id",value = "主键",required=true,paramType="path")
    public R selectOneEvent(@PathVariable("id")String id){
        R responseResult = aclEventService.selectOneEvent(id);

        return responseResult;
    }

    @ApiOperation("根据日期查询事件")
    @GetMapping("/selectEventByDate/{date}")
    @ApiImplicitParam(name="date",value = "日期",required=true,paramType="path")
    public R selectEventByDate(@PathVariable("date")String date) throws ParseException {
        R responseResult = aclEventService.selectEventByDate(date);

        return responseResult;
    }

    @ApiOperation("根据ids删除事件")
    @GetMapping("/deleteEventByIds/{ids}")
    @ApiImplicitParam(name="ids",value = "日期",required=true,paramType="path")
    public R deleteEventByIds(@PathVariable("ids") String ids) throws ParseException {
        R responseResult = aclEventService.deleteEventByIds(ids);

        return responseResult;
    }
}

