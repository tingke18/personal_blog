package com.tingke.admin.service;

import com.tingke.admin.entity.AclEvent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tingke.admin.model.R;

import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhx
 * @since 2020-05-19
 */
public interface AclEventService extends IService<AclEvent> {

    //添加事件
    R addEvent(String id);
    //修改事件
    R editEvent(AclEvent aclEvent);
    //完成事件
    R completeEvent(String id);
    //遍历返回事件
    R selectEvent();
    //通过id查询
    R selectOneEvent(String id);
    //根据日期查询事件
    R selectEventByDate(String date) throws ParseException;
    //根据ids删除事件
    R deleteEventByIds(String ids);
}
