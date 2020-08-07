package com.tingke.admin.service;

import com.tingke.admin.entity.FrLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tingke.admin.model.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhx
 * @since 2020-05-31
 */
public interface FrLinkService extends IService<FrLink> {

    //快捷链接显示
    R selectLink();

    //快捷链接分页显示
    R selectPageLink(Long page, Long limit,String condition);

    //快捷链接添加
    R addLink(FrLink frLink);

    //快捷链接删除
    R deleteLink(String id);

    //快捷链接修改
    R editLink(FrLink frLink);

    //根据id快捷链接
    R selectLinkById(String id);
}
