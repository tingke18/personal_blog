package com.tingke.admin.service;

import com.tingke.admin.entity.AclDiary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tingke.admin.entity.DiaryVo.DiaryVo;
import com.tingke.admin.model.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhx
 * @since 2020-05-30
 */
public interface AclDiaryService extends IService<AclDiary> {

    //个人日志显示
    R selectDiary(Long page,Long limit);

    //根据id查询随笔
    R selectDiaryById(String id);

    //封装个人日志显示
    R selectDiaryVo();

    //新增随笔
    R addDiary(AclDiary aclDiary);

    //修改随笔
    R editDiary(AclDiary aclDiary);

    //删除随笔
    R deleteDiary(String id);

    //编辑随笔发布状态
    R editDiaryPublish(String id, Integer isPublish);

    //后台个人日志查询
    R selectAllDiary(Long page, Long limit, DiaryVo diaryVo);
}
