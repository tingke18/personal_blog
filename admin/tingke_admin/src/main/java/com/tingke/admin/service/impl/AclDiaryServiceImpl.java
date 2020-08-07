package com.tingke.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tingke.admin.entity.AclDiary;
import com.tingke.admin.entity.DiaryVo.DiaryOneVo;
import com.tingke.admin.entity.DiaryVo.DiaryTwoVo;
import com.tingke.admin.entity.DiaryVo.DiaryVo;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.AclDiaryMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.AclDiaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhx
 * @since 2020-05-30
 */
@Service
public class AclDiaryServiceImpl extends ServiceImpl<AclDiaryMapper, AclDiary> implements AclDiaryService {

    //个人日志显示
    @Override
    public R selectDiary(Long page,Long limit) {

        Page<AclDiary> Rpage = new Page<>(page, limit);

        if (Rpage == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //构建查询条件
        QueryWrapper<AclDiary> wrapper = new QueryWrapper<>();
        wrapper.eq("is_publish",1);

        Page<AclDiary> ipage = this.page(Rpage,wrapper);

        if (ipage == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        List<AclDiary> diaries = ipage.getRecords();

        long total = ipage.getTotal();

        QueryResponseResultList<AclDiary> objectQueryResponseResultList = new QueryResponseResultList<>();
        objectQueryResponseResultList.setList(diaries);
        objectQueryResponseResultList.setTotal((int)total);


        return new R(CommonCode.SUCCESS,objectQueryResponseResultList);

    }

    //根据id查询随笔
    @Override
    public R selectDiaryById(String id) {

        //校空
        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        AclDiary diary = this.getById(id);

        return new R(CommonCode.SUCCESS,diary);

    }

    //封装个人日志显示
    @Override
    public R selectDiaryVo() {

        //获取所有年份
        QueryWrapper<AclDiary> wrapper = new QueryWrapper<>();
        wrapper.eq("is_publish",1);
        wrapper.orderByDesc("modified_time");

        List<AclDiary> aclDiaries = baseMapper.selectList(wrapper);

        if (aclDiaries == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        ArrayList<DiaryOneVo> diaryOneVos = new ArrayList<>();

        //遍历封装年份
        aclDiaries.forEach(aclDiary -> {

            Date modifiedTime = aclDiary.getModifiedTime();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            String year = simpleDateFormat.format(modifiedTime);


            DiaryOneVo diaryOneVo = new DiaryOneVo();
            diaryOneVo.setYears(year);

            diaryOneVos.add(diaryOneVo);

        });

        //去除List重复数据
        List<DiaryOneVo> diaryOneVoList = diaryOneVos.stream().distinct().collect(Collectors.toList());

        //遍历封装年月日随笔
        diaryOneVoList.forEach(diaryOneVo -> {
            //创建新二级list对象
            ArrayList<DiaryTwoVo> diaryTwoVos = new ArrayList<>();
            //遍历所有查询到的随笔
            aclDiaries.forEach(aclDiary -> {
                //获得修改时间
                Date modifiedTime = aclDiary.getModifiedTime();
                //格式化时间
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy");
                String year = simpleDateFormat1.format(modifiedTime);

                //判断年份是否相等
                if (diaryOneVo.getYears().equals(year)){
                    //创建新二级对象
                    DiaryTwoVo diaryTwoVo = new DiaryTwoVo();
                    //格式化月日
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd");
                    String month = simpleDateFormat2.format(modifiedTime);
                    //设置月日及对应的随笔
                    diaryTwoVo.setMonths(month);
                    diaryTwoVo.setDiary(aclDiary);
                    //添加新的二级对象
                    diaryTwoVos.add(diaryTwoVo);

                }

            });
            //设置一级对象下的二级对象
            diaryOneVo.setChildren(diaryTwoVos);
        });

        int count = diaryOneVoList.size();


        QueryResponseResultList<DiaryOneVo> objectQueryResponseResultList = new QueryResponseResultList<>();
        objectQueryResponseResultList.setList(diaryOneVoList);
        objectQueryResponseResultList.setTotal(count);


        return new R(CommonCode.SUCCESS,objectQueryResponseResultList);
    }

    //新增随笔
    @Override
    public R addDiary(AclDiary aclDiary) {

        //校空
        if (aclDiary == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.save(aclDiary);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //修改随笔
    @Override
    public R editDiary(AclDiary aclDiary) {
        //校空
        if (aclDiary == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        int count = baseMapper.updateById(aclDiary);

        if (count < 1){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return  new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //删除随笔
    @Override
    public R deleteDiary(String id) {
        //校空
        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.removeById(id);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }


        return  new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //编辑随笔发布状态
    @Override
    public R editDiaryPublish(String id, Integer isPublish) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(isPublish.toString())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        AclDiary diary = new AclDiary();
        diary.setId(id);
        diary.setIsPublish(isPublish);

        boolean success = this.updateById(diary);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        if (isPublish == 1){
            return  new R(CommonCode.SUCCESS, 1);
        }else {
            return  new R(CommonCode.SUCCESS, 0);
        }
    }

    //后台个人日志查询
    @Override
    public R selectAllDiary(Long page, Long limit, DiaryVo diaryVo) {

        //校空
        if (StringUtils.isEmpty(page.toString()) || StringUtils.isEmpty(limit.toString())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //分页
        Page<AclDiary> pageQ = new Page<>(page, limit);

        if (pageQ == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //条件
        QueryWrapper<AclDiary> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");

        if (diaryVo != null){
            if (StringUtils.isNoneBlank(diaryVo.getStart())){
                wrapper.gt("create_time",diaryVo.getStart());
            }
            if (StringUtils.isNoneBlank(diaryVo.getEnd())){
                wrapper.gt("create_time",diaryVo.getStart());
            }
        }

        Page<AclDiary> iPage = this.page(pageQ, wrapper);

        long total = iPage.getTotal();//条数
        List<AclDiary> data = iPage.getRecords();

        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setList(data);
        queryResponseResultList.setTotal((int) total);

        return new R(CommonCode.SUCCESS,queryResponseResultList);
    }
}
