package com.tingke.admin.service.impl;

import com.tingke.admin.entity.FrLinkCategory;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.FrLinkCategoryMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrLinkCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhx
 * @since 2020-05-31
 */
@Service
public class FrLinkCategoryServiceImpl extends ServiceImpl<FrLinkCategoryMapper, FrLinkCategory> implements FrLinkCategoryService {

    //快捷链接分类添加
    @Override
    @Transactional
    public R addLinkCategory(String category) {

        if (StringUtils.isEmpty(category)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        FrLinkCategory frLinkCategory = new FrLinkCategory();
        frLinkCategory.setCategory(category);
        boolean success = this.save(frLinkCategory);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //快捷链接分类删除
    @Override
    @Transactional
    public R deleteLinkCategory(String id) {

        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.removeById(id);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //快捷链接分类修改
    @Override
    @Transactional
    public R editLinkCategory(FrLinkCategory frLinkCategory) {

        if (frLinkCategory == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.updateById(frLinkCategory);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //快捷链接分类查询
    @Override
    public R selectLinkCategory() {

        List<FrLinkCategory> frLinkCategories = baseMapper.selectList(null);

        if (frLinkCategories == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setTotal(frLinkCategories.size());
        queryResponseResultList.setList(frLinkCategories);

        return new R(CommonCode.SUCCESS,queryResponseResultList);
    }

}
