package com.tingke.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tingke.admin.entity.AclEvent;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.AclEventMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.AclEventService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhx
 * @since 2020-05-19
 */
@Service
@SuppressWarnings("all")
public class AclEventServiceImpl extends ServiceImpl<AclEventMapper, AclEvent> implements AclEventService {

    @Autowired
    private AclEventMapper aclEventMapper;

    //添加事件
    @Override
    @Transactional
    public R addEvent(String content) {
        //校验空
        if (StringUtils.isEmpty(content)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        AclEvent aclEvent = new AclEvent();
        aclEvent.setEvent(content);

        boolean success = this.save(aclEvent);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }


        aclEvent.setStatus(0);
        if (aclEvent == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS,aclEvent);
    }

    //修改事件
    @Override
    @Transactional
    public R editEvent(AclEvent aclEvent) {
        //校验空
        if (aclEvent == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //修改
        boolean success = this.updateById(aclEvent);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        QueryWrapper<AclEvent> wrapper = new QueryWrapper<>();
        wrapper.eq("id",aclEvent.getId());
        wrapper.select("create_Time");

        AclEvent event = this.getOne(wrapper);


        return new R(CommonCode.SUCCESS,event.getCreateTime());
    }

    //删除事件
    @Override
    @Transactional
    public R completeEvent(String id) {
        //校验空
        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //将状态设为false
        AclEvent aclEvent = baseMapper.selectById(id);
        if (aclEvent == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        aclEvent.setStatus(1);
        int success = baseMapper.updateById(aclEvent);
        if (success <1){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, aclEvent.getCreateTime());
    }

    //遍历返回事件
    @Override
    public R selectEvent() {
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todyDate = simpleDateFormat.format(date);

        //根据日期查询事件
        List<AclEvent> aclEvents = aclEventMapper.selectEventList(todyDate);

        if (aclEvents == null){
            return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
        }

        int size = aclEvents.size();

        QueryResponseResultList<AclEvent> queryResponseResultList = new QueryResponseResultList<>();
        queryResponseResultList.setList(aclEvents);
        queryResponseResultList.setTotal(size);



        return new R(CommonCode.SUCCESS, queryResponseResultList);
    }

    @Override
    public R selectOneEvent(String id) {
        //校空
        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        AclEvent aclEvent = baseMapper.selectById(id);
        if (aclEvent == null){
            CastDiyException.cast(CommonCode.FAIL);
        }



        return new R(CommonCode.SUCCESS,aclEvent);
    }

    //根据日期查询事件
    @Override
    public R selectEventByDate(String date) throws ParseException {

        List<AclEvent> aclEvents = aclEventMapper.selectEventList(date);

        if (aclEvents == null){
            return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
        }
        int size = aclEvents.size();

        QueryResponseResultList<AclEvent> queryResponseResultList = new QueryResponseResultList<>();
        queryResponseResultList.setList(aclEvents);
        queryResponseResultList.setTotal(size);


        return new R(CommonCode.SUCCESS, queryResponseResultList);
    }

    //根据ids删除事件
    @Override
    @Transactional
    public R deleteEventByIds(String ids) {
        //校验空
        if (StringUtils.isEmpty(ids)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        QueryWrapper<AclEvent> wrapper = new QueryWrapper<>();
        wrapper.eq("id",ids.split(",")[0]);
        wrapper.select("create_Time");

        AclEvent event = this.getOne(wrapper);

        //转换成集合并删除
        String[] strings = ids.split(",");
        List<String> idsList = Arrays.asList(strings);
        boolean b = this.removeByIds(idsList);

        if (!b){
            CastDiyException.cast(CommonCode.FAIL);
        }




        return new R(CommonCode.SUCCESS, event.getCreateTime());
    }
}
