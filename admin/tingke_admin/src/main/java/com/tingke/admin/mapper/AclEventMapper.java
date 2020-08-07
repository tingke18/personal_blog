package com.tingke.admin.mapper;

import com.tingke.admin.entity.AclEvent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhx
 * @since 2020-05-19
 */
public interface AclEventMapper extends BaseMapper<AclEvent> {
    //根据日期查询事件
    List<AclEvent> selectEventList(@Param("todyDate") String todyDate);

}
