package com.tingke.admin.service;

import com.tingke.admin.entity.FrLinkCategory;
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
public interface FrLinkCategoryService extends IService<FrLinkCategory> {

    //快捷链接分类添加
    R addLinkCategory(String category);

    //快捷链接分类删除
    R deleteLinkCategory(String id);

    //快捷链接分类修改
    R editLinkCategory(FrLinkCategory frLinkCategory);

    //快捷链接分类查询
    R selectLinkCategory();

}
